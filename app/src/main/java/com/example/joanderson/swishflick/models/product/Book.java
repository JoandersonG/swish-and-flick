package com.example.joanderson.swishflick.models.product;

import android.support.annotation.Nullable;

import com.example.joanderson.swishflick.models.Cash;


public class Book extends Product {

    private int pagesAmount;
    private String author;
    private  String publisher;

    public Book(String name, String description, Cash price, int stockAmount,
                int pagesAmount, String author, String publisher) {
        super(name, description, price, stockAmount);
        this.pagesAmount = pagesAmount;
        this.author = author;
        this.publisher = publisher;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

    public void setPagesAmount(int pagesAmount) {
        this.pagesAmount = pagesAmount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj) && obj.getClass() == this.getClass();
    }
}
