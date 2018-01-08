package com.groundsoft.dean.shoppinglist.Models;


public class OneList {
    public Integer id;
    public String listname;
    public Integer date;
    public boolean checked = false;

    public OneList(Integer id, String listname, Integer date) {
        this.id = id;
        this.listname = listname;
        this.date = date;
    }
}
