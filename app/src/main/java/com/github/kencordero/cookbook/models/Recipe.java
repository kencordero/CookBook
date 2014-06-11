package com.github.kencordero.cookbook.models;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe {
	private UUID mId;
	private String mTitle;
	private ArrayList<Ingredient> mIngredients;
	// TODO model incomplete, missing directions, proportions, etc.
	
	public Recipe() {
		mId = UUID.randomUUID();
	}

	public void addIngredient(Ingredient ingredient) {
		mIngredients.add(ingredient);
	}
	
	public ArrayList<Ingredient> getIngredients() {
		return mIngredients;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setTitle(String title) {
		mTitle = title;
	}
	
	public UUID getId() {
		return mId;
	}
}
