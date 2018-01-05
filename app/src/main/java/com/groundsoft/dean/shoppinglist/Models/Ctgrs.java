package com.groundsoft.dean.shoppinglist.Models;

import android.content.Context;
import android.content.res.Resources;

import com.groundsoft.dean.shoppinglist.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Ctgrs {
    public Integer categoryId;
    public String categoryName;
    public Integer categoryOrder;
    public int categoryImage;

    @Override
    public String toString() {
        return categoryName;
    }

    public ArrayList<Ctgrs> getAllCategories(Context context) {

        ArrayList<Ctgrs> categories = new ArrayList<Ctgrs>();
        Integer id = 0;

        try {

            InputStream is = context.getResources().openRawResource(R.raw.ctgs);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("ctgs");

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    String name = element2.getAttribute("name");
                    //element2.getTextContent();
                    Integer cid = Integer.valueOf(element2.getAttribute("cid"));
                    String img = element2.getAttribute("image");

                    Resources res = context.getResources();
                    String n = res.getString(res.getIdentifier(name, "string", context.getPackageName()));

                    int image = 0;

                    if (!img.equals("")) {
                        image = res.getIdentifier(img, "mipmap", context.getPackageName());
                    }

                    Ctgrs c = new Ctgrs();

                    c.categoryId = id;
                    c.categoryName = n;
                    c.categoryImage = image;
                    c.categoryOrder = cid;

                    id++;

                    categories.add(c);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    public Integer search(Context context, String s) {
        Integer pos = -1;

        ArrayList<Ctgrs> categories = getAllCategories(context);

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).categoryName.equals(s)) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}