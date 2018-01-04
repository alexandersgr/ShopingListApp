package com.groundsoft.dean.shoppinglist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.groundsoft.dean.shoppinglist.Models.Ctgrs;
import com.groundsoft.dean.shoppinglist.R;

import java.util.ArrayList;

public class CategoriesSpinnerAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Ctgrs> objects;

    public CategoriesSpinnerAdapter(Context context, ArrayList<Ctgrs> categories) {
        ctx = context;
        objects = categories;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.inflable_category_spinner_item, parent, false);
        }

        Ctgrs p = (Ctgrs) getItem(position);

        ((TextView) view.findViewById(R.id.categoryName)).setText(p.categoryName);
        ((ImageView) view.findViewById(R.id.categoryImage)).setImageResource(p.categoryImage);

        return view;
    }

}
