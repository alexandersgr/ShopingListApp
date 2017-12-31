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

import com.groundsoft.dean.shoppinglist.Models.Categories;
import com.groundsoft.dean.shoppinglist.Models.Items;

import java.util.ArrayList;

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

        //fillList(listid);
        categorizedList(listid);
    }


    private void categorizedList(Integer listid) {
        int[] colors = new int[2];
        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.itemsListLayout);
        LayoutInflater ltInflater = getLayoutInflater();
        LayoutInflater ltInflater2 = getLayoutInflater();

        SQLite db = new SQLite(this);
        Items it = new Items(this);
        Categories cats = new Categories(this);

        ArrayList<Items> lists = it.getItems(listid);

        Integer currentItemCatId = -1;
        View category = null;

        for (int i = 0; i < lists.size(); i++) {


            if (currentItemCatId != lists.get(i).categoryid) {
                category = ltInflater.inflate(R.layout.categories, linLayout, false);

                TextView textCategory = (TextView) category.findViewById(R.id.textCategory);
                Integer cid = lists.get(i).categoryid;
                Categories cat = cats.getCategory(cid);
                String s = cat.categoryName;
                textCategory.setText(s);


                ///////////
                LinearLayout catlin = (LinearLayout) category.findViewById(R.id.itemsContainer);

                while (true) {

                    View item = ltInflater2.inflate(R.layout.items, catlin, false);

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

                    catlin.addView(item);

                    if (i<lists.size()-1){
                        if(lists.get(i).categoryid!=lists.get(i+1).categoryid){
                            break;
                        }
                        else{
                            i+=1;
                        }
                    }else{
                        break;
                    }
                }
                ///////

                linLayout.addView(category);
            }



            currentItemCatId = lists.get(i).categoryid;
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
        Items it = new Items(this);

        ArrayList<Items> lists = it.getItems(listid);

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

        Integer itemId = (Integer) ((CheckBox) v).getTag();
        new Items(this).updateItemCheckedStatus(itemId, ((CheckBox) v).isChecked() ? 1 : 0);

    }

}
