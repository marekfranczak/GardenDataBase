package org.example.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Flower {

    private SimpleIntegerProperty flowerId;
    private SimpleStringProperty namePL;
    private SimpleStringProperty nameLA;

    public Flower(){
        this.flowerId = new SimpleIntegerProperty();
        this.nameLA = new SimpleStringProperty();
        this.namePL = new SimpleStringProperty();
    }

    public int getFlowerId() {
        return flowerId.get();
    }

    public SimpleIntegerProperty flowerIdProperty() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId.set(flowerId);
    }

    public String getNamePL() {
        return namePL.get();
    }

    public SimpleStringProperty namePLProperty() {
        return namePL;
    }

    public void setNamePL(String namePL) {
        this.namePL.set(namePL);
    }

    public String getNameLA() {
        return nameLA.get();
    }

    public SimpleStringProperty nameLAProperty() {
        return nameLA;
    }

    public void setNameLA(String nameLA) {
        this.nameLA.set(nameLA);
    }
}
