package com.github.kencordero.cookbook;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.github.kencordero.cookbook.models.Ingredient;

public class IngredientListActivity extends SingleFragmentActivity
	implements IngredientListFragment.Callbacks, IngredientFragment.Callbacks {

	@Override
	protected Fragment createFragment() {		
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
		if (findViewById(R.id.detailFragmentContainer) == null) {			
			// phone
			Intent i = new Intent(this, IngredientPagerActivity.class);
			i.putExtra(IngredientFragment.EXTRA_INGREDIENT_ID, ingredient.getId());
			startActivity(i);
		}
		else {
			// tablet
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = IngredientFragment.newInstance(ingredient.getId());
			
			if (oldDetail != null)
				ft.remove(oldDetail);
			
			ft.add(R.id.detailFragmentContainer, newDetail).commit();
		}
	}
	
	
}
