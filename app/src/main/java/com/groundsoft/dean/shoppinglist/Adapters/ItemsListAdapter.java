package com.groundsoft.dean.shoppinglist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.groundsoft.dean.shoppinglist.Models.OneItem;
import com.groundsoft.dean.shoppinglist.R;

import java.util.ArrayList;

public class ItemsListAdapter extends BaseAdapter {

    private ArrayList<OneItem> items;
    private Context context;
    private LayoutInflater lInflater;
    private Integer listid;

    public ItemsListAdapter(Context context, ArrayList<OneItem> items) {
        this.context = context;
        this.items = items;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

    public void reloadLists(ArrayList<OneItem> items) {
        this.items = items;
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
                items.remove(i);
                i -= 1;
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

            //cb.setOnClickListener(checkedClick);

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
