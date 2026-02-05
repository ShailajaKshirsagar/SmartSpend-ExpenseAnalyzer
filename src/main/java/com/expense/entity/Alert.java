package com.expense.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String message;

    // CATEGORY_OVERSPEND, INCOME_EXPENSE_IMBALANCE
    private String type;

    @JsonIgnore
    private boolean readStatus;

    private Integer month;
    private Integer year;

    @JsonIgnore
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category; // nullable for income-expense alert
}