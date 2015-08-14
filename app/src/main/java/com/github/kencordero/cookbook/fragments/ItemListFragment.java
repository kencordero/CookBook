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
import com.github.kencordero.cookbook.models.Item;
import com.github.kencordero.cookbook.models.ItemCollection;

import java.util.ArrayList;

public class ItemListFragment extends ListFragment {
    private static final String TAG = ItemListFragment.class.getSimpleName();
    private ArrayList<Item> mItems;
    private OnFragmentInteractionListener mListener;

    /**
     * Required interface for the hosting activity
     */
    public interface OnFragmentInteractionListener {
        void onItemSelected(Item item);
    }

    public void updateUI() {
        ((ItemAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException exception) {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemListFragment.OnFragmentInteractionListener");
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
        mItems = ItemCollection.get(getActivity()).getItems();

        ItemAdapter adapter = new ItemAdapter(mItems);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        Item item = ((ItemAdapter)getListAdapter()).getItem(position);
        mListener.onItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_item_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_new_item:
                Item item = new Item();
                ItemCollection.get(getActivity()).addItem(item);
                updateUI();
                mListener.onItemSelected(item);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class ItemAdapter extends ArrayAdapter<Item> {
        public ItemAdapter(ArrayList<Item> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item, null);

            Item item = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id.item_list_item_textView);
            titleTextView.setText(item.getName());

            return convertView;
        }
    }
}
