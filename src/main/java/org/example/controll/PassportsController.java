package org.example.controll;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.Main;
import org.example.data.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PassportsController implements Initializable {

    @FXML
    private ListView<String> passportsListView;
    @FXML
    private ListView<String> flowersListView;
    @FXML
    private TextField passportNumber;
    @FXML
    private TextField shopName;
    @FXML
    private DatePicker passportDate;
    @FXML
    private AnchorPane passportsWindow;
    @FXML
    private ToggleButton editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addFlowersButton;
    @FXML
    private Button deleteFlowersButton;
    @FXML
    private Button previewButton;
    @FXML
    private Button generateButton;

    private List<Passport> passportList;
    private List<FlowersArrangement> flowersArrangementList;
    private List<Flower> flowersList;
    private List<Shop> shopList;

    @FXML
    private void addFlowers(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(passportsWindow.getScene().getWindow());
        dialog.setTitle("Dodawanie kwiatów");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addflowers.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Nie można otwrzyć okna.. "+e.getMessage());
            e.printStackTrace();
            return;
        }
        AddFlowersController controller = fxmlLoader.getController();
        controller.setData(Integer.parseInt(passportNumber.getText()));

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            System.out.println(controller.add());
            System.out.println("Wciśnięto OK");
        }

        refresh();
    }

    @FXML
    private void addPassport(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(passportsWindow.getScene().getWindow());
        dialog.setTitle("Tworzenie paszportu");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("createpassport.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Nie można otworzyć okna.. " + e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK){
            AddPassportController controller =  fxmlLoader.getController();
            System.out.println(controller.end());
            System.out.println("Wciśnięto OK");
        }

        refresh();
    }

    @FXML
    private void deletePassport(){
        String content = "'"+passportNumber.getText()+"'";
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(passportsWindow.getScene().getWindow());
        dialog.setTitle("Usuwanie paszportu");
        dialog.setHeaderText("Dane zostaną usunięte bezpowrotnie");
        dialog.setContentText("Jeśli jesteś pewien wciśnij OK");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK) {
            if (DataSource.getInstance().deletePassport(content)) {
                System.out.println("Udało się usunąc paszport");
                refresh();
            }
        }
    }

    @FXML
    private void edit(){
        if(editButton.isSelected()){

            String shopChoosed = (String) passportsListView.getSelectionModel().getSelectedItem();

            if(shopChoosed==null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nie wybrano sklepu");
                alert.setHeaderText(null);
                alert.setContentText("Proszę wybrać sklep do edycji");
                alert.showAndWait();
                editButton.setSelected(false);
                return;
            }

            editButton.setText("Zatwierdź");
            passportsListView.setEditable(false);
            addButton.setDisable(true);
            deleteButton.setDisable(true);
            addFlowersButton.setDisable(true);
            deleteFlowersButton.setDisable(true);
            previewButton.setDisable(true);
            generateButton.setDisable(true);
            shopName.setEditable(true);
            passportDate.setEditable(true);


        }
        else{
            String x = shopName.getText();
            String a = passportDate.getValue().toString();
            int shopId = -1;
            for(Shop shop : shopList){
                if (x.equals(shop.getName()))
                    shopId = shop.getShopId();
            }
            String z = passportNumber.getText();
            int k = Integer.parseInt(z);
            DataSource.getInstance().updatePassport(shopId, a, k);
            editButton.setText("Edytuj");
            passportsListView.setEditable(true);
            shopName.setEditable(false);
            passportDate.setEditable(false);
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            addFlowersButton.setDisable(false);
            deleteFlowersButton.setDisable(false);
            previewButton.setDisable(false);
            generateButton.setDisable(false);
            refresh();
        }
    }

    @FXML
    private void deleteFlowers(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(passportsWindow.getScene().getWindow());
        dialog.setTitle("Usuwanie kwiatów");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addflowers.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Nie można otwrzyć okna.. "+e.getMessage());
            e.printStackTrace();
            return;
        }
        AddFlowersController controller = fxmlLoader.getController();
        controller.setData(Integer.parseInt(passportNumber.getText()));

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            System.out.println(controller.delete());
            System.out.println("Wciśnięto OK");
        }

        refresh();
    }

    @FXML
    private void previewPassport(){

    }

    @FXML
    private void generatePassport(){
        try {
            Document document = new Document();
            Integer passportID = Integer.parseInt(passportNumber.getText());
            List<Integer> flowerIdList = new ArrayList<>();
            flowersList = DataSource.getInstance().flowerList();
            List<Flower> newFlower = new ArrayList<>();
            if(passportDate.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setContentText("Proszę wprowadzić datę paszportu");
                alert.showAndWait();
                return;
            }
            String fileName = shopName.getText()+"_"+passportDate.getValue().toString();
            String path = DataSource.getInstance().getPassportPath();
            flowersArrangementList = DataSource.getInstance().flowersArrangementList();
            for(FlowersArrangement flowersArrangement : flowersArrangementList){
                if(flowersArrangement.getPassportId() == passportID){
                    flowerIdList.add(flowersArrangement.getFlowerId());
                }
            }

            PdfWriter.getInstance(document, new FileOutputStream(path+"\\"+fileName+".pdf"));

            document.open();
            PdfPTable pdfPTable = new PdfPTable(3);

            Font f1 = FontFactory.getFont(String.valueOf(Font.FontFamily.TIMES_ROMAN), BaseFont.IDENTITY_H, true);
            Font font1 = f1;
            try {
                BaseFont bf = BaseFont.createFont("C:\\Users\\FM\\IdeaProjects\\GOTOWE_PROJEKTY\\GardenDataBase\\arial.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
                font1 = new Font(bf, 8);
            }catch(IOException e){
                System.out.println("Błąd wczytywania czcionki. "+e.getMessage());
            }



            for(Flower flower : flowersList){
                if(flowerIdList.contains(flower.getFlowerId())){
                    newFlower.add(flower);
                    System.out.println("Dodaje kwiatek: "+flower.getNamePL());
                    PdfPTable upTable = new PdfPTable(2);

                    PdfPCell cell1 = new PdfPCell(new Paragraph());
                    try {
                        Image img = Image.getInstance("C:\\Users\\FM\\IdeaProjects\\GOTOWE_PROJEKTY\\GardenDataBase\\pobrane.png");
                        cell1.addElement(img);
                        cell1.setBorderColor(BaseColor.WHITE);
                        upTable.addCell(cell1);
                    } catch (IOException e){
                        System.out.println("Nie udało się wczytać obrazu. "+e.getMessage());
                    }
                    PdfPCell cell2 = new PdfPCell(new Paragraph());
                    Chunk chunk1 = new Chunk("Paszport ro\u015Blin/\nPlant Passport", font1);
                    cell2.addElement(chunk1);
                    cell2.setBorderColor(BaseColor.WHITE);
                    upTable.addCell(cell2);


                    PdfPCell cell3 = new PdfPCell(new Paragraph());
                    cell3.addElement(upTable);
                    cell3.setBorderColor(BaseColor.WHITE);

                    PdfPCell cellDown = new PdfPCell(new Paragraph());
                    Chunk chunk2 = new Chunk("A. "+flower.getNameLA()+"\nB. Numer gospodarstwa\nC. "+flower.getFlowerId()+"\nD. PL", font1);
                    cellDown.addElement(chunk2);
                    cellDown.setBorderColor(BaseColor.WHITE);
                    PdfPTable pt = new PdfPTable(1);
                    pt.addCell(cell3);
                    pt.addCell(cellDown);

                    pdfPTable.addCell(pt);
                }
            }


            document.add(pdfPTable);

            document.close();
        }catch (FileNotFoundException | DocumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    private void refresh(){

        ObservableList<String> passportsList = DataSource.getInstance().showPassports();
        passportList = DataSource.getInstance().passportList();
        flowersList = DataSource.getInstance().flowerList();
        shopList = DataSource.getInstance().shopList();

        flowersArrangementList = DataSource.getInstance().flowersArrangementList();
        List<String> newFlowersList = new ArrayList<>();

        passportsListView.setItems(passportsList);
        shopName.setEditable(false);
        passportNumber.setEditable(false);
        passportDate.setEditable(false);
//        flowersListView.setEditable(false);

        passportsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String o, String t1) {
                if(t1 != null) {
                    newFlowersList.clear();
                    String nazwa = passportsListView.getSelectionModel().getSelectedItem();
                    for (Passport passport : passportList) {
                        if (nazwa.equals(String.valueOf(passport.getPassportId()))) {
                            for(Shop shop : shopList){
                                if(shop.getShopId() == passport.getShopId())
                                    shopName.setText(shop.getName());
                            }
                            passportNumber.setText(String.valueOf(passport.getPassportId()));
                            System.out.println(passport.getDate());
                            if(!(passport.getDate().isEmpty())){
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate localDate = LocalDate.parse(passport.getDate(), formatter);
                                passportDate.setValue(localDate);
                            }else{
                                passportDate.setValue(null);
                            }
                        }
                    }
                    for(FlowersArrangement flowersArrangement : flowersArrangementList){
                        if(nazwa.equals(String.valueOf(flowersArrangement.getPassportId()))){
                            for(Flower flower : flowersList){
                                if(flowersArrangement.getFlowerId() == flower.getFlowerId()) {
                                    newFlowersList.add(flower.getNamePL());
                                }
                            }
                        }
                    }
                    ObservableList<String> flowersListToObserve = FXCollections.observableList(newFlowersList);
                    flowersListView.setItems(flowersListToObserve);
                }
            }
        });
    }

}

