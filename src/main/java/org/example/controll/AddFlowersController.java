package org.example.controll;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.GridPane;
import org.example.data.DataSource;
import org.example.data.Flower;
import org.example.data.FlowersArrangement;

import javax.xml.crypto.Data;
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
                    gridPane.add(checkBox, i, j);
                    checkBoxList.add(checkBox);
                    System.out.println(flowerListFromDB.get(i+j)+" || "+i+"X"+j);
                    System.out.println("K:"+k);
                    k++;

                }
            }
        }
    }

    public String add(){
        String name = "";
        int i =0;

        for(FlowersArrangement flowersArrangement : flowersArrangementList){
            if(flowersArrangement.getPassportId() == passportId){
                flowerListFromPassport.add(flowersArrangement.getFlowerId());
            }
        }

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
        System.out.println("Lista wybranych kwiatów: "+list);
        list.removeAll(flowerListFromPassport);
        System.out.println("Lista wybranych kwiatów pomniejszona o już istniejące"+list);
        //DataSource.getInstance().addFlowersToPassport(shopsMenu.getText(), date.getValue().toString(), list);
        DataSource.getInstance().updateFlowerInPassport(passportId, list);

        System.out.println(list.toString());

        return name;
    }

    public String delete(){
        String name = "";
        int i =0;

        for(FlowersArrangement flowersArrangement : flowersArrangementList){
            if(flowersArrangement.getPassportId() == passportId){
                flowerListFromPassport.add(flowersArrangement.getFlowerId());
            }
        }

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

        System.out.println("Lista wybranych kwiatów: "+list);
        list.retainAll(flowerListFromPassport);
        System.out.println("Lista wybranych kwiatów pomniejszona o już istniejące"+list);
        //DataSource.getInstance().addFlowersToPassport(shopsMenu.getText(), date.getValue().toString(), list);
        DataSource.getInstance().deleteFlowerInPassport(passportId, list);

        System.out.println(list.toString());

        return name;
    }

    public void setData(int data){

        System.out.println("Data: "+data);
        this.passportId = data;
    }

    public int getPassportId(){
        return passportId;
    }
}
