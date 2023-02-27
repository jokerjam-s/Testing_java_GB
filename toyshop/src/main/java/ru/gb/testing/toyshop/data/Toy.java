package ru.gb.testing.toyshop.data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Toy {
    private SimpleIntegerProperty toyId;
    private SimpleStringProperty toyName;

    private SimpleIntegerProperty toyCount;

    private SimpleDoubleProperty toyRate;


    public Toy(String name){
        toyName.set(name);
    }

    public int getToyId() {
        return toyId.get();
    }

    public SimpleIntegerProperty toyIdProperty() {
        return toyId;
    }

    public void setToyId(int toyId) {
        this.toyId.set(toyId);
    }


    public String getToyName() {
        return toyName.get();
    }

    public SimpleStringProperty toyNameProperty() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName.set(toyName);
    }

    public int getToyCount() {
        return toyCount.get();
    }

    public SimpleIntegerProperty toyCountProperty() {
        return toyCount;
    }

    public void setToyCount(int toyCount) {
        this.toyCount.set(toyCount);
    }

    public double getToyRate() {
        return toyRate.get();
    }

    public SimpleDoubleProperty toyRateProperty() {
        return toyRate;
    }

    public void setToyRate(double toyRate) {
        this.toyRate.set(toyRate);
    }
}
