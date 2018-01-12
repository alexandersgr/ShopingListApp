package com.groundsoft.dean.shoppinglist.Models;


public class OneItem {
    public Integer id;
    public Integer listid;
    public Integer categoryid;
    public Float quantity;
    public Float price;
    public String name;
    public Integer checked = 0;
    public Integer date;

    public boolean MCMchecked = false;
    public int itemType;

    public static final int TYPE_ITEM = 1;
    public static final int TYPE_CATEGORY = 2;

}
