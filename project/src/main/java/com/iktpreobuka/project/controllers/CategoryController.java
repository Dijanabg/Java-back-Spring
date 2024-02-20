package com.iktpreobuka.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.services.CategoryServiceImpl;


@RestController
@RequestMapping(path = "/project/categories")
public class CategoryController {
	@Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }

    @PostMapping
    public CategoryEntity addCategory(@RequestBody CategoryEntity newCategory) {
        return categoryServiceImpl.addCategory(newCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryServiceImpl.deleteCategory(id);
    }

    @GetMapping("/{id}")
    public CategoryEntity getCategoryById(@PathVariable Integer id) {
        return categoryServiceImpl.getCategoryById(id);
    }
    
    @PutMapping("/{id}")
    public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity categoryDetails) {
        return categoryServiceImpl.updateCategory(id, categoryDetails);
    }
}
