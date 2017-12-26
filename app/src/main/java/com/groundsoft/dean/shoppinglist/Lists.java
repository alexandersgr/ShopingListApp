package com.groundsoft.dean.shoppinglist;

class Lists {
    Integer id;
    String listname;
    Integer date;
    String items;

    public Lists() {
    }

    public Lists(Integer id, String listname, Integer date) {
        this.id = id;
        this.listname = listname;
        this.date = date;
    }
}
