package com.github.kencordero.cookbook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.kencordero.cookbook.models.Cookbook;
import com.github.kencordero.cookbook.models.Recipe;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {
	private ArrayList<Recipe> mCookbook;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		mCookbook = Cookbook.get(getActivity()).getRecipes();
	}

	public void updateUI() {

	}

	public interface OnFragmentInteractionListener {
		void onRecipeSelected(Recipe recipe);
	}
}
