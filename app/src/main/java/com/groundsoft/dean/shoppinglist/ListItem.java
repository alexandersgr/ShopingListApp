package com.groundsoft.dean.shoppinglist;

import java.util.Date;

/**
 * Created by Dean on 15.12.2017.
 */

class ListItem {
    Integer id;
    String listname;
    Integer date;
    String items;

    public ListItem() {
    }

    public ListItem(Integer id, String listname, Integer date) {
        this.id = id;
        this.listname = listname;
        this.date = date;
    }
}
