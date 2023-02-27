package ru.gb.testing.toyshop.data;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Prize {
    private SimpleIntegerProperty prizeId;
    private SimpleStringProperty prizeName;
    private SimpleBooleanProperty prizeGiven;


    public Prize(String prizeName, boolean prizeGiven) {
        this.prizeName.set(prizeName);
        this.prizeGiven.set(prizeGiven);
    }

    public int getPrizeId() {
        return prizeId.get();
    }

    public SimpleIntegerProperty prizeIdProperty() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId.set(prizeId);
    }

    public String getPrizeName() {
        return prizeName.get();
    }

    public SimpleStringProperty prizeNameProperty() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName.set(prizeName);
    }

    public boolean isPrizeGiven() {
        return prizeGiven.get();
    }

    public SimpleBooleanProperty prizeGivenProperty() {
        return prizeGiven;
    }

    public void setPrizeGiven(boolean prizeGiven) {
        this.prizeGiven.set(prizeGiven);
    }
}
