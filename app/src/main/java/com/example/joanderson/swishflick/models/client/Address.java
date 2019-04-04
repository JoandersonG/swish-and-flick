package com.example.joanderson.swishflick.models.client;

public class Address {

    private String postcode;
    private String street;
    private String locationNumber;
    private String additional;
    private boolean isMuggleLocation;

    public Address(String postcode, String street, String locationNumber, String additional, boolean isMuggleLocation) {
       /* if (!postcode.matches([0-9][0-9][0-9][0-9]-[0-9][0-9][0-9] | [0-9][0-9][0-9][0-9][0-9][0-9][0-9])) {
            throw new IllegalArgumentException();
        }*///TODO: this
        this.postcode = postcode;
        this.street = street;
        this.locationNumber = locationNumber;
        this.additional = additional;
        this.isMuggleLocation = isMuggleLocation;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public boolean isMuggleLocation() {
        return isMuggleLocation;
    }

    public void setMuggleLocation(boolean muggleLocation) {
        isMuggleLocation = muggleLocation;
    }
}
