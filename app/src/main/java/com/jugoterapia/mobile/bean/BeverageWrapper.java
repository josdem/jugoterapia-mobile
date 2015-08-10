package com.jugoterapia.mobile.bean;

import java.util.List;

import com.jugoterapia.mobile.model.Beverage;

public class BeverageWrapper {
	private List<Beverage> beverages;
	
	public void setBeverages(List<Beverage> beverages) {
		this.beverages = beverages;
	}
	
	public List<Beverage> getBeverages() {
		return beverages;
	}
	
}
