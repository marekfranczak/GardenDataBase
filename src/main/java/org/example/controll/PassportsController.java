package org.example.controll;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
            System.out.println("Window cannot be opened.. "+e.getMessage());
            e.printStackTrace();
            return;
        }
        AddFlowersController controller = fxmlLoader.getController();
        controller.setData(Integer.parseInt(passportNumber.getText()));

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            controller.add();
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
            System.out.println("Window cannot be opened.. " + e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK){
            AddPassportController controller =  fxmlLoader.getController();
            controller.end();
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
                refresh();
            }
        }
    }

    @FXML
    private void edit(){
        if(editButton.isSelected()){

            String shopChoosed = passportsListView.getSelectionModel().getSelectedItem();

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
            generateButton.setDisable(true);
            shopName.setEditable(true);
            passportDate.setEditable(true);
        }
        else{
            String shopNameText = shopName.getText();
            String passportDateText = passportDate.getValue().toString();
            int shopId = -1;
            for(Shop shop : shopList){
                if (shopNameText.equals(shop.getName()))
                    shopId = shop.getShopId();
            }
            String passportNumberText = passportNumber.getText();
            int passportNumberInt = Integer.parseInt(passportNumberText);
            DataSource.getInstance().updatePassport(shopId, passportDateText, passportNumberInt);
            editButton.setText("Edytuj");
            passportsListView.setEditable(true);
            shopName.setEditable(false);
            passportDate.setEditable(false);
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            addFlowersButton.setDisable(false);
            deleteFlowersButton.setDisable(false);
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
            System.out.println("Window cannot be opened.. "+e.getMessage());
            e.printStackTrace();
            return;
        }
        AddFlowersController controller = fxmlLoader.getController();
        controller.setData(Integer.parseInt(passportNumber.getText()));

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            controller.delete();
        }

        refresh();
    }

    @FXML
    private void generatePassport(){
        try {
            Document document = new Document();
            int passportID = Integer.parseInt(passportNumber.getText());
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

            Font font = FontFactory.getFont(String.valueOf(Font.FontFamily.TIMES_ROMAN), BaseFont.IDENTITY_H, true);

            try {
                BaseFont bf = BaseFont.createFont("C:\\Users\\FM\\IdeaProjects\\GOTOWE_PROJEKTY\\GardenDataBase\\arial.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
                font = new Font(bf, 8);
            }catch(IOException e){
                System.out.println("Font loading error.."+e.getMessage());
                e.printStackTrace();
            }



            for(Flower flower : flowersList){
                if(flowerIdList.contains(flower.getFlowerId())){
                    newFlower.add(flower);
                    PdfPTable upCell = new PdfPTable(2);

                    PdfPCell imageCell = new PdfPCell(new Paragraph());
                    try {
                        Image img = Image.getInstance("C:\\Users\\FM\\IdeaProjects\\GOTOWE_PROJEKTY\\GardenDataBase\\pobrane.png");
                        imageCell.addElement(img);
                        imageCell.setBorderColor(BaseColor.WHITE);
                        upCell.addCell(imageCell);
                    } catch (IOException e){
                        System.out.println("Image loading error.. "+e.getMessage());
                        e.printStackTrace();
                    }
                    PdfPCell textCell = new PdfPCell(new Paragraph());
                    Chunk chunk1 = new Chunk("Paszport ro\u015Blin/\nPlant Passport", font);
                    textCell.addElement(chunk1);
                    textCell.setBorderColor(BaseColor.WHITE);
                    upCell.addCell(textCell);


                    PdfPCell mainCell = new PdfPCell(new Paragraph());
                    mainCell.addElement(upCell);
                    mainCell.setBorderColor(BaseColor.WHITE);

                    PdfPCell downCell = new PdfPCell(new Paragraph());
                    Chunk dataText = new Chunk("A. "+flower.getNameLA()+"\nB. Numer gospodarstwa\nC. "+flower.getFlowerId()+"\nD. PL", font);
                    downCell.addElement(dataText);
                    downCell.setBorderColor(BaseColor.WHITE);
                    PdfPTable pt = new PdfPTable(1);
                    pt.addCell(mainCell);
                    pt.addCell(downCell);

                    pdfPTable.addCell(pt);
                }
            }

            pdfPTable.completeRow();
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

        passportsListView.getSelectionModel().selectedItemProperty().addListener((ov, o, t1) -> {
            if (!editButton.isSelected()) {
                if (t1 != null) {
                    newFlowersList.clear();
                    String selectedPassport = passportsListView.getSelectionModel().getSelectedItem();
                    String[] passportStrings = selectedPassport.split("\\|");
                    for (Passport passport : passportList) {
                        if (passportStrings[2].equals(String.valueOf(passport.getPassportId()))) {
                            for (Shop shop : shopList) {
                                if (shop.getShopId() == passport.getShopId())
                                    shopName.setText(shop.getName());
                            }
                            passportNumber.setText(String.valueOf(passport.getPassportId()));
                            if (!(passport.getDate().isEmpty())) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate localDate = LocalDate.parse(passport.getDate(), formatter);
                                passportDate.setValue(localDate);
                            } else {
                                passportDate.setValue(null);
                            }
                        }
                    }
                    for (FlowersArrangement flowersArrangement : flowersArrangementList) {
                        if (passportStrings[2].equals(String.valueOf(flowersArrangement.getPassportId()))) {
                            for (Flower flower : flowersList) {
                                if (flowersArrangement.getFlowerId() == flower.getFlowerId()) {
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

