package com.expense.repository;

import com.expense.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface IncomeRepo extends JpaRepository<Income,Long> {

    @Query("SELECT i FROM Income i WHERE i.user.id =:userId")
    List<Income> findByUserId(@Param("userId") Long userId);
}
