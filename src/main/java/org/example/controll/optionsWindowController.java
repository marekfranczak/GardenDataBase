package org.example.controll;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import org.example.data.DataSource;
import org.example.data.Shop;
import org.w3c.dom.Text;

import javax.xml.crypto.Data;

public class optionsWindowController {

    @FXML
    private ToggleButton editButton;
    @FXML
    private ToggleButton hintsButton;
    @FXML
    private Button guideButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button pathPdfChooserButton;
    @FXML
    private Button pathDBChooserButton;
    @FXML
    private TextField pdfPath;
    @FXML
    private TextField databasePath;
    @FXML
    private TextField gardenNumber;



    @FXML
    private void edit(){

        if(editButton.isSelected()){

            editButton.setText("Zatwierdź");
            guideButton.setDisable(true);
            infoButton.setDisable(true);
            pathPdfChooserButton.setDisable(false);
            pathDBChooserButton.setDisable(false);
            pdfPath.setEditable(true);
            databasePath.setEditable(true);
            gardenNumber.setEditable(true);

        }
        else{
            DataSource.getInstance().setPassportPath(pdfPath.getText());
            DataSource.getInstance().setPathDB(databasePath.getText());
            DataSource.getInstance().setGardenNumber(gardenNumber.getText());
            editButton.setText("Edytuj");
            guideButton.setDisable(false);
            infoButton.setDisable(false);
            pathPdfChooserButton.setDisable(true);
            pathDBChooserButton.setDisable(true);
            pdfPath.setEditable(false);
            databasePath.setEditable(false);
            gardenNumber.setEditable(false);
            refresh();
        }
    }

    @FXML
    private void saveChanges(){

    }

    @FXML
    private void openGuide(){

    }

    @FXML
    private void openInfoWindow(){

    }

    private void refresh(){

    }
}
