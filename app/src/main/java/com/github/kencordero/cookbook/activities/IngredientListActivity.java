package com.github.kencordero.cookbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.github.kencordero.cookbook.fragments.IngredientFragment;
import com.github.kencordero.cookbook.fragments.IngredientListFragment;
import com.github.kencordero.cookbook.R;
import com.github.kencordero.cookbook.models.Ingredient;

public class IngredientListActivity extends SingleFragmentActivity implements
		IngredientListFragment.OnFragmentInteractionListener,
		IngredientFragment.OnFragmentInteractionListener {
    private static final String TAG = IngredientListActivity.class.getSimpleName();

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
		return new IngredientListFragment();
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterDetail;
	}

	@Override
	public void onIngredientUpdated(Ingredient ingredient) {
		FragmentManager fm = getSupportFragmentManager();
		IngredientListFragment listFragment = (IngredientListFragment)fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateUI();
	}

	@Override
	public void onIngredientSelected(Ingredient ingredient) {
        Log.i(TAG, "onIngredientSelected");
		if (findViewById(R.id.detailFragmentContainer) == null) {			
			// phone
			Intent i = new Intent(this, IngredientPagerActivity.class);
			i.putExtra(IngredientFragment.EXTRA_INGREDIENT_ID, ingredient.getUuid());
			startActivity(i);
		}
		else {
			// tablet
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = IngredientFragment.newInstance(ingredient.getUuid());
			
			if (oldDetail != null)
				ft.remove(oldDetail);
			
			ft.add(R.id.detailFragmentContainer, newDetail).commit();
		}
	}
}
