package com.github.kencordero.cookbook;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.kencordero.cookbook.models.Ingredient;
import com.github.kencordero.cookbook.models.Pantry;

public class IngredientListFragment extends ListFragment {
	private ArrayList<Ingredient> mPantry;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		mPantry = Pantry.get(getActivity()).getIngredients();
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
