package com.example.joanderson.swishflick.models.client;

public class Wand {

    private double size;
    private String wood;
    private String core;

    public Wand(double size, String wood, String core) {
        this.size = size;
        this.wood = wood;
        this.core = core;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getWood() {
        return wood;
    }

    public void setWood(String wood) {
        this.wood = wood;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Wand wand = (Wand) obj;
        return this.core.equals(wand.getCore()) &&
                this.size == wand.getSize() &&
                this.wood.equals(wand.wood);

    }
}
