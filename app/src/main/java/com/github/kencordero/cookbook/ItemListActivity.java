package com.github.kencordero.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.github.kencordero.cookbook.models.Item;

public class ItemListActivity extends SingleFragmentActivity implements
        ItemListFragment.OnFragmentInteractionListener,
        ItemFragment.OnFragmentInteractionListener {
    private static final String TAG = ItemListActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setIcon(R.drawable.ic_launcher);
            ab.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected Fragment createFragment() {
        Log.i(TAG, "createFragment");
        return new ItemListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterDetail;
    }

    @Override
    public void onItemUpdated(Item item) {
        FragmentManager fm = getSupportFragmentManager();
        ItemListFragment listFragment = (ItemListFragment)fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }

    @Override
    public void onItemSelected(Item item) {
        Log.i(TAG, "onItemSelected");
        if (findViewById(R.id.detailFragmentContainer) == null) {
            // phone
            Intent i = new Intent(this, ItemPagerActivity.class);
            i.putExtra(ItemFragment.EXTRA_ITEM_ID, item.getUuid());
            startActivity(i);
        }
        else {
            // tablet
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = ItemFragment.newInstance(item.getUuid());

            if (oldDetail != null)
                ft.remove(oldDetail);

            ft.add(R.id.detailFragmentContainer, newDetail).commit();
        }
    }
}
