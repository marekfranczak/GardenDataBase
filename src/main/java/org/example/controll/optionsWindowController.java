package org.example.controll;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.example.data.DataSource;
import org.example.data.Shop;
import org.w3c.dom.Text;

import javax.xml.crypto.Data;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class optionsWindowController implements Initializable {

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
    private TextField pdfPath;
    @FXML
    private TextField gardenNumber;
    @FXML
    private AnchorPane optionsWindow;
    @FXML
    private Label tipsAndTricks;


    @FXML
    private void edit(){

        if(editButton.isSelected()){
            editButton.setText("Zatwierdź");
            guideButton.setDisable(true);
            infoButton.setDisable(true);
            pathPdfChooserButton.setDisable(false);
            pdfPath.setEditable(true);
            gardenNumber.setEditable(true);
            hintsButton.setDisable(false);
        }
        else{
            DataSource.getInstance().setPassportPath(pdfPath.getText());
            DataSource.getInstance().setGardenNumber(gardenNumber.getText());
            int ax = DataSource.getInstance().getTipsAndTricks();
            DataSource.getInstance().updateProgramData(pdfPath.getText(), gardenNumber.getText(), ax);
            editButton.setText("Edytuj");
            guideButton.setDisable(false);
            infoButton.setDisable(false);
            pathPdfChooserButton.setDisable(true);
            pdfPath.setEditable(false);
            gardenNumber.setEditable(false);
            hintsButton.setDisable(true);
            refresh();
        }
    }

    @FXML
    private void openGuide(){

    }

    @FXML
    private void openInfoWindow(){

    }

    @FXML
    private void choosePathDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz miejsce do zapisu paszportów");
        fileChooser.setInitialFileName("fileName.pdf");
        File selectedFile = fileChooser.showSaveDialog(optionsWindow.getScene().getWindow());
        if(selectedFile != null) {
            String[] namePath;
            namePath = selectedFile.getAbsolutePath().split("\\\\");
            int z = namePath.length;
            String asd = "";
            for(int i =0;i<z-1;i++){
                if(i==0) {
                    asd=asd+namePath[i];
                }else {
                    asd = asd + "\\" + namePath[i];
                }
            }
            asd=asd+"\\";
            pdfPath.setText(asd);
            DataSource.getInstance().setPassportPath(asd);
        } else {
            System.out.println("Nie wybrano miejsca zapisu");
        }
        pdfPath.setText(DataSource.getInstance().getPassportPath());
        //refresh();
    }

    private void refresh(){
        pdfPath.setText(DataSource.getInstance().getPassportPath());
        gardenNumber.setText(DataSource.getInstance().getGardenNumber());
        //tipsAndTricksNumber = DataSource.getInstance().getTipsAndTricks();
        if(DataSource.getInstance().getTipsAndTricks() > 0){
            hintsButton.setSelected(true);
            tipsAndTricks.setText("Wyłącz podpowiedzi");
        } else {
            hintsButton.setSelected(false);
            tipsAndTricks.setText("Włącz podpowiedzi");
        }
        editButton.setText("Edytuj");
        guideButton.setDisable(false);
        infoButton.setDisable(false);
        pathPdfChooserButton.setDisable(true);
        pdfPath.setEditable(false);
        gardenNumber.setEditable(false);
        hintsButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }
}
