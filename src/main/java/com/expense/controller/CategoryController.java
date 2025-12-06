package com.expense.controller;

import com.expense.dtos.CategoryRequestDto;
import com.expense.entity.Category;
import com.expense.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add-Category")
    public ResponseEntity<String> createCategory(@RequestParam Long userId, @RequestBody CategoryRequestDto dto){
        String msg = categoryService.saveCategory(userId, dto);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/getAll-Categories")
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam Long userId){
        List<Category> categoryList = categoryService.getAllCategories(userId);
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    @PutMapping("/update-Category/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam Long userId, @RequestBody CategoryRequestDto dto) {
        String msg = categoryService.updateCategory(id,userId,dto);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) {
        String msg = categoryService.deleteCategory(id, userId);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
}
