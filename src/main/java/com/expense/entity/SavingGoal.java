package com.expense.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double targetAmount;
    private Double savedAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
