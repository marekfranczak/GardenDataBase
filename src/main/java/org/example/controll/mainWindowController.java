package org.example.controll;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class mainWindowController {


    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize(){ }

    @FXML
    private void flowerButton(){

        PageLoader object = new PageLoader();
        Pane view = object.getPage("flowerswindow");
        borderPane.setCenter(view);
        System.out.println("Wyświetlono kwiaty!");
    }

    @FXML
    private void shopButton(){

        PageLoader object = new PageLoader();
        Pane view = object.getPage("shopswindow");
        borderPane.setCenter(view);
        System.out.println("Wyświetlono sklepy!");
    }

    @FXML
    private void passportButton(){

        PageLoader object = new PageLoader();
        Pane view = object.getPage("passportswindow");
        borderPane.setCenter(view);
        System.out.println("Wyświetlono paszporty!");
    }

    @FXML
    private void optionButton(){

        PageLoader object = new PageLoader();
        Pane view = object.getPage("optionswindow");
        borderPane.setCenter(view);
        System.out.println("Wyświetlono opcje!");
    }

}
