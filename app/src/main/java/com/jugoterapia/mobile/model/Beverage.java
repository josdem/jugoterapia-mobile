package com.jugoterapia.mobile.model;

import java.io.UnsupportedEncodingException;

public class Beverage {
    private Integer id;
	private String name;
	private String ingredients;
	private String recipe;
	private Long categoryId;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}
	
	public String getRecipe() {
		return recipe;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getIngredients() {
		return ingredients;
	}

	@Override
	public String toString() {
        return name;
	}
	
}
