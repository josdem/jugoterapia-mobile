package com.jugoterapia.android.bean;

import java.util.List;

import com.jugoterapia.android.model.Category;

public class CategoryWrapper {
	private List<Category> categories;
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
}