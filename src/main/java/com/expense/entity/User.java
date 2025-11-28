package com.expense.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;    // store hashed later for security

    private Long mobno;

//    @Column(unique = true)
//    private String deviceId;    // optional device-based login /for syncing with backend server

    private LocalDateTime createdAt = LocalDateTime.now();
}
