package com.iktpreobuka.project.entities;

public class CategoryEntity {

	private Integer id;
    private String CategoryName;
    private String CategoryDescription;
	public CategoryEntity() {
		super();
	}
	public CategoryEntity(Integer id, String categoryName, String categoryDescription) {
		super();
		this.id = id;
		CategoryName = categoryName;
		CategoryDescription = categoryDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	public String getCategoryDescription() {
		return CategoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		CategoryDescription = categoryDescription;
	}
	@Override
	public String toString() {
		return "CategoryEntity [id=" + id + ", CategoryName=" + CategoryName + ", CategoryDescription="
				+ CategoryDescription + "]";
	}
    
}
