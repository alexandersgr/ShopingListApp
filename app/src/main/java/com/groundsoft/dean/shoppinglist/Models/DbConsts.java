package com.groundsoft.dean.shoppinglist.Models;

import android.database.sqlite.SQLiteDatabase;
import com.groundsoft.dean.shoppinglist.SQLite;

class DbConsts {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "com.groundsoft.dean.shoppinglist.db";

    static void createAll (SQLiteDatabase db){

        //CREATE TABLE IF NOT EXISTS

        String query = "Create table " + SQLite.TABLE_LISTS + " (" +
                SQLite.TABLE_LISTS_KEY_ID + " integer primary key, " +
                SQLite.TABLE_LISTS_KEY_NAME + " text, " +
                SQLite.TABLE_LISTS_KEY_DATE + " integer)";
        db.execSQL(query);

        String query2 = "Create table " + Items.TABLE_ITEMS + " (" +
                Items.TABLE_ITEMS_KEY_ID + " integer primary key, " +
                Items.TABLE_ITEMS_LIST_ID + " integer, " +
                Items.TABLE_ITEMS_CATEGORY_ID + " integer, " +
                Items.TABLE_ITEMS_KEY_NAME + " text, " +
                Items.TABLE_ITEMS_KEY_PRICE + " integer, " +
                Items.TABLE_ITEMS_KEY_QUANTITY + " integer, " +
                Items.TABLE_ITEMS_KEY_CHECKED + " integer, " +
                Items.TABLE_ITEMS_KEY_DATE + " integer)";
        db.execSQL(query2);

        String query3 = "Create table " + Categories.TABLE_CATEGORIES + " (" +
                Categories.TABLE_CATEGORIES_KEY_ID + " integer primary key, " +
                Categories.TABLE_CATEGORIES_KEY_NAME + " text, " +
                Categories.TABLE_CATEGORIES_KEY_ORDER + " integer)";
        db.execSQL(query3);

        String query4 = "Create table " + SQLite.TABLE_DEFITEMS + " (" +
                SQLite.TABLE_DEFITEMS_KEY_ID + " integer primary key, " +
                SQLite.TABLE_DEFITEMS_KEY_NAME + " text, " +
                SQLite.TABLE_DEFITEMS_KEY_ORDER + " integer)";
        db.execSQL(query4);

    }

    static void upgradeAll(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Items.TABLE_ITEMS);
        db.execSQL("drop table if exists " + Categories.TABLE_CATEGORIES);
        db.execSQL("drop table if exists " + SQLite.TABLE_LISTS);
        db.execSQL("drop table if exists " + SQLite.TABLE_DEFITEMS);
        createAll(db);
    }
}
