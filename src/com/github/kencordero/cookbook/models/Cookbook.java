package com.github.kencordero.cookbook.models;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

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
	
	public Recipe getRecipe(UUID id) {
		for (Recipe r: mRecipes) {
			if (r.getId().equals(id))
				return r;
		}
		return null;
	}
}
