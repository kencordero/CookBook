package com.github.kencordero.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.kencordero.cookbook.models.Ingredient;

import java.util.ArrayList;

/**
 * Created by kenneth on 8/3/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "cookbook";
    private static final int DB_VERSION = 1;

    private static final String TABLE_INGREDIENT = "ingredients";
    private static final String COL_ID = "_id";
    private static final String COL_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void closeDB() {
        Log.i(TAG, "closeDB");
        SQLiteDatabase db = getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        final String CREATE_TABLE_INGREDIENT = "CREATE TABLE " + TABLE_INGREDIENT + "("
                + COL_ID + " INTEGER PRIMARY KEY, " + COL_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_INGREDIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        onCreate(db);
    }

    public long createIngredient(Ingredient ingredient) {
        Log.i(TAG, "createIngredient");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, ingredient.getName());

        return db.insert(TABLE_INGREDIENT, null, values);
    }

    public int updateIngredient(Ingredient ingredient) {
        Log.i(TAG, "updateIngredient");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, ingredient.getId());
        values.put(COL_NAME, ingredient.getName());

        return db.update(TABLE_INGREDIENT, values, COL_ID + " = ?",
                new String[]{String.valueOf(ingredient.getId())});
    }

    public Ingredient getIngredient(long id) {
        Log.i(TAG, "getIngredient");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_INGREDIENT, new String[]{COL_NAME}, COL_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            cursor.close();

            return new Ingredient(id, name);
        } else return null;
    }

    public void deleteIngredient(long ingredient_id) {
        Log.i(TAG, "deleteIngredient");
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_INGREDIENT, COL_ID + " = ?", new String[] {String.valueOf(ingredient_id)});
    }

    public ArrayList<Ingredient> getAllIngredients() {
        Log.i(TAG, "getAllIngredients");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_INGREDIENT, null, null, null, null, null, COL_NAME);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                ingredients.add(new Ingredient(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }
}