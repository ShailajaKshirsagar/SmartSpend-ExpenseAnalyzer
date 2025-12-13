package com.expense.repository;

import com.expense.entity.CategoryBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryBudgetRepo extends JpaRepository<CategoryBudget,Long> {

    @Query("SELECT c FROM CategoryBudget c WHERE c.user.id =:userId AND " +
            "c.category.id =:categoryId AND c.month =:month AND c.year =:year")
    Optional<CategoryBudget> findByUserIdAndCategoryIdAndMonthAndYear(
            @Param("userId") Long userId, @Param("categoryId") Long categoryId,
            @Param("month") Integer month, @Param("year") Integer year);
}
