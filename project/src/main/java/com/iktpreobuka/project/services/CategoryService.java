package com.iktpreobuka.project.services;

import java.util.List;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;

public interface CategoryService {
	List<CategoryEntity> getAllCategories();
	CategoryEntity addCategory(CategoryEntity category);
    CategoryEntity getCategoryById(Integer id) throws ResourceNotFoundException;
    void deleteCategory(Integer id);
    CategoryEntity updateCategory(Integer id, CategoryEntity categoryDetails);
}
