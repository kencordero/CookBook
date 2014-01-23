package com.github.kencordero.cookbook;

import android.support.v4.app.Fragment;

public class IngredientListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {		
		return new IngredientListFragment();
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterDetail;
	}
}
