package com.expense.ai_client;

import com.expense.utility.ResourcesLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CohereClient {

    @Value("${cohere.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String categorize(String text,String type) {

        String promptTemplate = ResourcesLoader.load("ai_prompts/expense_categorization.txt");

        String prompt = promptTemplate.formatted(text);

        // request body for Cohere Chat API
        Map<String, Object> body = Map.of(
                "model", "command-r-plus-08-2024",
                    "message", prompt,
                "temperature", 0.2
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.cohere.ai/v1/chat",
                    entity,
                    Map.class
            );
            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null || responseBody.get("text") == null) {
                System.out.println("Cohere API returned null or empty response: " + responseBody);
                return "Unknown";
            }
            return responseBody.get("text").toString().trim();

        } catch (Exception e) {
            System.err.println("Error calling Cohere API: " + e.getMessage());
            return "Unknown";
        }
    }
}
