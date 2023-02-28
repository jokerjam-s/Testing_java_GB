package ru.gb.testing.toyshop.data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Toy implements Comparable<Toy> {
    private final SimpleIntegerProperty toyId = new SimpleIntegerProperty(0);
    private final SimpleStringProperty toyName = new SimpleStringProperty("");
    private final SimpleIntegerProperty toyCount = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty toyRate = new SimpleIntegerProperty(0);

    public Toy(){
        this.setToyName("");
        this.setToyCount(0);
        this.setToyRate(1);
        this.setToyId(0);
    }

    public Toy(String name, int count, int rate){
        this.setToyName(name);
        this.setToyCount(count);
        this.setToyRate(rate);
        this.setToyId(0);
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

    public int getToyRate() {
        return toyRate.get();
    }

    public SimpleIntegerProperty toyRateProperty() {
        return toyRate;
    }

    public void setToyRate(int toyRate) {
        this.toyRate.set(toyRate);
    }

    @Override
    public int compareTo(Toy o) {
        if(o == null){
            return -1;
        }

        double delta = this.toyRate.get() - o.toyRate.get();
        if(delta < 0){
            return 1;
        } else if (delta > 0) {
            return -1;
        }

        return 0;
    }
}
