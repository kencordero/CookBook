package com.github.kencordero.cookbook;

import android.support.v4.app.Fragment;

public class RecipeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new RecipeListFragment();
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterDetail;
	}
}
