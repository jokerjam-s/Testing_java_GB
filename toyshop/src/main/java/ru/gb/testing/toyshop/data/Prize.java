package ru.gb.testing.toyshop.data;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Prize {
    private final SimpleIntegerProperty prizeId = new SimpleIntegerProperty(0);
    private final SimpleStringProperty prizeName = new SimpleStringProperty("");
    private final SimpleBooleanProperty prizeGiven = new SimpleBooleanProperty(false);


    public Prize(String name, boolean given) {
        this.setPrizeName(name);
        this.setPrizeGiven(given);
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
