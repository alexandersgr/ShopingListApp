package com.groundsoft.dean.shoppinglist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.groundsoft.dean.shoppinglist.Models.Ctgrs;
import com.groundsoft.dean.shoppinglist.Models.Items;
import com.groundsoft.dean.shoppinglist.Models.OneItem;
import com.groundsoft.dean.shoppinglist.R;

import java.util.ArrayList;

public class ItemsListAdapter extends BaseAdapter {

    private ArrayList<OneItem> items;
    private Context context;
    private LayoutInflater lInflater;
    private Items it;
    private Integer currentList;
    private Ctgrs ct;
    private ArrayList<Ctgrs> categories;

    public ItemsListAdapter(Context context, Integer currentList, Ctgrs ct, ArrayList<Ctgrs> categories) {
        this.context = context;
        //this.items = items; ArrayList<OneItem> items,
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        it = new Items(context);
        this.currentList = currentList;
        this.ct = ct;
        this.categories = categories;


        refreshList();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setListChecked(Integer position, boolean checked) {
        items.get(position).MCMchecked = checked;
        notifyDataSetChanged();
    }

    public void clearChecked() {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).MCMchecked = false;
        }
        notifyDataSetChanged();
    }

    public void dropChecked() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).MCMchecked) {
                //lists.get(i).drop();
                it.dropItem(items.get(i).id);
                items.remove(i);
                i -= 1;
            }
        }

        refreshList();
        notifyDataSetChanged();
    }

    public void refreshList() {

        ArrayList<OneItem> items = new ArrayList<OneItem>();
        OneItem newItem;

        ArrayList<OneItem> itemsdb = it.getItems2(currentList);

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
            newItem.checked = currentItem.checked;

            items.add(newItem);


        }
        this.items= items;

    }

    public void updateChecked(Integer itemId, Integer ch) {
        for (int i = 0; i < items.size(); i++) {
            OneItem currentItem = items.get(i);
            if (currentItem.itemType==OneItem.TYPE_ITEM  && currentItem.id.equals(itemId)){
                currentItem.checked = ch;
                break;
            }
        }
        notifyDataSetChanged();
    }

    static class ItemViewHolder {
        TextView itemName;
        CheckBox cb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;


        OneItem currentItem = items.get(position);

        if (currentItem.itemType == OneItem.TYPE_ITEM) {

            if (item == null || (int)item.getTag() != OneItem.TYPE_ITEM) {
                item = lInflater.inflate(R.layout.inflable_items, parent, false);
                item.setTag(OneItem.TYPE_ITEM);
            }

            TextView itemName = (TextView) item.findViewById(R.id.itemName);
            CheckBox cb = (CheckBox) item.findViewById(R.id.checkBox);

            itemName.setText(currentItem.name);
            itemName.setTag(currentItem.id);

            if (currentItem.checked==1){
                itemName.setPaintFlags(itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else {
                itemName.setPaintFlags(itemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }




            //cb.setOnClickListener(checkedClick);

            cb.setFocusable(false);

            cb.setTag(currentItem.id);
            if (currentItem.checked == 1) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }


            if (currentItem.MCMchecked) {
                itemName.setTextColor(Color.parseColor("#ff0000"));
            } else {
                itemName.setTextColor(Color.parseColor("#000000"));
            }


        } else if (currentItem.itemType == OneItem.TYPE_CATEGORY) {
            if (item == null || (int)item.getTag() != OneItem.TYPE_CATEGORY) {
                item = lInflater.inflate(R.layout.inflable_category, parent, false);

                item.setTag(OneItem.TYPE_CATEGORY);
            }

            TextView categoryName = (TextView) item.findViewById(R.id.categoryName);
            categoryName.setText(currentItem.name);

        }

        return item;
    }
}
