package com.example.joanderson.swishflick.models.product;

import com.example.joanderson.swishflick.models.Cash;
import com.example.joanderson.swishflick.models.enums.ProductCategory;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;



public abstract class Product implements Serializable {
    /*
    var magicProperties: ArrayList<String>//todo: isso d'aproblema?
    val isMagicProduct : Boolean

    fun addMagicProperty (property : String) : Boolean{
        if (!isMagicProduct) return false
        magicProperties.add(property)
        return true
    }

    fun removeMagicProperty (property: String) : Boolean{
        if (!isMagicProduct) return false
        return magicProperties.remove(property)
    }
    fun totalMagicProperties() : Int{
        return magicProperties.size
    }
    */
    private String name;
    private String description;
    private ArrayList <ProductCategory> categories;
    private Boolean isMagicProduct;
    private ArrayList <String> magicProperties;
    private Cash price;
    private int stockAmount;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product() {

    }

    public Product(String name, String description, Cash price, int stockAmount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockAmount = stockAmount;
        categories = new ArrayList<>();
        isMagicProduct = false;
    }
    public Product(String name, String description, Cash price, int stockAmount, ArrayList<String> magicProperties) {
        this(name,description,price,stockAmount);
        this.isMagicProduct = true;
        this.magicProperties = magicProperties;
    }
    public Product(String name, String description, Cash price, int stockAmount, Boolean isMagicProduct) {
        this(name,description,price,stockAmount);
        this.isMagicProduct = isMagicProduct;
        if (isMagicProduct) {
            magicProperties = new ArrayList<>();
        }
    }

    public Boolean getMagicProduct() {
        return isMagicProduct;
    }

    public void setMagicProduct(Boolean magicProduct) {
        isMagicProduct = magicProduct;
    }

    public void addMagicProperty(String property) {
        magicProperties.add(property);
    }
    public Boolean removeMagicProperty(String property) {
        return magicProperties.remove(property);
    }
    public ArrayList<String> getMagicProperties() {
        return magicProperties;
    }

    public void setMagicProperties(ArrayList<String> magicProperties) {
        this.magicProperties = magicProperties;
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
