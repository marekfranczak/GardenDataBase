package org.example.controll;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import org.example.data.DataSource;
import org.example.data.Flower;
import org.example.data.FlowersArrangement;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFlowersController implements Initializable {

    @FXML
    private GridPane gridPane;

    private List<String> flowerListFromDB;
    private List<CheckBox> checkBoxList = new ArrayList<>();
    private List<Integer> flowerListFromPassport = new ArrayList<>();
    private int passportId;
    private List<FlowersArrangement> flowersArrangementList;
    private List<String> listOfSelectedFlower = new ArrayList<>();
    private List<Flower> flowerList = DataSource.getInstance().flowerList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flowerListFromDB = new ArrayList<>(DataSource.getInstance().showFlowers());

        flowersArrangementList = DataSource.getInstance().flowersArrangementList();

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
                    gridPane.add(checkBox, i, j);
                    checkBoxList.add(checkBox);
                    k++;
                }
            }
        }
    }

    public void add(){

        for(FlowersArrangement flowersArrangement : flowersArrangementList){
            if(flowersArrangement.getPassportId() == passportId){
                flowerListFromPassport.add(flowersArrangement.getFlowerId());
            }
        }

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
        list.removeAll(flowerListFromPassport);
        DataSource.getInstance().updateFlowerInPassport(passportId, list);

    }

    public void delete(){

        for(FlowersArrangement flowersArrangement : flowersArrangementList){
            if(flowersArrangement.getPassportId() == passportId){
                flowerListFromPassport.add(flowersArrangement.getFlowerId());
            }
        }

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

        list.retainAll(flowerListFromPassport);
        DataSource.getInstance().deleteFlowerInPassport(passportId, list);

    }

    public void setData(int data){
        this.passportId = data;
    }

    public int getPassportId(){
        return passportId;
    }
}
