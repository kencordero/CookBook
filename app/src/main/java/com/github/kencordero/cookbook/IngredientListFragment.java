package com.github.kencordero.cookbook;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.kencordero.cookbook.models.Ingredient;
import com.github.kencordero.cookbook.models.Pantry;

import java.util.ArrayList;

public class IngredientListFragment extends ListFragment {
	private static final String TAG = IngredientListFragment.class.getSimpleName();
	private ArrayList<Ingredient> mIngredients;
	private Callbacks mCallbacks;
	
	/**
	 * Required interface for the hosting activity
	 */
	public interface Callbacks {
		void onIngredientSelected(Ingredient ingredient);
	}
	
	public void updateUI() {
		((IngredientAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (Callbacks)activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.i(TAG, "onCreate");
		setHasOptionsMenu(true);
		mIngredients = Pantry.get(getActivity()).getIngredients();
		
		IngredientAdapter adapter = new IngredientAdapter(mIngredients);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Ingredient i = ((IngredientAdapter)getListAdapter()).getItem(position);
		mCallbacks.onIngredientSelected(i);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_ingredient_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_ingredient:
			Ingredient ingredient = new Ingredient();
			Pantry.get(getActivity()).addIngredient(ingredient);
			updateUI();
			mCallbacks.onIngredientSelected(ingredient);
			return true;
		default:
			return super.onOptionsItemSelected(item);		
		}
	}
	
	private class IngredientAdapter extends ArrayAdapter<Ingredient> {
		public IngredientAdapter(ArrayList<Ingredient> ingredients) {
			super(getActivity(), 0, ingredients);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_ingredient, null);			
			
			Ingredient ingredient = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.ingredient_list_item_textView);
			titleTextView.setText(ingredient.getName());
						
			return convertView;
		}
	}
}
