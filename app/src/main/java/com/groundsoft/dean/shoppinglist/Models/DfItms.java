package com.groundsoft.dean.shoppinglist.Models;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;

import com.groundsoft.dean.shoppinglist.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DfItms {

    public ArrayList<DfItmsRaw> getAllDefItems(Context context) {

        ArrayList<DfItmsRaw> defitems = new ArrayList<DfItmsRaw>();
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

                    Resources res = context.getResources();
                    String n = res.getString(res.getIdentifier(name, "string", context.getPackageName()));

                    DfItmsRaw di = new DfItmsRaw();

                    di.DIId = id;
                    di.DIName = n;
                    di.DICategoryOrder = cid;

                    id++;

                    defitems.add(di);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return defitems;
    }

    public ArrayList<DfItmsFiltered> filter(String s, ArrayList<DfItmsRaw> list) {

        ArrayList<DfItmsFiltered> filteredList = new ArrayList<DfItmsFiltered>();

        for (int i = 0; i < list.size(); i++) {
            int start = list.get(i).DIName.toLowerCase().indexOf(s);
            if (start >= 0) {
                DfItmsFiltered c = new DfItmsFiltered();
                c.DINameS = Html.fromHtml(String.valueOf(start) + " <b>d</b> " + list.get(i).DIName);
                filteredList.add(c);
            }
        }

        return filteredList;
    }

    public class DfItmsRaw {
        public Integer DIId;
        public String DIName;
        public Integer DICategoryOrder;
        public Spanned DINameS;
    }

    public class DfItmsFiltered {
        public Integer DIId;
        public String DIName;
        public Integer DICategoryOrder;
        public Spanned DINameS;

        @Override
        public String toString() {
            return String.valueOf(DINameS);
        }
    }
}
