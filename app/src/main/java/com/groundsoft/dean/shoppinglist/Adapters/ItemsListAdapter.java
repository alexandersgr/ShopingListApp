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

    static class ItemViewHolder {
        TextView itemName;
        CheckBox cb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemViewHolder vh;

        View item = convertView;
        if (item == null) {
            item = lInflater.inflate(R.layout.inflable_items, parent, false);

            vh = new ItemViewHolder();

            vh.itemName = (TextView) item.findViewById(R.id.itemName);
            vh.cb = (CheckBox) item.findViewById(R.id.checkBox);

            item.setTag(vh);

        } else {
            vh = (ItemViewHolder) convertView.getTag();
        }

        OneItem currentItem = items.get(position);

        vh.itemName.setText(currentItem.name);
        vh.itemName.setTag(currentItem.id);

        vh.cb.setTag(currentItem.id);
        if (currentItem.checked == 1) {
            vh.cb.setChecked(true);
        } else {
            vh.cb.setChecked(false);
        }

        if (currentItem.MCMchecked) {
            vh.itemName.setTextColor(Color.parseColor("#ff0000"));
        } else {
            vh.itemName.setTextColor(Color.parseColor("#000000"));
        }


        return item;
    }
}
