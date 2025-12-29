package com.expense.utility;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

public class ResourcesLoader
{
    public  static String load(String path){
        try{
            ClassPathResource resource = new ClassPathResource(path);
            return StreamUtils.copyToString(
                    resource.getInputStream(),
                    StandardCharsets.UTF_8
            );
        }catch (Exception e) {
            throw new RuntimeException("Unable to load file: " + path, e);
        }
    }
}
