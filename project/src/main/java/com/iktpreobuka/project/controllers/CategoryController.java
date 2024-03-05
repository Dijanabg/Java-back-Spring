package com.iktpreobuka.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.security.Views;
import com.iktpreobuka.project.services.BillService;
import com.iktpreobuka.project.services.CategoryService;
import com.iktpreobuka.project.services.OfferService;



@RestController
@RequestMapping(path = "/project/categories")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private BillService billService;

    @GetMapping
    @JsonView(Views.Public.class)
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryEntity addCategory(@RequestBody CategoryEntity newCategory) {
        return categoryService.addCategory(newCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategor(@PathVariable Integer id) {
    	if(offerService.existsActiveOffersForCategory(id)) {
            return ResponseEntity.badRequest().body("Cannot delete category with active offers.");
        }
        if(billService.existsActiveBillsForCategory(id)) {
            return ResponseEntity.badRequest().body("Cannot delete category with active bills.");
        }
        // Proces brisanja kategorije ako nema aktivnih ponuda ili računa
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public CategoryEntity getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }
    
    @PutMapping("/{id}")
    public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity categoryDetails) {
        return categoryService.updateCategory(id, categoryDetails);
    }
}
