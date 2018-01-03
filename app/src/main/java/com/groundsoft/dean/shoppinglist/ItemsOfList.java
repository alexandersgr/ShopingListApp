package com.groundsoft.dean.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.groundsoft.dean.shoppinglist.Models.Categories;
import com.groundsoft.dean.shoppinglist.Models.Items;

import java.util.ArrayList;

public class ItemsOfList extends AppCompatActivity {

    private Integer currentList;
    public Spinner categorySpinner;
    private static final String[]paths = {"item 1", "item 2", "item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_of_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        currentList = intent.getIntExtra("listid", 0);

        //fillList(currentList);
        categorizedList(currentList);
    }

    private void categorizedList(Integer listid) {
        int[] colors = new int[2];
        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.itemsListLayout);
        LayoutInflater ltInflater = getLayoutInflater();
        LayoutInflater ltInflater2 = getLayoutInflater();

        Items it = new Items(this);
        Categories cats = new Categories(this);

        ArrayList<Items> lists = it.getItems(listid);

        Integer currentItemCatId = -1;
        View category = null;

        for (int i = 0; i < lists.size(); i++) {


            if (currentItemCatId != lists.get(i).categoryid) {
                category = ltInflater.inflate(R.layout.inflable_categories, linLayout, false);

                TextView textCategory = (TextView) category.findViewById(R.id.textCategory);
                Integer cid = lists.get(i).categoryid;
                Categories cat = cats.getCategory(cid);
                String s = cat.categoryName;
                textCategory.setText(s);


                ///////////
                LinearLayout catlin = (LinearLayout) category.findViewById(R.id.itemsContainer);

                while (true) {

                    View item = ltInflater2.inflate(R.layout.inflable_items, catlin, false);

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


    }

    private void fillList(Integer listid) {

        int[] colors = new int[2];
        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.itemsListLayout);
        LayoutInflater ltInflater = getLayoutInflater();

        Items it = new Items(this);

        ArrayList<Items> lists = it.getItems(listid);

        for (int i = 0; i < lists.size(); i++) {

            View item = ltInflater.inflate(R.layout.inflable_items, linLayout, false);

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

    }

    public void checkedClick(View v) {

        Integer itemId = (Integer) ((CheckBox) v).getTag();
        new Items(this).updateItemCheckedStatus(itemId, ((CheckBox) v).isChecked() ? 1 : 0);

    }

    public void addItem(View view) {


        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_add_item, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.newItemName);

        categorySpinner = promptsView.findViewById(R.id.categorySpinner);



        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);
        //categorySpinner.setOnItemSelectedListener(this);

        //


        alertDialogBuilder
                .setPositiveButton(R.string.dialog_OK_btn,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = String.valueOf(userInput.getText());
                                if (!name.equals("")) {
                                    createNewItem(name, categorySpinner.getSelectedItemPosition());
                                }
                            }
                        })
                .setNegativeButton(R.string.dialog_Cancel_btn,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }


    private void createNewItem(String name, Integer pos) {
        Toast.makeText(this,name + " " + String.valueOf(pos), Toast.LENGTH_LONG).show();
    }


}
