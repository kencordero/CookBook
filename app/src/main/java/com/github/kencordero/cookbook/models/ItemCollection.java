package com.github.kencordero.cookbook.models;

import android.content.Context;
import android.os.Build;

import com.github.kencordero.cookbook.DatabaseHelper;

import java.util.ArrayList;
import java.util.UUID;

public class ItemCollection {
    private ArrayList<Item> mItems;

    private static ItemCollection sCollection;
    private Context mAppContext;
    private DatabaseHelper mDb;

    private ItemCollection(Context appContext) {
        mAppContext = appContext;

        mDb = new DatabaseHelper(mAppContext);
        //mItems = mDb.getAllItems();

    }

    public static ItemCollection get(Context c) {
        if (sCollection == null)
            sCollection = new ItemCollection(c.getApplicationContext());
        return sCollection;
    }

    public void addItem(Item item) {
        mItems.add(item);
    }

    public void saveItem(Item item) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            if (item.getName().isEmpty()) return;
        if (item.getId() == -1) {
            //long id = mDb.createItem(item);
            //item.setId(id);
        } else {
            //mDb.updateItem(item);
        }
    }

    public void removeItem(Item item) {
        mItems.remove(item);
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public Item getItem(long id) {
        for (Item i: mItems) {
            if (i.getId() == id)
                return i;
        }
        return null;
    }

    public Item getItem(UUID id) {
        for (Item i: mItems) {
            if (i.getUuid().equals(id))
                return i;
        }
        return null;
    }

    public void dispose() {
        mDb.closeDB();
    }
}
