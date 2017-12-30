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

    private static final String TABLE_LISTS = "lists";
    private static final String TABLE_LISTS_KEY_ID = "id";
    private static final String TABLE_LISTS_KEY_NAME = "name";
    private static final String TABLE_LISTS_KEY_DATE = "date";

    private static final String TABLE_ITEMS = "Items";
    private static final String TABLE_ITEMS_KEY_ID = "id";
    private static final String TABLE_ITEMS_LIST_ID = "listid";
    private static final String TABLE_ITEMS_CATEGORY_ID = "categoryid";
    private static final String TABLE_ITEMS_KEY_NAME = "name";
    private static final String TABLE_ITEMS_KEY_PRICE = "price";
    private static final String TABLE_ITEMS_KEY_QUANTITY = "quantity";
    private static final String TABLE_ITEMS_KEY_CHECKED = "checked";
    private static final String TABLE_ITEMS_KEY_DATE = "date";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_CATEGORIES_KEY_ID = "id";
    private static final String TABLE_CATEGORIES_KEY_NAME = "name";
    private static final String TABLE_CATEGORIES_KEY_ORDER = "catorder";

    private static final String TABLE_DEFITEMS = "defaultitems";
    private static final String TABLE_DEFITEMS_KEY_ID = "id";
    private static final String TABLE_DEFITEMS_KEY_NAME = "name";
    private static final String TABLE_DEFITEMS_KEY_ORDER = "defitorder";

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
                TABLE_ITEMS_LIST_ID + " integer, " +
                TABLE_ITEMS_CATEGORY_ID + " integer, " +
                TABLE_ITEMS_KEY_NAME + " text, " +
                TABLE_ITEMS_KEY_PRICE + " integer, " +
                TABLE_ITEMS_KEY_QUANTITY + " integer, " +
                TABLE_ITEMS_KEY_CHECKED + " integer, " +
                TABLE_ITEMS_KEY_DATE + " integer)";
        sqLiteDatabase.execSQL(query2);

        String query3 = "Create table " + TABLE_CATEGORIES + " (" +
                TABLE_CATEGORIES_KEY_ID + " integer primary key, " +
                TABLE_CATEGORIES_KEY_NAME + " text, " +
                TABLE_CATEGORIES_KEY_ORDER + " integer)";
        sqLiteDatabase.execSQL(query3);

        String query4 = "Create table " + TABLE_DEFITEMS + " (" +
                TABLE_DEFITEMS_KEY_ID + " integer primary key, " +
                TABLE_DEFITEMS_KEY_NAME + " text, " +
                TABLE_DEFITEMS_KEY_ORDER + " integer)";
        sqLiteDatabase.execSQL(query4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_LISTS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_ITEMS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_CATEGORIES);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_DEFITEMS);

        onCreate(sqLiteDatabase);
    }

    public void fill() {
        long x = System.currentTimeMillis() - 60000000;
        for (int i = 1; i <= 21; i++) {
            addList("List " + i, (int) (long) (
                    (x / 1000) + i * 10000)
            );
        }


        for (int n = 0; n <= 5; n++) {
            int b = n + 14;
            for (int i = 0; i <= 10; i++) {
                addItemTest(b, (i % 4)*10 + 10, "Item of list " + b + " #" + i, 0, 1, i % 2,
                        (int) (long) ((x / 1000) + i * 10000));
            }
        }


        addCategory("Аксессуары", 10);
        addCategory("Алкоголь, табак", 20);
        addCategory("Бакалея", 30);
        addCategory("Замороженные продукты", 40);
        addCategory("Косметика, гигиена", 50);
        addCategory("Лекарства", 60);
        addCategory("Молочные продукты", 70);
        addCategory("Мясо, рыба, яйца", 80);
        addCategory("Напитки, соки", 90);
        addCategory("Обувь", 100);
        addCategory("Одежда", 110);
        addCategory("Товары для дома", 120);
        addCategory("Фрукты, овощи, соленья", 130);
        addCategory("Хлеб, выпечка, сладости", 140);
        addCategory("Электроника, бытовая техника", 150);
        addCategory("Другое", 9000);


        addDefItem("Батон", 10);
        addDefItem("Кола", 20);
        addDefItem("Колбаса", 30);
        addDefItem("Хлеб", 40);

        /*

         */
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

    public void addCategory(String name, Integer order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_CATEGORIES_KEY_NAME, name);
        vals.put(TABLE_CATEGORIES_KEY_ORDER, order);

        db.insert(TABLE_CATEGORIES, null, vals);
        db.close();
    }

    public Categories getCategory(Integer categoryOrder) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES,
                new String[]{TABLE_CATEGORIES_KEY_ID, TABLE_CATEGORIES_KEY_NAME, TABLE_CATEGORIES_KEY_ORDER},
                TABLE_CATEGORIES_KEY_ORDER + "=" + categoryOrder,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        Categories categories = new Categories();

        while (cursor.moveToNext()) {

            categories.categoryId = cursor.getInt(0);
            categories.categoryName = cursor.getString(1);
            categories.categoryOrder = cursor.getInt(2);

        }
        cursor.close();

        return categories;
    }

    // ------------------------- Items -----------------------------//

    public void updateItemCheckedStatus(Integer itemId, Integer checked) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update " + TABLE_ITEMS +
                " set " + TABLE_ITEMS_KEY_CHECKED + "=" + checked + " where " + TABLE_ITEMS_KEY_ID + " = " + itemId;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();

        db.close();
    }

    public void addItem(Integer listid, String name, Integer price, Integer quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_ITEMS_LIST_ID, listid);
        vals.put(TABLE_ITEMS_KEY_NAME, name);
        vals.put(TABLE_ITEMS_KEY_PRICE, price);
        vals.put(TABLE_ITEMS_KEY_QUANTITY, quantity);
        vals.put(TABLE_ITEMS_KEY_CHECKED, 0);

        db.insert(TABLE_ITEMS, null, vals);
        db.close();
    }

    public void addItemTest(Integer listid, Integer categoryid, String name, Integer price, Integer quantity, Integer checked, Integer date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_ITEMS_LIST_ID, listid);
        vals.put(TABLE_ITEMS_CATEGORY_ID, categoryid);

        vals.put(TABLE_ITEMS_KEY_NAME, name);
        vals.put(TABLE_ITEMS_KEY_PRICE, price);
        vals.put(TABLE_ITEMS_KEY_QUANTITY, quantity);
        vals.put(TABLE_ITEMS_KEY_CHECKED, checked);
        vals.put(TABLE_ITEMS_KEY_DATE, date);

        db.insert(TABLE_ITEMS, null, vals);
        db.close();
    }

    public String getFirstItems(Integer listid, Integer limit) {
        String res = "";
        ArrayList items = new ArrayList<Items>();
        String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_LIST_ID + " = " + listid + " limit " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Items item = new Items();
            item.name = cursor.getString(3);
            item.quantity = cursor.getInt(5);
            item.checked = cursor.getInt(6);
            items.add(item);
            res += cursor.getString(3) + " ";
        }


        cursor.close();
        return res;
    }

    public ArrayList<Items> getItems(Integer listid) {
        ArrayList items = new ArrayList<Items>();

        //String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_LIST_ID + " = " + listid + " order by " + TABLE_ITEMS_KEY_DATE + " desc";

        String query = "select * from " + TABLE_ITEMS +
                " where " + TABLE_ITEMS_LIST_ID + " = " + listid +
                " order by " + TABLE_ITEMS_CATEGORY_ID + " desc, " + TABLE_ITEMS_KEY_DATE + " desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Items item = new Items();
            item.id = cursor.getInt(0);
            item.listid = cursor.getInt(1);
            item.categoryid = cursor.getInt(2);
            item.name = cursor.getString(3);
            item.price = cursor.getInt(4);
            item.quantity = cursor.getInt(5);
            item.checked = cursor.getInt(6);
            item.date = cursor.getInt(7);


            items.add(item);
        }

        cursor.close();

        return items;
    }


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
