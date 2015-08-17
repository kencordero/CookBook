package com.github.kencordero.cookbook.fragments;

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

import com.github.kencordero.cookbook.R;
import com.github.kencordero.cookbook.models.Cookbook;
import com.github.kencordero.cookbook.models.Recipe;

import java.util.ArrayList;

public class RecipeListFragment extends ListFragment {
	private static final String TAG = RecipeListFragment.class.getSimpleName();
	private ArrayList<Recipe> mRecipes;
	private OnFragmentInteractionListener mListener;

	/**
	 * Required interface for the hosting activity
	 */
	public interface OnFragmentInteractionListener {
		void onRecipeSelected(Recipe recipe);
	}

	public void updateUI() {
		((RecipeAdapter)getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException exception) {
			throw new ClassCastException(activity.toString()
					+ " must implement RecipeListFragment.OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.i(TAG, "onCreate");
		setHasOptionsMenu(true);
		mRecipes = Cookbook.get(getActivity()).getRecipes();

		RecipeAdapter adapter = new RecipeAdapter(mRecipes);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Recipe recipe = ((RecipeAdapter)getListAdapter()).getItem(position);
		mListener.onRecipeSelected(recipe);
	}

	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_recipe_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_new_recipe:
				Recipe recipe = new Recipe();
				Cookbook.get(getActivity()).addRecipe(recipe);
				updateUI();
				mListener.onRecipeSelected(recipe);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private class RecipeAdapter extends ArrayAdapter<Recipe> {
		public RecipeAdapter(ArrayList<Recipe> recipes) {
			super(getActivity(), 0, recipes);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item, null);

			Recipe recipe = getItem(position);

			TextView titleTextView = (TextView)convertView.findViewById(R.id.item_list_item_textView);
			titleTextView.setText(recipe.getTitle());

			return convertView;
		}
	}
}
