package com.example.joanderson.swishflick.models;

public class Cash {

    private int galleon;
    private int sickle;
    private int knut;

    public Cash(int galleon, int sickle, int knut) {

        this.galleon = galleon;
        this.sickle = sickle;
        this.knut = knut;
        updateValues();
    }

    public int getGalleon() {
        return galleon;
    }

    public void setGalleon(int galleon) {
        this.galleon = galleon;
    }

    public int getSickle() {
        return sickle;
    }

    public void setSickle(int sickle) {
        this.sickle = sickle;
        updateValues();
    }

    public int getKnut() {
        return knut;
    }

    public void setKnut(int knut) {
        this.knut = knut;
        updateValues();
    }

    private void updateValues() {
        while (knut >= 29) {
            sickle++;
            knut -= 29;
        }
        while (sickle >= 17) {
            galleon++;
            sickle -= 17;
        }
    }

    public void multiply(int value) {
        galleon *= value;
        sickle *= value;
        knut *= value;
        updateValues();
    }

    public void add(Cash value) {
        galleon += value.galleon;
        sickle += value.sickle;
        knut += value.knut;
        updateValues();
    }

    public void subtract(Cash value) {
        galleon -= value.galleon;
        sickle -= value.sickle;
        knut -= value.knut;
        updateValues();
    }




}
