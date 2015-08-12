package com.github.kencordero.cookbook;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.kencordero.cookbook.models.Ingredient;
import com.github.kencordero.cookbook.models.Pantry;

import java.util.UUID;

public class IngredientFragment extends Fragment {
	private static final String TAG = IngredientFragment.class.getSimpleName();
	public static final String EXTRA_INGREDIENT_ID = "com.github.kencordero.cookbook.ingredient_id";
	
	private Ingredient mIngredient;
	private OnFragmentInteractionListener mListener;
	
	/**
	 * Required interface for hosting activities.
	 */
	public interface OnFragmentInteractionListener {
		void onIngredientUpdated(Ingredient ingredient);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (OnFragmentInteractionListener)activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.i(TAG, "onCreate");
		
		UUID ingredientId = (UUID)getArguments().getSerializable(EXTRA_INGREDIENT_ID);
		mIngredient = Pantry.get(getActivity()).getIngredient(ingredientId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		View v = inflater.inflate(R.layout.fragment_ingredient, parent, false);

		EditText nameField = (EditText) v.findViewById(R.id.ingredientName);
		nameField.setText(mIngredient.getName());
		nameField.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mIngredient.setName(s.toString());
				getActivity().setTitle(mIngredient.getName());
			}
		});
		return v;				
	}
	
	public static IngredientFragment newInstance(UUID ingredientId) {
		Log.i(TAG, "newInstance");
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_INGREDIENT_ID, ingredientId);
		
		IngredientFragment fragment = new IngredientFragment();
		fragment.setArguments(bundle);
		
		return fragment;
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause: " + mIngredient.getName());
		Pantry.get(getActivity()).saveIngredient(mIngredient);
	}
		
}
