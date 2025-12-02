package com.expense.repository;

import com.expense.entity.Category;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    @Query("SELECT c FROM Category c WHERE c.user.id =:userId")
    List<Category> findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Category c WHERE c.id =:id AND c.user.id =:userId")
    Optional<Category> findByIdAndUserId(@Param("id") Long id,@Param("userId") Long userId);

}
