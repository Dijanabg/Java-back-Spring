package com.iktpreobuka.project.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.security.Views;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Integer id;
	
	@Column(name = "category_name", nullable = false)
	@JsonView(Views.Public.class)
	@NotBlank(message = "Category name must be provided.")
    private String categoryName;
	
	@Column(name = "category_description", nullable = false)
	@JsonView(Views.Public.class)
	@NotBlank(message = "Category description must be provided.")
	@Size(max=30, message = "Category description must be under {max} characters long.")
    private String categoryDescription;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfferEntity> offers = new ArrayList<>();
	//orphanRemoval = true je opcija u JPA (Java Persistence API) koja se koristi u kontekstu veze između entiteta, konkretno za one veze koje su oznacene sa @OneToMany ili @OneToOne. Ova opcija omogućava automatsko brisanje "siročića" (orphan) entiteta kada se oni više ne referenciraju iz "roditeljskog" entiteta.
	
	public CategoryEntity() {
		super();
	}
	public CategoryEntity(Integer id, String categoryName, String categoryDescription) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	@Override
	public String toString() {
		return "CategoryEntity [id=" + id + ", CategoryName=" + categoryName + ", CategoryDescription="
				+ categoryDescription + "]";
	}
    
}
