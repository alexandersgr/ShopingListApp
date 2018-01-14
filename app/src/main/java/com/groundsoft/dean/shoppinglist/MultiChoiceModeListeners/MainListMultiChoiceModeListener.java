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

import com.groundsoft.dean.shoppinglist.Adapters.MainListAdapter;
import com.groundsoft.dean.shoppinglist.R;
import com.groundsoft.dean.shoppinglist.ShoppingListMainActivity;


public class MainListMultiChoiceModeListener implements ListView.MultiChoiceModeListener {


    Context context;
    MenuInflater inflater;
    Activity activity;
    private View toolbar;
    private MainListAdapter mla;
    private ListView mlist;

    public MainListMultiChoiceModeListener(Context context, MenuInflater inflater, Activity activity, View toolbar, MainListAdapter mla, ListView mlist) {
        this.context = context;
        this.inflater = inflater;
        this.activity = activity;
        this.toolbar = toolbar;
        this.mla = mla;
        this.mlist = mlist;
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        toolbar.setVisibility(View.GONE);

        inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.list_ms_menu, menu);
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
                //TODO: edit list name dialog
                ((ShoppingListMainActivity)context).listNameEditDialog();
                break;
            case R.id.copy:
                //TODO: copy list dialog
                break;
            case R.id.delete:
                mla.dropChecked();
                //Toast.makeText(ShoppingListMainActivity.this, "del " + "", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(activity, "Clicked " + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        toolbar.setVisibility(View.VISIBLE);
        mla.clearChecked();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        setSubtitle(mode);
        mla.setListChecked(position, checked);
        //(Lists)mlist.getItemAtPosition(position);
        //mlist.findViewById((int) id).
        //mla.notifyDataSetChanged();
        //if (mlist.isItemChecked(3)) {mlist.setItemChecked(3, false);}
    }

    private void setSubtitle(ActionMode mode) {
        final int checkedCount = mlist.getCheckedItemCount();
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
