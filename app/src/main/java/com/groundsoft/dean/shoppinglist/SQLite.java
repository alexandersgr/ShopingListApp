package com.groundsoft.dean.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.groundsoft.dean.shoppinglist.Models.Categories;
import com.groundsoft.dean.shoppinglist.Models.DefItems;
import com.groundsoft.dean.shoppinglist.Models.Items;
import com.groundsoft.dean.shoppinglist.Models.Lists;

import java.util.ArrayList;


public class SQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "com.groundsoft.dean.shoppinglist.db";

     public static final String TABLE_LISTS = "lists";
    public static final String TABLE_LISTS_KEY_ID = "id";
    public static final String TABLE_LISTS_KEY_NAME = "name";
    public static final String TABLE_LISTS_KEY_DATE = "date";





    public static final String TABLE_DEFITEMS = "defaultitems";
    public static final String TABLE_DEFITEMS_KEY_ID = "id";
    public static final String TABLE_DEFITEMS_KEY_NAME = "name";
    public static final String TABLE_DEFITEMS_KEY_ORDER = "defitorder";

    SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        onCreate(sqLiteDatabase);
    }



    // ------------------------- Default items -----------------------------//

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

        DefItems defItems = new DefItems();

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
            DefItems item = new DefItems();
            item.defItemId = cursor.getInt(0);
            item.defItemName = cursor.getString(1);
            item.defItemOrder = cursor.getInt(2);

            items.add(item);
        }

        cursor.close();

        return items;
    }

    // ------------------------- Categories -----------------------------//


    // ------------------------- Items -----------------------------//


    // ------------------------- Lists -----------------------------//

    public long addList(String name, Integer date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_LISTS_KEY_NAME, name);
        vals.put(TABLE_LISTS_KEY_DATE, date);

        long res = db.insert(TABLE_LISTS, null, vals);
        db.close();
        return res;
    }

    public void addList(Lists li) {
        addList(li.listname, li.date);
    }

    public Lists getList(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_LISTS,
                new String[]{TABLE_LISTS_KEY_ID, TABLE_LISTS_KEY_NAME, TABLE_LISTS_KEY_DATE},
                TABLE_LISTS_KEY_ID + "=" + id,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        Lists li = new Lists();

        while (cursor.moveToNext()) {

            li.id = cursor.getInt(0);
            li.listname = cursor.getString(1);
            li.date = cursor.getInt(2);

        }
        cursor.close();

        return li;
    }

    public ArrayList<Lists> getAllLists() {
        ArrayList listitems = new ArrayList<Lists>();

        String query = "select * from " + TABLE_LISTS + " order by date desc";  //id desc date desc
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Lists li = new Lists(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            );
            listitems.add(li);
        }

        cursor.close();

        return listitems;
    }
}
