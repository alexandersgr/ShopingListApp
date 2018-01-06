package com.groundsoft.dean.shoppinglist;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class MainListMultiChoiceModeListener implements ListView.MultiChoiceModeListener{

    Context context;

    public MainListMultiChoiceModeListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        //MenuInflater inflater = context.getActivity().getMenuInflater();
        //inflater.inflate(R.menu.menu_shopping_list_main, menu);
        mode.setTitle("Select Items");
        //setSubtitle(mode);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
