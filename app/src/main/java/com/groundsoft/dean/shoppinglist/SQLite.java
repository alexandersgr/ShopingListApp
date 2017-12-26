package com.groundsoft.dean.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class SQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "com.groundsoft.dean.shoppinglist.db";

    private static final String TABLE_LISTS = "Lists";
    private static final String TABLE_LISTS_KEY_ID = "id";
    private static final String TABLE_LISTS_KEY_NAME = "name";
    private static final String TABLE_LISTS_KEY_DATE = "date";

    private static final String TABLE_ITEMS = "Items";
    private static final String TABLE_ITEMS_KEY_ID = "id";
    private static final String TABLE_ITEMS_FKEY_ID = "listid";
    private static final String TABLE_ITEMS_KEY_NAME = "name";
    private static final String TABLE_ITEMS_KEY_PRICE = "price";
    private static final String TABLE_ITEMS_KEY_QUANTITY = "quantity";
    private static final String TABLE_ITEMS_KEY_CHECKED = "checked";

    SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table " + TABLE_LISTS + " (" +
                TABLE_LISTS_KEY_ID + " integer primary key, " +
                TABLE_LISTS_KEY_NAME + " text, " +
                TABLE_LISTS_KEY_DATE + " integer)";
        sqLiteDatabase.execSQL(query);

        String query2 = "Create table " + TABLE_ITEMS + " (" +
                TABLE_ITEMS_KEY_ID + " integer primary key, " +
                TABLE_ITEMS_FKEY_ID + " integer, " +
                TABLE_ITEMS_KEY_NAME + " text, " +
                TABLE_ITEMS_KEY_PRICE + " integer, " +
                TABLE_ITEMS_KEY_QUANTITY + " integer, " +
                TABLE_ITEMS_KEY_CHECKED + " integer)";
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_LISTS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_ITEMS);
        onCreate(sqLiteDatabase);
    }

    public void fill() {
        for (int i = 0; i <= 20; i++) {
            addList("List " + i, (int) (long) (System.currentTimeMillis() / 1000));
        }

        for (int i = 0; i <= 5; i++) {
            addItem(20, "Item of list 20 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(19, "Item of list 19 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(18, "Item of list 18 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(17, "Item of list 17 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(16, "Item of list 16 #" + i, 0, 1);
        }
    }

    // ------------------------------------------------------//

    public void addItem(Integer listid, String name, Integer price, Integer quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_ITEMS_FKEY_ID, listid);
        vals.put(TABLE_ITEMS_KEY_NAME, name);
        vals.put(TABLE_ITEMS_KEY_PRICE, price);
        vals.put(TABLE_ITEMS_KEY_QUANTITY, quantity);
        vals.put(TABLE_ITEMS_KEY_CHECKED, 0);

        db.insert(TABLE_ITEMS, null, vals);
        db.close();
    }

    public String getFirstItems(Integer listid, Integer limit) {
        String res = "";
        ArrayList items = new ArrayList<Items>();
        String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_FKEY_ID + " = " + listid + " limit " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Items item = new Items();
            item.name = cursor.getString(2);
            item.quantity = cursor.getInt(4);
            item.checked = cursor.getInt(5);
            items.add(item);
            res += cursor.getString(2) + " ";
        }


        cursor.close();
        return res;
    }

    public ArrayList<Items> getItems(Integer listid) {
        ArrayList items = new ArrayList<Items>();

        String query = "select * from " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Items item = new Items();
            item.id = cursor.getInt(0);
            item.listid = cursor.getInt(1);
            item.name = cursor.getString(2);
            item.price = cursor.getInt(3);
            item.quantity = cursor.getInt(4);
            item.checked = cursor.getInt(5);

            items.add(item);
        }

        cursor.close();

        return items;
    }


    // ------------------------------------------------------//

    public void addList(String name, Integer date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_LISTS_KEY_NAME, name);
        vals.put(TABLE_LISTS_KEY_DATE, date);

        db.insert(TABLE_LISTS, null, vals);
        db.close();
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
