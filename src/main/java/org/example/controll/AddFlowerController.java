package org.example.controll;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.data.DataSource;

public class AddFlowerController {

    @FXML
    private TextField flowerNamePL;
    @FXML
    private TextField flowerNameLA;

    public void end(){
        DataSource.getInstance().addFlower(flowerNamePL.getText(), flowerNameLA.getText());
    }
}
