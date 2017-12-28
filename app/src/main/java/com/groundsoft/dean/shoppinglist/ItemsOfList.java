package com.groundsoft.dean.shoppinglist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ItemsOfList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_of_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int listid = intent.getIntExtra("listid", 0);

        fillList(listid);
    }


    private void categorizedList(Integer listid) {
        int[] colors = new int[2];
        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.itemsListLayout);
        LayoutInflater ltInflater = getLayoutInflater();

        SQLite db = new SQLite(this);

        ArrayList<Items> lists = db.getItems(listid);

        for (int i = 0; i < lists.size(); i++) {

            View category = ltInflater.inflate(R.layout.categories, linLayout, false);

            TextView textCategory = (TextView) category.findViewById(R.id.textCategory);
            textCategory.setText(((Categories) db.getCategory(lists.get(i).categoryid)).categoryName);

            while(true){

                View item = ltInflater.inflate(R.layout.items, linLayout, false);
                TextView itemName = (TextView) item.findViewById(R.id.itemName);
                itemName.setText(lists.get(i).name);
                itemName.setTag(lists.get(i).id);

                CheckBox cb = (CheckBox) item.findViewById(R.id.checkBox);
                cb.setTag(lists.get(i).id);
                if (lists.get(i).checked == 1) {
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }

                item.setBackgroundColor(colors[i % 2]);

                //item.setOnClickListener(listOnClick);

                category.addView(item);
            }
        }


        for (int i = 0; i < lists.size(); i++) {


        }

        db.close();
    }

    private void fillList(Integer listid) {

        int[] colors = new int[2];
        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.itemsListLayout);
        LayoutInflater ltInflater = getLayoutInflater();

        SQLite db = new SQLite(this);

        ArrayList<Items> lists = db.getItems(listid);

        for (int i = 0; i < lists.size(); i++) {

            View item = ltInflater.inflate(R.layout.items, linLayout, false);

            TextView itemName = (TextView) item.findViewById(R.id.itemName);
            itemName.setText(lists.get(i).name);
            itemName.setTag(lists.get(i).id);

            CheckBox cb = (CheckBox) item.findViewById(R.id.checkBox);
            cb.setTag(lists.get(i).id);
            if (lists.get(i).checked == 1) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }

            item.setBackgroundColor(colors[i % 2]);

            //item.setOnClickListener(listOnClick);

            linLayout.addView(item);
        }

        db.close();
    }

    public void checkedClick(View v) {
        SQLite db = new SQLite(this);

        //TODO: update checked state
        Integer itemId = (Integer) ((CheckBox) v).getTag();
        db.updateItemCheckedStatus(itemId, ((CheckBox) v).isChecked() ? 1 : 0);

        db.close();
    }

}
