package com.github.kencordero.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.github.kencordero.cookbook.models.Recipe;

public class RecipeListActivity extends SingleFragmentActivity implements
		RecipeFragment.OnFragmentInteractionListener,
		RecipeListFragment.OnFragmentInteractionListener {
	private static final String TAG =  RecipeListActivity.class.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		ActionBar ab = getSupportActionBar();
		if (ab != null) {
			ab.setIcon(R.drawable.ic_launcher);
			ab.setDisplayShowHomeEnabled(true);
		}
	}

	@Override
	protected Fragment createFragment() {
		Log.i(TAG, "createFragment");
		return new RecipeListFragment();
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterDetail;
	}

	@Override
	public void onRecipeUpdated(Recipe recipe) {
		FragmentManager fm = getSupportFragmentManager();
		RecipeListFragment listFragment = (RecipeListFragment)fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateUI();
	}

	@Override
	public void onRecipeSelected(Recipe recipe) {
		Log.i(TAG, "onIngredientSelected");
		if (findViewById(R.id.detailFragmentContainer) == null) {
			// phone
			Intent intent = new Intent(this, RecipePagerActivity.class);
			intent.putExtra(RecipeFragment.EXTRA_RECIPE_ID, recipe.getUuid());
			startActivity(intent);
		}
		else {
			// tablet
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = RecipeFragment.newInstance(recipe.getUuid());

			if (oldDetail != null)
				ft.remove(oldDetail);

			ft.add(R.id.detailFragmentContainer, newDetail).commit();
		}
	}


}
