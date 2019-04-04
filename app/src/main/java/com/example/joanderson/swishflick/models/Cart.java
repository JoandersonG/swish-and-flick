package com.example.joanderson.swishflick.models;

import java.util.ArrayList;

public class Cart {

    private ArrayList <Item> itens;
    private Cash totalPrice;

    public Cart() {
        this.itens = new ArrayList<>();
        totalPrice = new Cash(0,0,0);
    }

    public ArrayList<Item> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Item> itens) {
        this.itens = itens;
        //recalcular              tod o            o                preço
        this.totalPrice = new Cash(0,0,0);
        for (Item item : itens) {
            this.totalPrice.add(item.getTotalPrice());
        }
    }

    public void addItem(Item item) {
        if (this.itens.contains(item)) {
            Item item1 = this.itens.get(this.itens.indexOf(item));
            item1.setAmount(item1.getAmount()+item.getAmount());
        }
        else {
            this.itens.add(item);
        }
        totalPrice.add(item.getTotalPrice());
    }

    public void removeItem(Item item) {
        this.itens.remove(item);
        this.totalPrice.subtract(item.getTotalPrice());
    }

    public Cash getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Cash totalPrice) {
        this.totalPrice = totalPrice;
    }

}
