package com.groundsoft.dean.shoppinglist.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.groundsoft.dean.shoppinglist.ShoppingListMainActivity;

public class Categories1 extends SQLiteOpenHelper {
    public Integer categoryId;
    public String categoryName;
    public Integer categoryOrder;

    static final String TABLE_CATEGORIES = "categories";
    static final String TABLE_CATEGORIES_KEY_ID = "id";
    static final String TABLE_CATEGORIES_KEY_NAME = "name";
    static final String TABLE_CATEGORIES_KEY_ORDER = "catorder";
    private SQLiteDatabase dba;

    public Categories1(Context context) {
        super(context, DbConsts.DATABASE_NAME, null, DbConsts.DATABASE_VERSION);

        dba = ShoppingListMainActivity.dba;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbConsts.createAll(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbConsts.upgradeAll(db, oldVersion, newVersion);
    }


    public void addCategory(String name, Integer order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_CATEGORIES_KEY_NAME, name);
        vals.put(TABLE_CATEGORIES_KEY_ORDER, order);

        db.insert(TABLE_CATEGORIES, null, vals);
        db.close();
    }

    public Categories1 getCategory(Integer categoryOrder) {
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = dba.query(TABLE_CATEGORIES,
                new String[]{TABLE_CATEGORIES_KEY_ID, TABLE_CATEGORIES_KEY_NAME, TABLE_CATEGORIES_KEY_ORDER},
                TABLE_CATEGORIES_KEY_ORDER + "=" + categoryOrder,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        Categories1 categories = new Categories1(null);

        while (cursor.moveToNext()) {

            categories.categoryId = cursor.getInt(0);
            categories.categoryName = cursor.getString(1);
            categories.categoryOrder = cursor.getInt(2);

        }
        cursor.close();

        return categories;
    }
}
