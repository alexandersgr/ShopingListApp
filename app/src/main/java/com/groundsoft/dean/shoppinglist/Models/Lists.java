package com.groundsoft.dean.shoppinglist.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Lists extends SQLiteOpenHelper {
    public Integer id;
    public String listname;
    public Integer date;
    public String items;
    public boolean checked = false;

    private Context context;

    static final String TABLE_LISTS = "lists";
    static final String TABLE_LISTS_KEY_ID = "id";
    static final String TABLE_LISTS_KEY_NAME = "name";
    static final String TABLE_LISTS_KEY_DATE = "date";

    SQLiteDatabase dba;

    public Lists(Context context) {
        super(context, DbConsts.DATABASE_NAME, null, DbConsts.DATABASE_VERSION);
        this.context = context;

        if(context!=null) {
            dba = getWritableDatabase();
        }
    }

    public SQLiteDatabase getDb(){
        return dba;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbConsts.createAll(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbConsts.upgradeAll(db, oldVersion, newVersion);
    }

    public Lists(Context context, Integer id, String listname, Integer date) {
        super(context, DbConsts.DATABASE_NAME, null, DbConsts.DATABASE_VERSION);
        this.context=context;
        this.id = id;
        this.listname = listname;
        this.date = date;
    }


    public long addList(String name, Integer date) {
        //SQLiteDatabase db = this.getWritableDatabase();

        String safeName = name.replace("'","''");

        ContentValues vals = new ContentValues();
        vals.put(TABLE_LISTS_KEY_NAME, safeName);
        vals.put(TABLE_LISTS_KEY_DATE, date);

        long res = dba.insert(TABLE_LISTS, null, vals);
        //db.close();
        return res;
    }

    public void addList(Lists li) {
        addList(li.listname, li.date);
    }

    public Lists getList(Integer id) {
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = dba.query(TABLE_LISTS,
                new String[]{TABLE_LISTS_KEY_ID, TABLE_LISTS_KEY_NAME, TABLE_LISTS_KEY_DATE},
                TABLE_LISTS_KEY_ID + "=" + id,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        Lists li = new Lists(null);

        while (cursor.moveToNext()) {

            li.id = cursor.getInt(0);
            li.listname = cursor.getString(1);
            li.date = cursor.getInt(2);

        }
        cursor.close();

        return li;
    }

    public ArrayList<OneList> getAllLists() {
        ArrayList listitems = new ArrayList<OneList>();

        String query = "select * from " + TABLE_LISTS + " order by date desc";  //id desc date desc
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = dba.rawQuery(query, null);

        while (cursor.moveToNext()) {
            OneList li = new OneList(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            );
            listitems.add(li);
        }

        cursor.close();

        return listitems;
    }

    public void drop() {
        String query = "delete from " + TABLE_LISTS + " where " + TABLE_LISTS_KEY_ID + "=" + this.id;
        //SQLiteDatabase db = getWritableDatabase();
        dba.execSQL(query);
    }
}
