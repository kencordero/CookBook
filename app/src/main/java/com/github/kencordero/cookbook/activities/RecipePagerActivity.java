package com.github.kencordero.cookbook.activities;

import android.view.Menu;
import android.view.MenuItem;

import com.github.kencordero.cookbook.R;
import com.github.kencordero.cookbook.fragments.RecipeFragment;
import com.github.kencordero.cookbook.models.Recipe;

public class RecipePagerActivity extends ItemPagerActivity
    implements RecipeFragment.OnFragmentInteractionListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecipeUpdated(Recipe recipe) {

    }
}
