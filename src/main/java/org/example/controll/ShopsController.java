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
import org.example.data.Shop;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShopsController implements Initializable {

    @FXML
    private TextField shopName;
    @FXML
    private TextField shopNumber;
    @FXML
    private TextArea shopAddress;
    @FXML
    private ListView<String> listView;
    @FXML
    private AnchorPane shopsWindow;
    @FXML
    private ToggleButton editButton;

    private List<Shop> shopList;

    @FXML
    public void add(){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(shopsWindow.getScene().getWindow());
        dialog.setTitle("Dodawanie sklepu");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addshop.fxml"));
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
            AddShopController controler =  fxmlLoader.getController();
            controler.end();
        }
        refresh();
    }

    @FXML
    private void delete(){
        String shopChoosed = listView.getSelectionModel().getSelectedItem();
        List<String> shopData = new ArrayList<>();

        if(shopChoosed==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nie wybrano sklepu");
            alert.setHeaderText(null);
            alert.setContentText("Proszę wybrać sklep do usunięcia");
            alert.showAndWait();
            return;
        }
        else{
            for(Shop shop : shopList){
                if(shopChoosed.equals(shop.getName())){
                    shopData.add(String.valueOf(shop.getShopId()));
                    shopData.add(shop.getName());
                    shopData.add(shop.getAddress());
                }
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(shopsWindow.getScene().getWindow());
            dialog.setTitle("Usuń sklep");
            dialog.setContentText("Na pewno chcesz usunąć sklep?");

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()== ButtonType.OK){
                if(DataSource.getInstance().deleteShop(shopData)) {
                    shopName.setText("");
                    shopNumber.setText("");
                    shopAddress.setText("");
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

            String shopChoosed = listView.getSelectionModel().getSelectedItem();

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
            shopName.setEditable(true);
            shopAddress.setEditable(true);


        }
        else{
            String x = shopName.getText();
            String a = shopNumber.getText();
            String z = shopAddress.getText();
            int y = Integer.parseInt(a);
            DataSource.getInstance().updateShop(x, z, y);
            editButton.setText("Edytuj");
            listView.setEditable(true);
            shopName.setEditable(false);
            shopAddress.setEditable(false);
            refresh();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    private void refresh(){
        ObservableList<String> shopsList = DataSource.getInstance().showShops();
        shopList = DataSource.getInstance().shopList();
        listView.setItems(shopsList);
        shopName.setEditable(false);
        shopAddress.setEditable(false);
        shopNumber.setEditable(false);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue <? extends String> ov, String o, String t1) {
                if (!editButton.isSelected()) {
                    if (t1 != null) {
                        String nazwa = listView.getSelectionModel().getSelectedItem();
                        for (Shop shop : shopList) {
                            if (nazwa.equals(shop.getName())) {
                                shopName.setText(shop.getName());
                                shopAddress.setText(shop.getAddress());
                                shopNumber.setText(String.valueOf(shop.getShopId()));
                            }
                        }
                    }
                }
            }
        });
    }


}
