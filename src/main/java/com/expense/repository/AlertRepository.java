package com.expense.repository;

import com.expense.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<Alert> findByUserIdAndCategoryIdAndTypeAndMonthAndYear(
            Long userId,
            Long categoryId,
            String type,
            Integer month,
            Integer year
    );

    Optional<Alert> findByUserIdAndTypeAndMonthAndYear(
            Long userId,
            String type,
            Integer month,
            Integer year
    );
}
