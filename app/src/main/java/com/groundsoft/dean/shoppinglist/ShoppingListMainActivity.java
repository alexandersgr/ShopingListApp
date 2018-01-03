package com.groundsoft.dean.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.groundsoft.dean.shoppinglist.Models.Categories;
import com.groundsoft.dean.shoppinglist.Models.DefItems;
import com.groundsoft.dean.shoppinglist.Models.Items;
import com.groundsoft.dean.shoppinglist.Models.Lists;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ShoppingListMainActivity extends AppCompatActivity {

    int[] colors = new int[2];
    private View.OnClickListener listOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listOnClick(v);
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

    }

    protected void onResume() {
        super.onResume();

        fillMainList();
    }

    public void fillMainList() {

        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.mainlist);
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
        TextView listName = (TextView) view.findViewById(R.id.listName);

        TextView tv = (TextView) findViewById(R.id.textView4);
        tv.setText(listName.getTag().toString());

        openListActivity((Integer) listName.getTag());
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

        //TextView tv = (TextView) findViewById(R.id.textView4);
        //tv.setText(String.valueOf(res));

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



    void xmltest(){
        TextView tv = (TextView) findViewById(R.id.textView4);


        try {
            InputStream is = getResources().openRawResource(R.raw.ctgs);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("ctgs");

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    String name = element2.getAttribute("name");
                    Integer cid = Integer.valueOf(element2.getAttribute("cid"));

                    Resources res = this.getResources();
                    String n =  res.getString(res.getIdentifier(name, "string", this.getPackageName()));

                    tv.setText(n + " " + cid);
                    //tv1.setText(tv1.getText()+"Surname : " + getValue("surname", element2)+"\n");
                    //tv1.setText(tv1.getText()+"-----------------------");
                }
            }

        } catch (Exception e) {e.printStackTrace();}

    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
