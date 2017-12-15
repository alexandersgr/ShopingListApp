package com.groundsoft.dean.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class SQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "com.groundsoft.dean.shoppinglist.db";
    private static final String TABLE_LISTITEMS = "ListItems";
    private static final String TABLE_LISTITEMS_KEY_ID = "id";
    private static final String TABLE_LISTITEMS_KEY_NAME = "name";
    private static final String TABLE_LISTITEMS_KEY_DATE = "date";


    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table " + TABLE_LISTITEMS + " (" +
                TABLE_LISTITEMS_KEY_ID + " integer primary key, " +
                TABLE_LISTITEMS_KEY_NAME + " text, " +
                TABLE_LISTITEMS_KEY_DATE + " integer)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_LISTITEMS);
        onCreate(sqLiteDatabase);
    }

    public void addListItem(String name, Integer date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_LISTITEMS_KEY_NAME, name);
        vals.put(TABLE_LISTITEMS_KEY_DATE, date);

        db.insert(TABLE_LISTITEMS, null, vals);
        db.close();
    }

    public void addListItem(ListItem li) {
        addListItem(li.listname, li.date);
    }

    public ListItem getListItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_LISTITEMS,
                new String[]{TABLE_LISTITEMS_KEY_ID, TABLE_LISTITEMS_KEY_NAME, TABLE_LISTITEMS_KEY_DATE},
                TABLE_LISTITEMS_KEY_ID + "=0",
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ListItem li = new ListItem(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2))
        );

        cursor.close();

        return li;
    }

    public List<ListItem> getAllListItems() {
        List<ListItem> listitems = new ArrayList<ListItem>();

        String query = "select * from " + TABLE_LISTITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            ListItem li = new ListItem(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2))
            );
            listitems.add(li);
        }

        cursor.close();

        return listitems;
    }
}
