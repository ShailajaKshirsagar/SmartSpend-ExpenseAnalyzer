package com.expense.repository;

import com.expense.dtos.otp.BillResponseDto;
import com.expense.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

    //for upcoming bills
    @Query("SELECT b FROM Bill b WHERE b.userId =:userId AND b.dueDate >=:date AND b.isPaid =false")
    List<Bill> findByUserIdAndDueDateGreaterThanEqualAndIsPaidFalse(@Param("userId") Long userId, @Param("date") LocalDate date);

    //for overdue bills
    @Query("SELECT b FROM Bill b WHERE b.userId =:userId AND b.dueDate <:date AND b.isPaid =false")
    List<Bill> findByUserIdAndDueDateLessThanAndIsPaidFalse(@Param("userId") Long userId,@Param("date") LocalDate date);
}
