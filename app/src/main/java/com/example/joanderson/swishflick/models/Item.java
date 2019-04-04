package com.example.joanderson.swishflick.models;

import com.example.joanderson.swishflick.models.product.Product;

import android.support.annotation.Nullable;


public class Item {

    private Product product;
    private int amount;
    private Cash totalPrice;

    public Item(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.totalPrice = product.getPrice();
        this.totalPrice.multiply(amount);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.totalPrice = product.getPrice();
        this.totalPrice.multiply(amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.totalPrice = product.getPrice();
        this.totalPrice.multiply(amount);
    }

    public Cash getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Cash totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null && this.getClass() != obj.getClass()) {
            return false;
        }
        return product == ((Item) obj).product;
    }
}
