package com.expense.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertResponseDto {

    @JsonIgnore
    private Long alertId;
    private String message;
    private String type;
    @JsonIgnore
    private boolean read;
}
