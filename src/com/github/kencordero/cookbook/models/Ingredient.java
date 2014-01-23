package com.github.kencordero.cookbook.models;

import java.util.UUID;

public class Ingredient {
	private UUID mId;
	private String mName;
	
	// TODO - when adding ingredients, make sure one doesn't already exist
	public Ingredient() {
		mId = UUID.randomUUID();
	}
		
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public UUID getId() {
		return mId;
	}
}
