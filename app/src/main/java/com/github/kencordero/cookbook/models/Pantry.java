package com.github.kencordero.cookbook.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class Pantry {
	private ArrayList<Ingredient> mIngredients;
	
	private static Pantry sPantry;
	private Context mAppContext;
	
	private Pantry(Context appContext) {
		mAppContext = appContext;

		mIngredients = new ArrayList<Ingredient>();
	}
	
	public static Pantry get(Context c) {
		if (sPantry == null)
			sPantry = new Pantry(c.getApplicationContext());
		return sPantry;
	}
	
	public void addIngredient(Ingredient i) {
		mIngredients.add(i);
	}
	
	public void removeIngredient(Ingredient i) {
		mIngredients.remove(i);
	}
	
	public ArrayList<Ingredient> getIngredients() {
		return mIngredients;
	}
	
	public Ingredient getIngredient(UUID id) {
		for (Ingredient i: mIngredients) {
			if (i.getId().equals(id))
				return i;
		}
		return null;
	}

}