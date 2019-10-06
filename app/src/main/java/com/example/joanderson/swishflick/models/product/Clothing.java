package com.example.joanderson.swishflick.models.product;

import android.support.annotation.Nullable;

import com.example.joanderson.swishflick.models.Cash;

import java.util.ArrayList;


public class Clothing extends Product {

    private String size;
    private String color;//**ENUM?

    public Clothing(String name, String description, Cash price, int stockAmount,
                    String size, String color) {
        super(name, description, price, stockAmount);
        this.size = size;
        this.color = color;
    }

    public Clothing(String name, String description, Cash price, int stockAmount, ArrayList<String> magicProperties, String size, String color) {
        super(name, description, price, stockAmount, magicProperties);
        this.size = size;
        this.color = color;
    }

    public Clothing(String name, String description, Cash price, int stockAmount, Boolean isMagicProduct, String size, String color) {
        super(name, description, price, stockAmount, isMagicProduct);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj) && obj.getClass() == this.getClass();
    }
}
