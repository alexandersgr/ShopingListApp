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
import com.groundsoft.dean.shoppinglist.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainListAdapter extends BaseAdapter {

    private ArrayList<Lists> lists;
    private Context context;
    private LayoutInflater lInflater;
    View.OnClickListener listOnClick;

    public MainListAdapter(Context context, ArrayList<Lists> lists, View.OnClickListener event) {
        this.lists = lists;
        this.context = context;
        this.listOnClick = event;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int[] colors = new int[2];

        colors[0] = Color.parseColor("#ffffff"); //559966CC
        colors[1] = Color.parseColor("#eeeeee"); //55336699

        Items it = new Items(context);

        View item = convertView;
        if (item == null) {
            item = lInflater.inflate(R.layout.inflable_lists, parent, false);
        }



        TextView listName = (TextView) item.findViewById(R.id.listName);
        listName.setText(lists.get(position).listname);
        listName.setTag(lists.get(position).id);

        if (lists.get(position).checked) {
            listName.setTextColor(Color.parseColor("#ff0000"));
        } else {
            listName.setTextColor(Color.parseColor("#000000"));
        }

        TextView listDate = (TextView) item.findViewById(R.id.listDate);
        long dv = Long.valueOf(lists.get(position).date) * 1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("H:mm d MMM yy").format(df);
        listDate.setText(vv);

        TextView listItems = (TextView) item.findViewById(R.id.listItems);
        listItems.setText(it.getFirstItems(lists.get(position).id, 3));

        //item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
        item.setBackgroundColor(colors[position % 2]);

        //item.setOnClickListener(listOnClick);


        it.close();

        ////////////

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
                lists.get(i).drop();
                lists.remove(i);
                i -= 1;
            }
        }
        notifyDataSetChanged();
    }
}
