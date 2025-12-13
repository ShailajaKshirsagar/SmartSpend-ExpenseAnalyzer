package com.expense.repository;

import com.expense.entity.SavingGoal;
import com.expense.entity.SavingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SavingTransactionRepo extends JpaRepository<SavingTransaction,Long> {

    @Query("""
       SELECT s
       FROM SavingTransaction s
       WHERE s.user.id = :userId
       AND s.date BETWEEN :startDate AND :endDate
       """)
    List<SavingTransaction> findByUserIdAndDateBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
