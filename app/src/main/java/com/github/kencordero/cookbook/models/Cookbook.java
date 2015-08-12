package com.github.kencordero.cookbook.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class Cookbook {
	private ArrayList<Recipe> mRecipes;
	
	private static Cookbook sCookbook;
	private Context mAppContext;
	
	private Cookbook(Context appContext) {
		mAppContext = appContext;
	}
	
	public static Cookbook get(Context c) {
		if (sCookbook == null)
			sCookbook = new Cookbook(c.getApplicationContext());
		return sCookbook;
	}
	
	public void addRecipe(Recipe r) {
		mRecipes.add(r);
	}
	
	public void removeRecipe(Recipe r) {
		mRecipes.remove(r);
	}
	
	public ArrayList<Recipe> getRecipes() {
		return mRecipes;
	}
	
	public Recipe getRecipe(UUID id) {
		for (Recipe r: mRecipes) {
			if (r.getUuid().equals(id))
				return r;
		}
		return null;
	}
}
