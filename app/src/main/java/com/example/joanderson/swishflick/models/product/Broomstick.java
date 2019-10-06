package com.example.joanderson.swishflick.models.product;

import android.support.annotation.Nullable;

import com.example.joanderson.swishflick.models.Cash;

import java.util.ArrayList;


public class Broomstick extends Product {

    private int maxSpeed;

    public Broomstick(String name, String description, Cash price, int stockAmount, int maxSpeed) {
        super(name, description, price, stockAmount);
        this.maxSpeed = maxSpeed;
    }

    public Broomstick(String name, String description, Cash price, int stockAmount, ArrayList<String> magicProperties, int maxSpeed) {
        super(name, description, price, stockAmount, magicProperties);
        this.maxSpeed = maxSpeed;
    }

    public Broomstick(String name, String description, Cash price, int stockAmount, Boolean isMagicProduct, int maxSpeed) {
        super(name, description, price, stockAmount, isMagicProduct);
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj) && obj.getClass() == this.getClass();
    }
}
