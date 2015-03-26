package com.jugoterapia.android.bean;

import java.util.List;

import com.jugoterapia.android.model.Beverage;

public class BeverageWrapper {
	private List<Beverage> beverages;
	
	public void setBeverages(List<Beverage> beverages) {
		this.beverages = beverages;
	}
	
	public List<Beverage> getBeverages() {
		return beverages;
	}
	
}
