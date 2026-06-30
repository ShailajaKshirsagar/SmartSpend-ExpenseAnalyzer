package com.expense.repository;

import com.expense.entity.SavingGoal;
import com.expense.entity.SavingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingGoalRepo extends JpaRepository<SavingGoal,Long> {

    @Query("SELECT s FROM SavingGoal s WHERE s.user.id =:userId")
    List<SavingGoal> findByUserId(@Param("userId") Long userId);

    Optional<Object> findByIdAndUserId(Long goalId, Long userId);
}
