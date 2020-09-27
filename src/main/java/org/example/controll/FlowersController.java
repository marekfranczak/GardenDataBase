package org.example.controll;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.Main;
import org.example.data.DataSource;
import org.example.data.Flower;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FlowersController implements Initializable {


    @FXML
    private TextField flowerNameLA;
    @FXML
    private TextField flowerNamePL;
    @FXML
    private TextField flowerNumber;
    @FXML
    private ListView<String> listView;
    @FXML
    private AnchorPane flowersWindow;
    @FXML
    private ToggleButton editButton;

    private List<Flower> flowerList;


    @FXML
    public void add(){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(flowersWindow.getScene().getWindow());
        dialog.setTitle("Dodawanie kwiatu");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addflower.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Nie można otworzyć okna.." + e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK){
            AddFlowerController controler =  fxmlLoader.getController();
            controler.end();
        }
        refresh();
    }

    @FXML
    private void delete(){
        String flowerChooser = listView.getSelectionModel().getSelectedItem();
        List<String> flowerData = new ArrayList<>();

        if(flowerChooser==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nie wybrano rośliny");
            alert.setHeaderText(null);
            alert.setContentText("Proszę wybrać roślinę do usunięcia");
            alert.showAndWait();
            return;
        }
        else{
            for(Flower flower : flowerList){
                if(flowerChooser.equals(flower.getNamePL())){
                    flowerData.add(String.valueOf(flower.getFlowerId()));
                    flowerData.add(flower.getNamePL());
                    flowerData.add(flower.getNameLA());
                }
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(flowersWindow.getScene().getWindow());
            dialog.setTitle("Usuń roślinę");
            dialog.setContentText("Na pewno chcesz usunąć roślinę?");

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()== ButtonType.OK){
                if(DataSource.getInstance().deleteFlower(flowerData)) {
                    flowerNamePL.setText("");
                    flowerNameLA.setText("");
                    flowerNumber.setText("");
                    System.out.println("Udało się usunąć");
                }
                else
                    System.out.println("Nie udało się usunąć");
            }
        }

        refresh();
    }

    @FXML
    private void edit(){
        if(editButton.isSelected()){

            String shopChoosed = (String) listView.getSelectionModel().getSelectedItem();

            if(shopChoosed==null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nie wybrano sklepu");
                alert.setHeaderText(null);
                alert.setContentText("Proszę wybrać sklep do edycji");
                alert.showAndWait();
                return;
            }

            editButton.setText("Zatwierdź");
            listView.setEditable(false);
            flowerNameLA.setEditable(true);
            flowerNamePL.setEditable(true);

        }
        else{
            String x = flowerNamePL.getText();
            String a = flowerNumber.getText();
            String z = flowerNameLA.getText();
            int y = Integer.parseInt(a);
            DataSource.getInstance().updateFlower(x, z, y);
            editButton.setText("Edytuj");
            listView.setEditable(true);
            flowerNameLA.setEditable(false);
            flowerNamePL.setEditable(false);
            refresh();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    private void refresh(){
        ObservableList<String> flowersList = DataSource.getInstance().showFlowers();
        flowerList = DataSource.getInstance().flowerList();
        listView.setItems(flowersList);
        flowerNamePL.setEditable(false);
        flowerNameLA.setEditable(false);
        flowerNumber.setEditable(false);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String o, String t1) {
                if(t1 != null){
                    String nazwa = listView.getSelectionModel().getSelectedItem();
                    for(Flower flower : flowerList){
                        if(nazwa.equals(flower.getNamePL())){
                            flowerNamePL.setText(flower.getNamePL());
                            flowerNameLA.setText(flower.getNameLA());
                            flowerNumber.setText(String.valueOf(flower.getFlowerId()));
                        }
                    }
                }
            }
        });
        listView.getSelectionModel().selectFirst();
    }
}



























