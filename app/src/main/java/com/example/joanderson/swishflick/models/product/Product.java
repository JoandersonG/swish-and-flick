package com.example.joanderson.swishflick.models.product;

import com.example.joanderson.swishflick.models.Cash;
import com.example.joanderson.swishflick.models.enums.ProductCategory;
import android.support.annotation.Nullable;
import java.util.ArrayList;



public abstract class Product {

    private String name;
    private String description;
    private ArrayList <ProductCategory> categories;
    private Cash price;
    private int stockAmount;

    public Product(String name, String description, Cash price, int stockAmount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockAmount = stockAmount;
        categories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<ProductCategory> categories) {
        this.categories = categories;
    }

    public void addCategory(ProductCategory category) {
        if (!this.categories.contains(category)) {
            this.categories.add(category);
        }
    }

    public void removeCategory(ProductCategory category) {
        this.categories.remove(category);
    }

    public Cash getPrice() {
        return price;
    }

    public void setPrice(Cash price) {
        this.price = price;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        return this.name.equals(((Product)obj).name) &&
                this.description.equals(((Product)obj).description) &&
                this.price.equals(((Product)obj).price);
    }
}
