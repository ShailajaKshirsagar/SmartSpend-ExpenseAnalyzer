package com.expense.repository;

import com.expense.entity.MonthlyBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyBudgetRepo extends JpaRepository<MonthlyBudget,Long> {

    @Query("SELECT m FROM MonthlyBudget m WHERE m.userId =:userId AND m.month =:month AND m.year =:year")
    Optional<MonthlyBudget> findByUserIdAndMonthAndYear(@Param("userId") Long userId, @Param("month") Integer month, @Param("year") Integer year);

}
