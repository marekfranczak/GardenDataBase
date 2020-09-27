package org.example.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Shop {

    private SimpleIntegerProperty shopId;
    private SimpleStringProperty name;
    private SimpleStringProperty address;

    public Shop(){
        this.shopId = new SimpleIntegerProperty();
        this.address = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}
