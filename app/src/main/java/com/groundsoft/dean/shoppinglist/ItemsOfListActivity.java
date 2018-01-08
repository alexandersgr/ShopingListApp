package com.groundsoft.dean.shoppinglist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;

import com.groundsoft.dean.shoppinglist.Adapters.CategoriesSpinnerAdapter;
import com.groundsoft.dean.shoppinglist.Adapters.ItemNameAdapter;
import com.groundsoft.dean.shoppinglist.Adapters.ItemsListAdapter;
import com.groundsoft.dean.shoppinglist.Models.Ctgrs;
import com.groundsoft.dean.shoppinglist.Models.DfItms;
import com.groundsoft.dean.shoppinglist.Models.Items;
import com.groundsoft.dean.shoppinglist.Models.OneItem;
import com.groundsoft.dean.shoppinglist.MultiChoiceModeListeners.ItemsListMultiChoiceModeListener;

import java.util.ArrayList;

public class ItemsOfListActivity extends AppCompatActivity {

    private Integer currentList;
    private Spinner categorySpinner;
    private Context context;
    ListPopupWindow lpw;
    ArrayList<Ctgrs> categories;
    ArrayList<DfItms.DfItmsRaw> defItemsList;
    EditText userInput = null;
    EditText price = null;
    EditText quantity = null;
    ItemsListAdapter ila;
    ArrayList<OneItem> items;
    ListView itemsList;
    Ctgrs ct;


    private AdapterView.OnItemClickListener actvOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Ctgrs s = (Ctgrs) parent.getItemAtPosition(position);
            categorySpinner.setSelection(s.categoryId);

            //createNewItem("x", s.categoryId);
        }
    };

    private AdapterView.OnItemClickListener lpwOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DfItms.DfItmsFiltered itmFiltered = (DfItms.DfItmsFiltered) parent.getItemAtPosition(position);
            userInput.setText(String.valueOf(itmFiltered.DINameS));
            Integer did = DfItms.getitemIdbyCid(itmFiltered.DICategoryOrder, categories);
            categorySpinner.setSelection(did);
            lpw.dismiss();
        }
    };

    private TextWatcher itemNameOnTextChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //createNewItem(String.valueOf(s), count);

            if (count > 0) {
                ArrayList<DfItms.DfItmsFiltered> c = new ArrayList<DfItms.DfItmsFiltered>();

                DfItms di = new DfItms();

                c = di.filter(String.valueOf(s), defItemsList);

                //ArrayAdapter<DfItms.DfItmsFiltered> adapter3 = new ArrayAdapter<DfItms.DfItmsFiltered>(context, android.R.layout.select_dialog_item, c);

                ItemNameAdapter adapter3 = new ItemNameAdapter(context, c);
                lpw.setAdapter(adapter3);

                if (c.size() == 0) {
                    lpw.dismiss();
                } else {
                    lpw.show();
                }

            } else {
                lpw.dismiss();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private AdapterView.OnItemClickListener listOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //
        }
    };


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
        //categorizedList(currentList);


        ct = new Ctgrs();
        categories = ct.getAllCategories(this);


        items = createItemsList(currentList);
        ila = new ItemsListAdapter(this, items);

        itemsList = findViewById(R.id.itemsList);

        ItemsListMultiChoiceModeListener modeListener =  new ItemsListMultiChoiceModeListener(
                this, getMenuInflater(), this, toolbar, ila, items, itemsList);



        itemsList.setAdapter(ila);
        itemsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        itemsList.setMultiChoiceModeListener(modeListener);
        itemsList.setOnItemClickListener(listOnItemClick);

        DfItms di = new DfItms();
        defItemsList = di.getAllDefItems(this);

    }


    private ArrayList<OneItem> createItemsList(Integer listid) {

        ArrayList<OneItem> items = new ArrayList<OneItem>();
        OneItem newItem;

        Items it = new Items(this);

        ArrayList<OneItem> itemsdb = it.getItems2(listid);

        Integer currentItemCatId = -1;

        for (int i = 0; i < itemsdb.size(); i++) {

            OneItem currentItem = itemsdb.get(i);

            if (i == 0 || !currentItem.categoryid.equals(itemsdb.get(i - 1).categoryid)) {
                newItem = new OneItem();
                newItem.name = ct.getName(categories, itemsdb.get(i).categoryid);
                newItem.itemType = OneItem.TYPE_CATEGORY;

                items.add(newItem);

            }


            newItem = new OneItem();
            newItem.id = currentItem.id;
            newItem.name = currentItem.name;  //categories.getCategoryName(itemsdb.get(i).categoryid);
            newItem.itemType = OneItem.TYPE_ITEM;
            newItem.categoryid = currentItem.categoryid;
            newItem.date = currentItem.date;
            newItem.listid = currentItem.listid;
            newItem.price = currentItem.price;
            newItem.quantity = currentItem.quantity;

            items.add(newItem);


        }
        return items;
    }


    private void categorizedList(Integer listid) {
        /*
        int[] colors = new int[2];
        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.itemsListLayout);
        linLayout.removeAllViews();

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

                    if (i < lists.size() - 1) {
                        if (!lists.get(i).categoryid.equals(lists.get(i + 1).categoryid) ) {
                            break;
                        } else {
                            i += 1;
                        }
                    } else {
                        break;
                    }
                }
                ///////

                linLayout.addView(category);
            }


            currentItemCatId = lists.get(i).categoryid;
        }

*/
    }

    private void fillList(Integer listid) {
/*
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
*/
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


        userInput = (EditText) promptsView.findViewById(R.id.newItemName);
        price = (EditText) promptsView.findViewById(R.id.editPrice);
        quantity = (EditText) promptsView.findViewById(R.id.editQuantity);


        categorySpinner = promptsView.findViewById(R.id.categorySpinner);


        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paths);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        CategoriesSpinnerAdapter adapter = new CategoriesSpinnerAdapter(this, categories);

        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection(categories.size() - 1);
        //categorySpinner.setOnItemSelectedListener(this);


        ArrayAdapter<Ctgrs> adapter2 = new ArrayAdapter<Ctgrs>(this, android.R.layout.select_dialog_item, categories);
        final AutoCompleteTextView actv = (AutoCompleteTextView) promptsView.findViewById(R.id.autoCompleteItemName);
        actv.setThreshold(1);
        actv.setAdapter(adapter2);
        actv.setOnItemClickListener(actvOnClick);


        //Spanned sp = Html.fromHtml("ccc <b>ddd</b> ddd <i>iii</i> sss");
        //userInput.setText(sp);


        lpw = new ListPopupWindow(this);
        //lpw.setAdapter(adapter2);
        lpw.setAnchorView(userInput);
        //lpw.setModal(true);
        lpw.setOnItemClickListener(lpwOnClick);

        userInput.addTextChangedListener(itemNameOnTextChange);


        alertDialogBuilder
                .setTitle(R.string.new_item_dialog_title)
                .setPositiveButton(R.string.dialog_OK_btn,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = String.valueOf(userInput.getText());
                                //String name = String.valueOf(actv.getText());
                                if (!name.equals("")) {
                                    createNewItem();
                                    //categorySpinner.getSelectedItemPosition()
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


    private void createNewItem() {
        String name = String.valueOf(userInput.getText());
        Integer categoryOrder = ((Ctgrs) categorySpinner.getSelectedItem()).categoryOrder;
        Integer itemprice = 0;
        if (!String.valueOf(price.getText()).equals("")) {
            itemprice = Integer.valueOf(String.valueOf(price.getText()));
        }

        Integer itemquontity = 1;
        if (!String.valueOf(quantity.getText()).equals("")) {
            itemquontity = Integer.valueOf(String.valueOf(quantity.getText()));
        }

        long date = System.currentTimeMillis() / 1000;

        //Toast.makeText(this, name + " " + String.valueOf(pos), Toast.LENGTH_LONG).show();
        Items it = new Items(this);
        it.addItemTest(currentList, categoryOrder, name, itemprice, itemquontity, 0, (int) date);
        it.close();

        categorizedList(currentList);

    }

}
