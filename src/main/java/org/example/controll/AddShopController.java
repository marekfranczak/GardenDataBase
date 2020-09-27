package org.example.controll;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.data.DataSource;


public class AddShopController {

    @FXML
    TextField shopNameTextField;
    @FXML
    TextField shopNumberTextField;
    @FXML
    TextArea shopAdressTextField;


    public void end(){
        DataSource.getInstance().addShop(shopNameTextField.getText(), shopAdressTextField.getText());
    }
}
