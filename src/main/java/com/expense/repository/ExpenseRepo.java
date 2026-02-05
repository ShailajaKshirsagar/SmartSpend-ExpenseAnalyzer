package com.expense.repository;

import com.expense.dtos.ExpenseResponseDto;
import com.expense.dtos.insights.CategorySummaryDto;
import com.expense.entity.Expense;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long> {

        // Fetch all expenses of one user
        @Query("SELECT e FROM Expense e WHERE e.user.id =:userId")
        List<Expense> findByUserId(@Param("userId") Long userId);

        //fetch expense by date
        @Query("SELECT e FROM Expense e WHERE e.user.id =:userId AND e.date =:date")
        List<Expense> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

        //expense by category
        @Query("SELECT e FROM Expense e WHERE e.user.id =:userId AND LOWER(e.category) = LOWER(:category)")
        List<Expense> findByCategory(@Param("userId") Long userId, @Param("category") String category);

        //expense by month
        @Query("SELECT e FROM Expense e WHERE e.user.id =:userId AND MONTH(e.date) =:month AND YEAR(e.date) =:year")
        List<Expense> findByMonth(@Param("userId") Long userId,
                                 @Param("month") int month,
                                 @Param("year") int year);

        //today's expense
        @Query("SELECT e FROM Expense e WHERE e.user.id =:userId AND e.date =:today")
        List<Expense> getTodaysExpenses(@Param("userId") Long userId, @Param("today") LocalDate today);

        //Monthly total
        @Query(""" 
        SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId 
        AND EXTRACT(MONTH FROM e.date) = :month
        AND EXTRACT(YEAR FROM e.date) = :year
        """)
        Double getMonthlyTotal(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

        //recent transactions/expeses
        @Query("SELECT e FROM Expense e WHERE e.user.id =:userId ORDER BY e.date DESC")
        Page<Expense> findRecentExpenses(@Param("userId") Long userId, Pageable pageable);

        //Total spent per category --> categorysummary
        @Query(""" 
        SELECT new com.expense.dtos.insights.CategorySummaryDto(INITCAP(LOWER(e.category)), SUM(e.amount)) FROM Expense e
        WHERE e.user.id =:userId GROUP BY LOWER(e.category)
        """)
        List<CategorySummaryDto> getCategorySummary(@Param("userId") Long userId);

        //monthly category summary
        @Query("""
        SELECT new com.expense.dtos.insights.CategorySummaryDto(INITCAP(LOWER(e.category)), SUM(e.amount))
        FROM Expense e WHERE e.user.id =:userId AND EXTRACT (MONTH FROM e.date) = :month AND EXTRACT(YEAR FROM e.date) = :year
        GROUP BY LOWER(e.category)
    """)
        List<CategorySummaryDto> getMonthlyCategorySummary(@Param("userId") Long userId,
                                                           @Param("month") int month, @Param("year") int year);

        //weekly or datewise
        @Query("""
        SELECT new com.expense.dtos.insights.CategorySummaryDto(INITCAP(LOWER(e.category)),SUM(e.amount))
        FROM Expense e WHERE e.user.id =:userId AND e.date BETWEEN :fromDate AND :toDate
        GROUP BY LOWER(e.category)
        """)
        List<CategorySummaryDto> getRangeCategorySummary(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate,
                @Param("toDate") LocalDate toDate
        );

        // Category-wise monthly total
        @Query("""
        select coalesce(sum(e.amount), 0)
        from Expense e
        where e.user.id = :userId
        and lower(e.category) = lower(:category)
        and extract(month from e.date) = :month
        and extract(year from e.date) = :year
        """)
        double getCategoryMonthlyTotal(
                Long userId,
                String category,
                int month,
                int year
        );


}
