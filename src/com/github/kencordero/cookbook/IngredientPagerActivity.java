package com.github.kencordero.cookbook;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.kencordero.cookbook.models.Ingredient;
import com.github.kencordero.cookbook.models.Pantry;

public class IngredientPagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private ArrayList<Ingredient> mIngredients;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mIngredients = Pantry.get(this).getIngredients();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return mIngredients.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Ingredient ingredient = mIngredients.get(pos);
				return IngredientFragment.newInstance(ingredient.getId());
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) { }

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }

			@Override
			public void onPageSelected(int pos) {
				Ingredient ingredient = mIngredients.get(pos);
				if (ingredient.getName() != null) {
					setTitle(ingredient.getName());
				}
			}
			
		});
		
		UUID ingredientId = (UUID)getIntent().getSerializableExtra(IngredientFragment.EXTRA_INGREDIENT_ID);
		for (int i = 0; i < mIngredients.size(); ++i) {
			if (mIngredients.get(i).getId().equals(ingredientId)) {
				mViewPager.setCurrentItem(i);
				
				// onPageSelected not called when i == 0
				if (i == 0 && mIngredients.get(i).getName() != null)
					setTitle(mIngredients.get(i).getName());
				break;
			}
		}
	}
	
	public void onIngredientUpdated(Ingredient ingredient) { }
}
