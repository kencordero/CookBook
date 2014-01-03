package com.github.kencordero.cookbook;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.kencordero.cookbook.models.Ingredient;
import com.github.kencordero.cookbook.models.Pantry;

public class IngredientFragment extends Fragment {
	public static final String EXTRA_INGREDIENT_ID = "com.github.kencordero.cookbook.ingredient";
	
	private Ingredient mIngredient;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		UUID ingredientId = (UUID)getArguments().getSerializable(EXTRA_INGREDIENT_ID);
		mIngredient = Pantry.get(getActivity()).getIngredient(ingredientId);
	}
}
