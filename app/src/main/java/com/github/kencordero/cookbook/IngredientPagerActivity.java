package com.github.kencordero.cookbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.kencordero.cookbook.models.Ingredient;
import com.github.kencordero.cookbook.models.Pantry;

import java.util.ArrayList;
import java.util.UUID;

public class IngredientPagerActivity extends AppCompatActivity
	implements IngredientFragment.Callbacks {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.fragment_ingredient, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_save_and_add_another:
				Ingredient ingredient = new Ingredient();
				Pantry.get(this).addIngredient(ingredient);
				mViewPager.getAdapter().notifyDataSetChanged();
				mViewPager.setCurrentItem(mIngredients.size() - 1, true);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onIngredientUpdated(Ingredient ingredient) { }
}
