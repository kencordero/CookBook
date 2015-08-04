package com.github.kencordero.cookbook.models;

public class Ingredient {
	private long mId;
	private String mName;
	
	// TODO - when adding ingredients, make sure one doesn't already exist
	public Ingredient() {
		mId = -1;
	}
	public Ingredient(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public long getId() {
		return mId;
	}
}
