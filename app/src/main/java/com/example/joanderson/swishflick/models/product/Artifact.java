package com.example.joanderson.swishflick.models.product;

import com.example.joanderson.swishflick.models.Cash;

import java.util.ArrayList;

public class Artifact extends Product {
    public Artifact(String name, String description, Cash price, int stockAmount) {
        super(name, description, price, stockAmount);
    }

    public Artifact(String name, String description, Cash price, int stockAmount, ArrayList<String> magicProperties) {
        super(name, description, price, stockAmount, magicProperties);
    }

    public Artifact(String name, String description, Cash price, int stockAmount, Boolean isMagicProduct) {
        super(name, description, price, stockAmount, isMagicProduct);
    }
}
