package org.example.controll;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.example.Main;

import java.net.URL;

public class PageLoader {

    private Pane view;

    public Pane getPage(String fileName){
        try {
            URL fileUrl = Main.class.getResource(fileName + ".fxml");
            if(fileName == null){
                throw new java.io.FileNotFoundException("File");
            }
            view = new FXMLLoader().load(fileUrl);
        } catch (Exception e){
            System.out.println("No page "+fileName);
        }
        return view;
    }
}
