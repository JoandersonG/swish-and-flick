package com.example.joanderson.swishflick.models;

import com.google.firebase.database.Query;

import java.util.ArrayList;

public class HomeFragmentItem {

    private String title;
    private ArrayList <String> productsId;

    public HomeFragmentItem(String title, ArrayList<String> productsId) {
        this.title = title;
        this.productsId = productsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getProductsId() {
        return productsId;
    }

    public void setProductsId(ArrayList<String> productsId) {
        this.productsId = productsId;
    }
}
