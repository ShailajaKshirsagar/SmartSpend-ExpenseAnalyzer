package com.expense.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String billName;
    private Double amount;
    private LocalDate dueDate;
    private Boolean isRecurring; //it can be true if it is recurring monthly
    private Boolean isPaid;
    private Boolean reminder3DaysSent = false;   // 3 days before due date--> upcoming bills
    private Boolean reminder1DaySent = false;    // 1 day before due date
    private Boolean overdue1DaySent = false;     // 1 day after due date


}
