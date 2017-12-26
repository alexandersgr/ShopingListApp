package com.groundsoft.dean.shoppinglist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShoppingListMainActivity extends AppCompatActivity {

    int[] colors = new int[2];
    private View.OnClickListener listOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listOnClick(v);
        }
    };;

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

        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.mainlist);

        //ListView lv = (ListView) findViewById(R.id.mainlist);

        LayoutInflater ltInflater = getLayoutInflater();

        SQLite db = new SQLite(this);

        ArrayList<Lists> lists = db.getAllLists();

        for (int i = 0; i < lists.size(); i++) {
            //Log.d("myLogs", "i = " + i);

            View item = ltInflater.inflate(R.layout.lists, linLayout, false);

            TextView listName = (TextView) item.findViewById(R.id.listName);
            listName.setText(lists.get(i).listname);
            listName.setTag(lists.get(i).id);

            TextView listDate = (TextView) item.findViewById(R.id.listDate);
            long dv = Long.valueOf(lists.get(i).date)*1000;// its need to be in milisecond
            Date df = new java.util.Date(dv);
            String vv = new SimpleDateFormat("H:m dd MMM yy").format(df);
            listDate.setText(vv);

            TextView listItems = (TextView) item.findViewById(R.id.listItems);
            listItems.setText(db.getFirstItems(lists.get(i).id,3));

            //item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);

            item.setOnClickListener(listOnClick);

            linLayout.addView(item);
        }

        db.close();

    }

    public void listOnClick (View view)  {
        TextView listName = (TextView) view.findViewById(R.id.listName);

        TextView tv = (TextView)findViewById(R.id.textView4);
        tv.setText(listName.getTag().toString());


        Intent myIntent = new Intent(ShoppingListMainActivity.this, ItemsOfList.class);
        myIntent.putExtra("listid", (Integer)listName.getTag()); //Optional parameters
        ShoppingListMainActivity.this.startActivity(myIntent);

    }

    public void addList (View v){
        //Toast.makeText(this,"text", Toast.LENGTH_LONG).show();

        SQLite db = new SQLite(this);

        //db.fill();

        TextView tv = (TextView)findViewById(R.id.textView4);

        Lists li = db.getList(2);

           tv.setText(li.id + " " +li.listname + " " + li.date);
        //li.listname + " " + li.date
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
