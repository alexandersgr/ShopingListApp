package com.groundsoft.dean.shoppinglist.Models;

public class Lists {
    public Integer id;
    public String listname;
    public Integer date;
    public String items;

    public Lists() {
    }

    public Lists(Integer id, String listname, Integer date) {
        this.id = id;
        this.listname = listname;
        this.date = date;
    }
}
