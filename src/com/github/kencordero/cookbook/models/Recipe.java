package com.github.kencordero.cookbook.models;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe {
	private UUID mId;
	private String mTitle;
	private ArrayList<Ingredient> mIngredients;
	
	public Recipe(String title) {
		mId = UUID.randomUUID();
		mTitle = title;
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
}
