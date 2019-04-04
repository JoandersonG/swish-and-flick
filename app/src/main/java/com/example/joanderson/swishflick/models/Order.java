package com.example.joanderson.swishflick.models;

import com.example.joanderson.swishflick.models.client.Client;
import com.example.joanderson.swishflick.models.enums.PaymentStatus;
import com.example.joanderson.swishflick.models.enums.ShippingStatus;

public class Order {

    private ShippingStatus shippingStatus;
    private PaymentStatus paymentStatus;
    private Cart cart;
    private Client client;

    public Order(ShippingStatus shippingStatus, PaymentStatus paymentStatus, Cart cart, Client client) {
        this.shippingStatus = shippingStatus;
        this.paymentStatus = paymentStatus;
        this.cart = cart;
        this.client = client;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
