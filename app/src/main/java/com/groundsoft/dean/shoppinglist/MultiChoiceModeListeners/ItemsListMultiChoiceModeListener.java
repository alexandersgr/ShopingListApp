package com.groundsoft.dean.shoppinglist.MultiChoiceModeListeners;

import android.app.Activity;
import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.groundsoft.dean.shoppinglist.Adapters.ItemsListAdapter;
import com.groundsoft.dean.shoppinglist.Models.OneItem;
import com.groundsoft.dean.shoppinglist.R;

import java.util.ArrayList;

public class ItemsListMultiChoiceModeListener implements ListView.MultiChoiceModeListener {


    Context context;
    MenuInflater inflater;
    Activity activity;
    private View toolbar;
    private ItemsListAdapter ila;
    private ArrayList<OneItem> items;
    private ListView ilist;

    public ItemsListMultiChoiceModeListener(Context context, MenuInflater inflater, Activity activity, View toolbar, ItemsListAdapter ila, ArrayList<OneItem> items, ListView ilist) {
        this.context = context;
        this.inflater = inflater;
        this.activity = activity;
        this.toolbar = toolbar;
        this.ila = ila;
        this.items = items;
        this.ilist = ilist;
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        toolbar.setVisibility(View.GONE);

        inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.items_ms_menu, menu);
        mode.setTitle(R.string.main_list_ms_menu_title);
        setSubtitle(mode);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                //TODO: edit list name (2nd)
                break;
            case R.id.delete:
                ila.dropChecked();
                mode.finish();
                break;
            default:
                //Toast.makeText(activity, "Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        toolbar.setVisibility(View.VISIBLE);
        ila.clearChecked();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        if (((OneItem) ilist.getItemAtPosition(position)).itemType == OneItem.TYPE_CATEGORY) {
            if (ilist.isItemChecked(position)) {
                ilist.setItemChecked(position, false);
            }

        } else {
            ila.setListChecked(position, checked);
        }

        setSubtitle(mode);
    }

    private void setSubtitle(ActionMode mode) {
        final int checkedCount = ilist.getCheckedItemCount();
        switch (checkedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            case 1:
                mode.setSubtitle(R.string.main_list_ms_menu_subtitle1);
                break;
            default:
                mode.setSubtitle("" + checkedCount + " " + activity.getString(R.string.main_list_ms_menu_subtitle2));
                break;
        }
    }
}
