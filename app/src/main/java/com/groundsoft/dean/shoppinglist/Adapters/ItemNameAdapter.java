package com.groundsoft.dean.shoppinglist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.groundsoft.dean.shoppinglist.Models.Ctgrs;
import com.groundsoft.dean.shoppinglist.Models.DfItms;
import com.groundsoft.dean.shoppinglist.R;

import java.util.ArrayList;

public class ItemNameAdapter extends BaseAdapter {

    ArrayList<DfItms.DfItmsFiltered> filteredList = new ArrayList<DfItms.DfItmsFiltered>();
    Context context;
    private LayoutInflater lInflater;

    public ItemNameAdapter(Context context, ArrayList<DfItms.DfItmsFiltered> list) {
        this.context = context;
        filteredList = list;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.inflable_item_name_popup_window_item, parent, false);
        }

        DfItms.DfItmsFiltered p = (DfItms.DfItmsFiltered) getItem(position);

        ((TextView) view.findViewById(R.id.itemName)).setText(p.DINameS);

        return view;

    }
}
