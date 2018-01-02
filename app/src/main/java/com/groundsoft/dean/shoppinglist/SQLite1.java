package com.groundsoft.dean.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLite1 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "com.groundsoft.dean.shoppinglist.db";


    SQLite1(Context context) {
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

    // ------------------------- Categories -----------------------------//


    // ------------------------- Items -----------------------------//


    // ------------------------- Lists -----------------------------//

}
