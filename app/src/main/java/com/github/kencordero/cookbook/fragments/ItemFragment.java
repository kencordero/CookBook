package com.github.kencordero.cookbook.fragments;

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

import com.github.kencordero.cookbook.R;
import com.github.kencordero.cookbook.models.Item;
import com.github.kencordero.cookbook.models.ItemCollection;

import java.util.UUID;

public class ItemFragment extends Fragment {
    private static final String TAG = ItemFragment.class.getSimpleName();
    public static final String EXTRA_ITEM_ID = "com.github.kencordero.cookbook.item_id";

    private Item mItem;
    private OnFragmentInteractionListener mListener;

    /**
     * Required interface for hosting activities.
     */
    public interface OnFragmentInteractionListener {
        void onItemUpdated(Item item);
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

        UUID itemId = (UUID)getArguments().getSerializable(EXTRA_ITEM_ID);
        mItem = ItemCollection.get(getActivity()).getItem(itemId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_item, parent, false);

        EditText nameField = (EditText) v.findViewById(R.id.itemName);
        nameField.setText(mItem.getName());
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
                mItem.setName(s.toString());
                getActivity().setTitle(mItem.getName());
            }
        });
        return v;
    }

    public static ItemFragment newInstance(UUID itemId) {
        Log.i(TAG, "newInstance");
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_ITEM_ID, itemId);

        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: " + mItem.getName());
        ItemCollection.get(getActivity()).saveItem(mItem);
    }

}
