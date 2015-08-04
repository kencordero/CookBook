package com.github.kencordero.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.kencordero.cookbook.models.Ingredient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 8/3/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "cookbook";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase mDatabase;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void open() throws SQLException {
        mDatabase = getWritableDatabase();
    }

    public void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

        private static final String TABLE_INGREDIENT = "ingredients";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String CREATE_TABLE_INGREDIENT = "CREATE TABLE " + TABLE_INGREDIENT + "("
                    + COL_ID + " INTEGER PRIMARY KEY, " + COL_NAME + " TEXT)";
            db.execSQL(CREATE_TABLE_INGREDIENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
            onCreate(db);
        }

        public long createIngredient(Ingredient ingredient) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_NAME, ingredient.getName());

            return db.insert(TABLE_INGREDIENT, null, cv);
        }

        public Ingredient getIngredient(long ingredient_id) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_INGREDIENT, new String[] {COL_NAME}, COL_ID + " = ?",
                    new String[] {Long.toString(ingredient_id)}, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }

            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            cursor.close();
            return new Ingredient(name);
        }

        public List<Ingredient> getAllIngredients() {
            List<Ingredient> ingredients = new ArrayList<>();

            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_INGREDIENT, null, null, null, null, null, null, COL_NAME);

            if (cursor.moveToFirst()) {
                do {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));

                    ingredients.add(ingredient);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return ingredients;
        }
    }