package com.github.kencordero.cookbook.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.kencordero.cookbook.R;
import com.github.kencordero.cookbook.fragments.ItemFragment;
import com.github.kencordero.cookbook.models.Item;
import com.github.kencordero.cookbook.models.ItemCollection;

import java.util.ArrayList;
import java.util.UUID;

public class ItemPagerActivity extends AppCompatActivity
        implements ItemFragment.OnFragmentInteractionListener {
    private static final String TAG = ItemPagerActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private ArrayList<Item> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mItems = ItemCollection.get(this).getItems();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public Fragment getItem(int pos) {
                Item item = mItems.get(pos);
                return ItemFragment.newInstance(item.getUuid());
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int pos) {
                Item item = mItems.get(pos);
                if (item.getName() != null) {
                    setTitle(item.getName());
                }
            }

        });

        UUID itemId = (UUID)getIntent().getSerializableExtra(ItemFragment.EXTRA_ITEM_ID);
        for (int i = 0; i < mItems.size(); ++i) {
            if (mItems.get(i).getUuid().equals(itemId)) {
                mViewPager.setCurrentItem(i);

                // onPageSelected not called when i == 0
                if (i == 0) {
                    if (mItems.get(i).getName() != null) {
                        setTitle(mItems.get(i).getName());
                    } else {
                        setTitle(R.string.app_name);
                    }
                }
                break;
            }
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setIcon(R.drawable.ic_launcher);
            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP,
                    ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.i(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.fragment_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.menu_item_save_and_add_another:
                ItemCollection.get(this).addItem(new Item());
                mViewPager.getAdapter().notifyDataSetChanged();
                mViewPager.setCurrentItem(mItems.size() - 1, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onItemUpdated(Item item) {
        Log.i(TAG, "onItemUpdated");
    }

    @Override
    public void onStop() {
        super.onStop();
        ItemCollection.get(this).dispose();
    }
}
