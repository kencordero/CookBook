package com.github.kencordero.cookbook.models;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe {
	private long mId;
	private UUID mUuid;
	private String mTitle;
	private ArrayList<Ingredient> mIngredients;
	// TODO model incomplete, missing directions, proportions, etc.
	
	public Recipe() {
		mUuid = UUID.randomUUID();
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
	
	public long getId() {
		return mId;
	}

	public UUID getUuid() {
		return mUuid;
	}
}
