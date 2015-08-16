package com.github.kencordero.cookbook.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.kencordero.cookbook.R;
import com.github.kencordero.cookbook.models.Cookbook;
import com.github.kencordero.cookbook.models.Recipe;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String EXTRA_RECIPE_ID = "com.github.kencordero.cookbook.recipe_id";
    private Recipe mRecipe;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance(UUID id) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECIPE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UUID recipeId = (UUID)getArguments().getSerializable(EXTRA_RECIPE_ID);
            mRecipe = Cookbook.get(getActivity()).getRecipe(recipeId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onRecipeUpdated(Recipe recipe);
    }

}
