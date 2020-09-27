package org.example.data;

import javafx.beans.property.SimpleIntegerProperty;

public class FlowersArrangement {

    private SimpleIntegerProperty passportId;
    private SimpleIntegerProperty flowerId;

    public FlowersArrangement(){
        this.passportId = new SimpleIntegerProperty();
        this.flowerId = new SimpleIntegerProperty();
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

    public int getFlowerId() {
        return flowerId.get();
    }

    public SimpleIntegerProperty flowerIdProperty() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId.set(flowerId);
    }
}
