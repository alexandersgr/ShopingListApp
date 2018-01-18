package com.groundsoft.dean.shoppinglist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.groundsoft.dean.shoppinglist.Models.Items;
import com.groundsoft.dean.shoppinglist.Models.Lists;
import com.groundsoft.dean.shoppinglist.Models.OneList;
import com.groundsoft.dean.shoppinglist.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainListAdapter extends BaseAdapter {

    private ArrayList<OneList> lists;
    private Context context;
    private LayoutInflater lInflater;
    private Items it;


    public MainListAdapter(Context context, ArrayList<OneList> list) {
        this.lists = list;
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        it = new Items(context);

    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void reloadLists() {

        Lists li = new Lists(context);
        lists = li.getAllLists();

        li.close();
    }

    static class ListItemViewHolder {
        TextView listName;
        TextView listDate;
        TextView listItems;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        int[] colors = new int[2];

        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        ListItemViewHolder vh;

        View item = convertView;

        if (item == null) {
            item = lInflater.inflate(R.layout.inflable_lists, parent, false);

            vh = new ListItemViewHolder();

            vh.listName = (TextView) item.findViewById(R.id.listName);
            vh.listDate = (TextView) item.findViewById(R.id.listDate);
            vh.listItems = (TextView) item.findViewById(R.id.listItems);

            item.setTag(vh);

        } else {
            vh = (ListItemViewHolder) convertView.getTag();
        }

        OneList currentItem = lists.get(position);

        vh.listName.setText(currentItem.listname);
        vh.listName.setTag(currentItem.id);

        if (currentItem.checked) {
            vh.listName.setTextColor(Color.parseColor("#ff0000"));
        } else {
            vh.listName.setTextColor(Color.parseColor("#000000"));
        }

        long dv = Long.valueOf(currentItem.date) * 1000;
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("H:mm d MMM yy").format(df);
        vh.listDate.setText(vv);

        vh.listItems.setText(it.getFirstItems(currentItem.id, 3));

        item.setBackgroundColor(colors[position % 2]);

        return item;
    }

    public void setListChecked(Integer position, boolean checked) {
        lists.get(position).checked = checked;
        notifyDataSetChanged();
    }

    public void clearChecked() {
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).checked = false;
        }
        notifyDataSetChanged();
    }

    public void dropChecked() {
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).checked) {
                //lists.get(i).drop();
                //TODO: удаление листов
                lists.remove(i);
                i -= 1;
            }
        }
        notifyDataSetChanged();
    }
}
