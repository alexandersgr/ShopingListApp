package com.groundsoft.dean.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.groundsoft.dean.shoppinglist.Adapters.MainListAdapter;
import com.groundsoft.dean.shoppinglist.Models.Categories;
import com.groundsoft.dean.shoppinglist.Models.DefItems;
import com.groundsoft.dean.shoppinglist.Models.Items;
import com.groundsoft.dean.shoppinglist.Models.Lists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ShoppingListMainActivity extends AppCompatActivity {

    private int[] colors = new int[2];
    private ListView mlist;
    private MainListAdapter mla;
    private boolean needRefresh = false;


    public View.OnClickListener listOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listOnClick(v);
        }
    };
    private AdapterView.OnItemClickListener listOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CheckedTextView v = (CheckedTextView) view;

            TextView tv = (TextView) findViewById(R.id.textView4);

            //listName.getTag().toString()
            tv.setText(v.getText());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        //fillMainList();

        Lists li = new Lists(this);
        ArrayList<Lists> lists = li.getAllLists();


        mla = new MainListAdapter(this, lists, listOnClick);

        mlist = (ListView) findViewById(R.id.list);




        mlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mlist.setMultiChoiceModeListener(new ModeCallback());
        mlist.setOnItemClickListener(listOnItemClick);
        //mlist.setAdapter(mla);



        String[] mStrings = new String[]{"one","two","three"};
        mlist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, mStrings));


    }



    protected void onResume() {
        super.onResume();

        if (needRefresh) {
            mla.notifyDataSetChanged();
            needRefresh = false;
        }
        //fillMainList();
    }

    public void fillMainList() {

        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = null; // (LinearLayout) findViewById(R.id.mainlist);
        linLayout.removeAllViews();

        //ListView lv = (ListView) findViewById(R.id.mainlist);

        LayoutInflater ltInflater = getLayoutInflater();

        Lists li = new Lists(this);
        Items it = new Items(this);

        ArrayList<Lists> lists = li.getAllLists();

        for (int i = 0; i < lists.size(); i++) {
            //Log.d("myLogs", "i = " + i);

            View item = ltInflater.inflate(R.layout.inflable_lists, linLayout, false);

            TextView listName = (TextView) item.findViewById(R.id.listName);
            listName.setText(lists.get(i).listname);
            listName.setTag(lists.get(i).id);

            TextView listDate = (TextView) item.findViewById(R.id.listDate);
            long dv = Long.valueOf(lists.get(i).date) * 1000;// its need to be in milisecond
            Date df = new java.util.Date(dv);
            String vv = new SimpleDateFormat("H:mm d MMM yy").format(df);
            listDate.setText(vv);

            TextView listItems = (TextView) item.findViewById(R.id.listItems);
            listItems.setText(it.getFirstItems(lists.get(i).id, 3));

            //item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);

            item.setOnClickListener(listOnClick);

            linLayout.addView(item);
        }

        li.close();
        it.close();
    }

    public void listOnClick(View view) {
        //TextView listName = (TextView) view.findViewById(R.id.listName);

        CheckedTextView v = (CheckedTextView) view;

        TextView tv = (TextView) findViewById(R.id.textView4);

        //listName.getTag().toString()
        tv.setText(v.getText());

        //openListActivity((Integer) listName.getTag());
    }

    public void openListActivity(Integer listId) {
        Intent myIntent = new Intent(ShoppingListMainActivity.this, ItemsOfList.class);
        myIntent.putExtra("listid", listId); //Optional parameters
        ShoppingListMainActivity.this.startActivity(myIntent);

    }

    public void addList(View v) {
        //Toast.makeText(this,"text", Toast.LENGTH_LONG).show();

        /*
        SQLite db = new SQLite(this);

        TextView tv = (TextView) findViewById(R.id.textView4);
        DefItems di = db.getDefItem(3);
        tv.setText(di.defItemId + " " + di.defItemName + " " + di.defItemOrder);

        db.close();
         */


        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_add_list, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.newListName);

        alertDialogBuilder
                //.setCancelable(false)
                .setPositiveButton(R.string.dialog_OK_btn,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = String.valueOf(userInput.getText());
                                if (!name.equals("")) {
                                    createAndShowNewList(name);
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


    public void createAndShowNewList(String name) {
        Lists li = new Lists(this);

        long res = li.addList(name, (int) (long) (System.currentTimeMillis() / 1000));

        li.close();

        needRefresh = true;

        openListActivity((int) res);

    }

    public void fillDb(View v) {
        Lists li = new Lists(this);
        Items it = new Items(this);
        Categories cat = new Categories(this);
        DefItems di = new DefItems(this);

        long x = System.currentTimeMillis() - 60000000;
        for (int i = 1; i <= 21; i++) {
            li.addList("List " + i, (int) (long) (
                    (x / 1000) + i * 10000)
            );
        }


        for (int n = 0; n <= 5; n++) {
            int b = n + 14;
            for (int i = 0; i <= 10; i++) {
                it.addItemTest(b, (i % 4) * 10 + 10, "Item of list " + b + " #" + i, 0, 1, i % 2,
                        (int) (long) ((x / 1000) + i * 10000));
            }
        }

/*
        cat.addCategory("Аксессуары", 10);
        cat.addCategory("Алкоголь, табак", 20);
        cat.addCategory("Бакалея", 30);
        cat.addCategory("Замороженные продукты", 40);
        cat.addCategory("Косметика, гигиена", 50);
        cat.addCategory("Лекарства", 60);
        cat.addCategory("Молочные продукты", 70);
        cat.addCategory("Мясо, рыба, яйца", 80);
        cat.addCategory("Напитки, соки", 90);
        cat.addCategory("Обувь", 100);
        cat.addCategory("Одежда", 110);
        cat.addCategory("Товары для дома", 120);
        cat.addCategory("Фрукты, овощи, соленья", 130);
        cat.addCategory("Хлеб, выпечка, сладости", 140);
        cat.addCategory("Электроника, бытовая техника", 150);
        cat.addCategory("Другое", 9000);
*/


        di.addDefItem("Батон", 10);
        di.addDefItem("Кола", 20);
        di.addDefItem("Колбаса", 30);
        di.addDefItem("Хлеб", 40);

        /*

         */

        li.close();
        it.close();
        di.close();
        cat.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds inflable_items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            TextView tv = (TextView) findViewById(R.id.textView4);
            tv.setText("options");
            return true;
        }

        if (id == R.id.action_test1) {

            TextView tv = (TextView) findViewById(R.id.textView4);
            tv.setText("test1");

            xmltest();

            //R.xml.ctgs
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    void xmltest() {
        TextView tv = (TextView) findViewById(R.id.textView4);
        Snackbar.make(tv, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    private class ModeCallback implements ListView.MultiChoiceModeListener {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_shopping_list_main, menu);
            mode.setTitle("Select Items");
            setSubtitle(mode);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                default:
                    Toast.makeText(ShoppingListMainActivity.this, "Clicked " + item.getTitle(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {

        }

        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            setSubtitle(mode);
        }

        private void setSubtitle(ActionMode mode) {
            final int checkedCount = mlist.getCheckedItemCount();
            switch (checkedCount) {
                case 0:
                    mode.setSubtitle(null);
                    break;
                case 1:
                    mode.setSubtitle("One item selected");
                    break;
                default:
                    mode.setSubtitle("" + checkedCount + " items selected");
                    break;
            }
        }
    }

}

