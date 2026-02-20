package com.expense.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String title;

    private Double amount;

    private String category;

    private LocalDate date; // or LocalDate

    @ManyToOne
    @JoinColumn(name = "user_id")   // foreign key in expense table
    private User user;
}
