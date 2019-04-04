package com.example.joanderson.swishflick.models.product;

import android.support.annotation.Nullable;

import com.example.joanderson.swishflick.models.Cash;

import java.util.ArrayList;



public class Potion extends Product {

    private int mlQuantity;
    private ArrayList <String> effects;

    public Potion(String name, String description, Cash price, int stockAmount,
                  int mlQuantity) {
        super( name, description, price, stockAmount);
        this.mlQuantity = mlQuantity;
        this.effects = new ArrayList<>();
    }

    public int getMlQuantity() {
        return mlQuantity;
    }

    public void setMlQuantity(int mlQuantity) {
        this.mlQuantity = mlQuantity;
    }

    public ArrayList<String> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<String> effects) {
        this.effects = effects;
    }

    public void addEfect(String effect) {
        if(!this.effects.contains(effect)) {
            this.effects.add(effect);
        }
    }

    public void removeEffect(String effect) {
        this.effects.remove(effect);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj) && obj.getClass() == this.getClass();
    }
}
