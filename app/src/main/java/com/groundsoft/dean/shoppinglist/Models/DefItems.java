package com.groundsoft.dean.shoppinglist.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DefItems extends SQLiteOpenHelper {
    public Integer defItemId;
    public String defItemName;
    public Integer defItemOrder;

    static final String TABLE_DEFITEMS = "defaultitems";
    static final String TABLE_DEFITEMS_KEY_ID = "id";
    static final String TABLE_DEFITEMS_KEY_NAME = "name";
    static final String TABLE_DEFITEMS_KEY_ORDER = "defitorder";

    public DefItems(Context context) {
        super(context, DbConsts.DATABASE_NAME, null, DbConsts.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbConsts.createAll(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbConsts.upgradeAll(db, oldVersion, newVersion);
    }


    public void addDefItem(String name, Integer order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_DEFITEMS_KEY_NAME, name);
        vals.put(TABLE_DEFITEMS_KEY_ORDER, order);

        db.insert(TABLE_DEFITEMS, null, vals);
        db.close();
    }

    public DefItems getDefItem(Integer defItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_DEFITEMS,
                new String[]{TABLE_DEFITEMS_KEY_ID, TABLE_DEFITEMS_KEY_NAME, TABLE_DEFITEMS_KEY_ORDER},
                TABLE_DEFITEMS_KEY_ID + "=" + defItemId,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        DefItems defItems = new DefItems(null);

        while (cursor.moveToNext()) {

            defItems.defItemId = cursor.getInt(0);
            defItems.defItemName = cursor.getString(1);
            defItems.defItemOrder = cursor.getInt(2);

        }
        cursor.close();

        return defItems;
    }

    public ArrayList<DefItems> searchDefItems(String name, Integer limit) {
        ArrayList items = new ArrayList<DefItems>();

        String query = "select * from " + TABLE_DEFITEMS +
                " where " + TABLE_DEFITEMS_KEY_NAME + " like '%" + name + "%'" + " limit " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            DefItems item = new DefItems(null);
            item.defItemId = cursor.getInt(0);
            item.defItemName = cursor.getString(1);
            item.defItemOrder = cursor.getInt(2);

            items.add(item);
        }

        cursor.close();

        return items;
    }

}
