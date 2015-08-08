package com.github.kencordero.cookbook.models;

import android.content.Context;
import android.os.Build;

import com.github.kencordero.cookbook.DatabaseHelper;

import java.util.ArrayList;
import java.util.UUID;

public class Pantry {
	private ArrayList<Ingredient> mIngredients;
	
	private static Pantry sPantry;
	private Context mAppContext;
	private DatabaseHelper mDb;
	
	private Pantry(Context appContext) {
		mAppContext = appContext;

		mDb = new DatabaseHelper(mAppContext);
		mIngredients = mDb.getAllIngredients();

	}
	
	public static Pantry get(Context c) {
		if (sPantry == null)
			sPantry = new Pantry(c.getApplicationContext());
		return sPantry;
	}
	
	public void addIngredient(Ingredient i) {
		mIngredients.add(i);
	}

	public void saveIngredient(Ingredient i) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
			if (i.getName().isEmpty()) return;
		if (i.getId() == -1) {
			long id = mDb.createIngredient(i);
			i.setId(id);
		} else {
			mDb.updateIngredient(i);
		}
	}
	
	public void removeIngredient(Ingredient i) {
		mIngredients.remove(i);
	}
	
	public ArrayList<Ingredient> getIngredients() {
		return mIngredients;
	}
	
	public Ingredient getIngredient(long id) {
		for (Ingredient i: mIngredients) {
			if (i.getId() == id)
				return i;
		}
		return null;
	}

	public Ingredient getIngredient(UUID id) {
		for (Ingredient i: mIngredients) {
			if (i.getUuid().equals(id))
				return i;
		}
		return null;
	}

	public void dispose() {
		mDb.closeDB();
	}
}
