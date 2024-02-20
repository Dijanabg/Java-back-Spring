package com.iktpreobuka.project.services;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;
import com.iktpreobuka.project.repositories.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public CategoryEntity addCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public CategoryEntity getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }
    
    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
    //kod koji ne koristi izuzetak (odselektovati komentar u importu za optional)
//    @Override
//    public CategoryEntity updateCategory(Integer id, CategoryEntity categoryDetails) {
//        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(id);
//        if (categoryOptional.isPresent()) {
//            CategoryEntity categoryToUpdate = categoryOptional.get();
//            categoryToUpdate.setCategoryName(categoryDetails.getCategoryName());
//            categoryToUpdate.setCategoryDescription(categoryDetails.getCategoryDescription());
//            return categoryRepository.save(categoryToUpdate);
//        }
//        // Možete baciti izuzetak ako kategorija nije pronađena
//        return null; // ili baciti custom izuzetak
//    }
    //kod koji koristi izuzetak
    @Override
    public CategoryEntity updateCategory(Integer id, CategoryEntity categoryDetails) {
        CategoryEntity categoryToUpdate = categoryRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Category with id " + id + " not found."));
        categoryToUpdate.setCategoryName(categoryDetails.getCategoryName());
        categoryToUpdate.setCategoryDescription(categoryDetails.getCategoryDescription());
        return categoryRepository.save(categoryToUpdate);
    }
}
