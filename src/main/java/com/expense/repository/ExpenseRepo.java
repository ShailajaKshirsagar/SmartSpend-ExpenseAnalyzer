package com.expense.repository;

import com.expense.dtos.ExpenseResponseDto;
import com.expense.entity.Expense;
import org.springframework.cglib.core.Local;
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
        @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id =:userId AND MONTH(e.date) =:month AND YEAR(e.date) =:year")
        Double getMonthlyTotal(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

}
