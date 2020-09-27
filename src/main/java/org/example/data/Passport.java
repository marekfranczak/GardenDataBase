package org.example.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Passport {

    private SimpleIntegerProperty passportId;
    private SimpleIntegerProperty shopId;
    private SimpleStringProperty date;
    private LocalDate date2;

    public Passport(){
        this.date = new SimpleStringProperty();
        this.passportId = new SimpleIntegerProperty();
        this.shopId = new SimpleIntegerProperty();
    }

    public int getPassportId() {
        return passportId.get();
    }

    public SimpleIntegerProperty passportIdProperty() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId.set(passportId);
    }

    public int getShopId() {
        return shopId.get();
    }

    public SimpleIntegerProperty shopIdProperty() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId.set(shopId);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public LocalDate getDate2() {
        return date2;
    }
}
