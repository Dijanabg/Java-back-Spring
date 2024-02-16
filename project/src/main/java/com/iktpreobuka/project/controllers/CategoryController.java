package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.CategoryEntity;


@RestController
@RequestMapping(path = "/project/categories")
public class CategoryController {
	List<CategoryEntity> categories = new ArrayList<>();
	private List<CategoryEntity> getDB() {
		if(categories.isEmpty()) {
			categories.add(new CategoryEntity(1, "music", "description 1"));
			categories.add(new CategoryEntity(2, "food", "description 2"));
			categories.add(new CategoryEntity(3, "entertainment", "description 3"));
		}
	return categories;
	}
	public CategoryController() {
	        categories = getDB(); 
	}
	
	@GetMapping
	public List<CategoryEntity> getAllCategory(){
		return categories;
	}
	
	@PostMapping
	public CategoryEntity addCategory(@RequestBody CategoryEntity newCategory) {
		categories.add(newCategory);
		return newCategory;
	}
	
	@DeleteMapping("/{id}")
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		for(Iterator<CategoryEntity> iterator = categories.iterator(); iterator.hasNext();) {
			CategoryEntity category = iterator.next();
			if(category.getId().equals(id)) {
				iterator.remove();
				return category;
			}
		}
		return null;
	}
	
	@GetMapping("/{id}")
	public CategoryEntity getCategorybyId(@PathVariable Integer id) {
		for(CategoryEntity category : categories) {
			if(category.getId().equals(id)) {
				return category;
			}
		}
		return null;
	}
	
}
