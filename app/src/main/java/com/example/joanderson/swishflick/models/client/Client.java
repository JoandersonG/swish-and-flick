package com.example.joanderson.swishflick.models.client;

import com.example.joanderson.swishflick.models.product.Product;

import java.util.ArrayList;

public class Client {

    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private ArrayList <String> favoriteProducts;//id of them
    private ArrayList <String> orders;//id of order
    private Wand wand;

    public Client(String firstName, String lastName, String email, Address address, Wand wand) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.wand = wand;
        this.favoriteProducts = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<String> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(ArrayList<String> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public void addfavoriteProduct(String productId) {
        if(!this.favoriteProducts.contains(productId)) {
            this.favoriteProducts.add(productId);
        }
    }

    public void removeFavoriteProduct(String productId) {
        this.favoriteProducts.remove(productId);
    }

    public ArrayList<String> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<String> orders) {
        this.orders = orders;
    }

    public void addOrder(String orderId) {
        if(!this.orders.contains(orderId)) {
            this.orders.add(orderId);
        }
    }

    public void removeOrder(String orderId){
        this.orders.remove(orderId);
    }

    public Wand getWand() {
        return wand;
    }

    public void setWand(Wand wand) {
        this.wand = wand;
    }
}
