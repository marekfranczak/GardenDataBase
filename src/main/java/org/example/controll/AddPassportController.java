package org.example.controll;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.data.DataSource;
import org.example.data.Flower;



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

    private List<CheckBox> checkBoxList = new ArrayList<>();
    private List<RadioMenuItem> radioMenuItems = new ArrayList<>();

    List<String> flowerListFromDB;
    List<Flower> flowerList = DataSource.getInstance().flowerList();
    List<String> listOfSelectedFlower = new ArrayList<>();
    List<String> shopNameList = DataSource.getInstance().showShops();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flowerListFromDB = new ArrayList<>(DataSource.getInstance().showFlowers());
        double size = flowerListFromDB.size();
        int newSize;
        if((Math.sqrt(size)%2)!=0){
            newSize = (int) (Math.sqrt(size)+1);
        } else {
            newSize = (int)size;
        }
        int k=0;
        int a=(int)size;
        for(int i=0; i<newSize; i++){
            for(int j=0; j<newSize; j++) {
                if(k<a){
                CheckBox checkBox = new CheckBox(flowerListFromDB.get(k));
                dialogGridPane.add(checkBox, i, j);
                checkBoxList.add(checkBox);
                k++;
                }
            }
        }
        int sizeShop = shopNameList.size();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
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

    public void end(){
        for(CheckBox checkBox : checkBoxList){
            if(checkBox.isSelected()) {
                listOfSelectedFlower.add(checkBox.getText());
            }
        }

        List<Integer> list = new ArrayList<>();
        for(Flower flower : flowerList){
            for(String flowerName : listOfSelectedFlower){
                if(flower.getNamePL().equals(flowerName))
                    list.add(flower.getFlowerId());
            }
        }

        DataSource.getInstance().addFlowersToPassport(shopsMenu.getText(), date.getValue().toString(), list);

    }
}
