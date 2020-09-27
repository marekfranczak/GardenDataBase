package org.example.controll;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.data.DataSource;
import org.example.data.Flower;
import org.example.data.Shop;


import java.net.URL;
import java.util.*;

public class AddPassportController implements Initializable {

    @FXML
    private GridPane dialogGridPane;
    @FXML
    private DatePicker date;
    @FXML
    private MenuButton shopsMenu;
    @FXML
    private ToggleGroup togglegroup1;
    @FXML
    private MenuButton MenuButtonTest;
    @FXML
    private SplitMenuButton SplitMenuButtonTest;

    private List<CheckBox> checkBoxList = new ArrayList<>();
    private List<RadioMenuItem> radioMenuItems = new ArrayList<>();

    List<String> flowerListFromDB;
    List<Flower> flowerList = DataSource.getInstance().flowerList();
    List<String> listOfSelectedFlower = new ArrayList<>();
    List<Shop> shopList = DataSource.getInstance().shopList();
    List<String> shopNameList = DataSource.getInstance().showShops();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flowerListFromDB = new ArrayList<>(DataSource.getInstance().showFlowers());
        double size = flowerListFromDB.size();
        System.out.println("Size: "+size);
        int newSize;
        if((Math.sqrt(size)%2)!=0){
            newSize = (int) (Math.sqrt(size)+1);
            System.out.println("rozmiar Nie Parzysty = "+newSize);
        } else {
            newSize = (int)size;
            System.out.println("rozmiar Parzysty = "+newSize);
        }
        int k=0;
        int a=(int)size;
        System.out.println(flowerListFromDB.toString());
        for(int i=0; i<newSize; i++){
            for(int j=0; j<newSize; j++) {
                if(k<a){
                CheckBox checkBox = new CheckBox(flowerListFromDB.get(k));
                dialogGridPane.add(checkBox, i, j);
                checkBoxList.add(checkBox);
                System.out.println(flowerListFromDB.get(i+j)+" || "+i+"X"+j);
                System.out.println("K:"+k);
                k++;

                }
            }
        }
        int sizeShop = shopNameList.size();
        System.out.println("sizeShop: "+sizeShop);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println(((RadioMenuItem)e.getSource()).getText() + " selected");
                shopsMenu.setText(((RadioMenuItem) e.getSource()).getText());
            }
        };

        for(int l=0;l<sizeShop;l++){
            RadioMenuItem radioMenuItem = new RadioMenuItem(shopNameList.get(l));
            radioMenuItem.setSelected(false);
            radioMenuItem.setToggleGroup(togglegroup1);
            radioMenuItem.setOnAction(event);
            radioMenuItems.add(radioMenuItem);
        }
        shopsMenu.getItems().addAll(radioMenuItems);
        shopsMenu.show();
    }

    public String end(){
        int i=0;
        String name=" ";
        for(CheckBox checkBox : checkBoxList){
            if(checkBox.isSelected()) {
                name = name + " || " + checkBox.getText();
                listOfSelectedFlower.add(checkBox.getText());
            }

            System.out.println(flowerListFromDB.get(i));
            i++;
        }

        List<Integer> list = new ArrayList<>();
        for(Flower flower : flowerList){
            for(String flowerName : listOfSelectedFlower){
                if(flower.getNamePL().equals(flowerName))
                    list.add(flower.getFlowerId());
            }
        }
        for(RadioMenuItem radioMenuItem : radioMenuItems){
            if(radioMenuItem.isSelected()) {
                System.out.println(radioMenuItem.getText());
                System.out.println(radioMenuItem.getId());
            }
        }
        DataSource.getInstance().addFlowersToPassport(shopsMenu.getText(), date.getValue().toString(), list);
        System.out.println(date.getValue());
        System.out.println(shopsMenu.getText());
        date.getValue();
        System.out.println(list.toString());


        return name;
    }
}
