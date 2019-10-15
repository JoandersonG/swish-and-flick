package com.example.joanderson.swishflick.models.product;

import android.support.annotation.Nullable;

import com.example.joanderson.swishflick.helpers.ProductValidation;
import com.example.joanderson.swishflick.models.Cash;

import java.io.InvalidClassException;
import java.util.ArrayList;


public class Broomstick extends Product {

    private int maxSpeed;

    public Broomstick(String name, String description, Cash price, int stockAmount, int maxSpeed) throws InvalidClassException{
        super(name, description, price, stockAmount);
        this.maxSpeed = maxSpeed;
        attributesValidation();
        //todo: modificar construtores para um chamar o outro
    }

    public Broomstick(String name, String description, Cash price, int stockAmount, ArrayList<String> magicProperties, int maxSpeed) throws InvalidClassException{
        super(name, description, price, stockAmount, magicProperties);
        this.maxSpeed = maxSpeed;
        attributesValidation();
    }

    public Broomstick(String name, String description, Cash price, int stockAmount, Boolean isMagicProduct, int maxSpeed) throws InvalidClassException{
        super(name, description, price, stockAmount, isMagicProduct);
        this.maxSpeed = maxSpeed;
        attributesValidation();
    }

    private void attributesValidation() throws InvalidClassException{
        int validation = ProductValidation.validateProduct(this);
        if (validation != 0) {
            //error found
            throw new IllegalArgumentException();
        }
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
