package com.expense.repository;

import com.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check if email exists
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email =:email")
    boolean existsByEmail(@Param("email") String email);

    // Find user by email
    @Query("SELECT u FROM User u WHERE u.email =:email")
    Optional<User> findByEmail(@Param("email") String email);

    //checks email and password matches or not
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User loginUser(@Param("email") String email,
                   @Param("password") String password);

    //to find user by mobile
    @Query("SELECT u FROM User u WHERE u.mobile =:mobile")
    Optional<User> findByMobile(@Param("mobile") Long mobile);
}
