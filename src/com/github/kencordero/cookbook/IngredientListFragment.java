package com.github.kencordero.cookbook;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.github.kencordero.cookbook.models.Ingredient;
import com.github.kencordero.cookbook.models.Pantry;

public class IngredientListFragment extends ListFragment {
	private ArrayList<Ingredient> mPantry;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		mPantry = Pantry.get(getActivity()).getIngredients();
	}
}
