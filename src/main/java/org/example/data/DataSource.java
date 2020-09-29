package org.example.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSource {

    private final String DB_NAME = "Garden.db";
    private final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\FM\\IdeaProjects\\GOTOWE_PROJEKTY\\GardenDataBase\\" + DB_NAME;

    public final String TABLE_FLOWERS = "flowers";
    private final String COLUMN_FLOWER_ID = "flowerId";
    private final String COLUMN_FLOWER_NAME_PL = "namePL";
    private final String COLUMN_FLOWER_NAME_LA = "nameLA";
    private final String COLUMN_FLOWER_REMOVED = "removed";

    public final String TABLE_FLOWERS_ARRANGEMENTS = "flowersArrangement";
    private final String COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID = "passportId";
    private final String COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID = "flowerId";

    public final String TABLE_PASSPORTS = "passports";
    private final String COLUMN_PASSPORT_ID = "passportId";
    private final String COLUMN_PASSPORT_SHOP_ID = "shopId";
    private final String COLUMN_PASSPORT_DATE = "date";
    private final String COLUMN_PASSPORT_REMOVED = "removed";

    public final String TABLE_SHOPS = "shops";
    private final String COLUMN_SHOP_ID = "shopId";
    private final String COLUMN_SHOP_NAME = "name";
    private final String COLUMN_SHOP_ADDRESS = "address";
    private final String COLUMN_SHOP_REMOVED = "removed";

    private String passportPath = "C:\\Users\\FM\\IdeaProjects\\GOTOWE_PROJEKTY\\GardenDataBase";
    private String pathDB;
    private String gardenNumber;

    private Connection connection;

    private static DataSource instance = new DataSource();

    private DataSource(){}

    public static DataSource getInstance(){
        return instance;
    }

    public boolean open(){
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Połączono z bazą danych.");
            return true;
        } catch (SQLException e){
            System.out.println("Nie można się połączyć z bazą danych: "+e.getMessage());
            return false;
        }
    }

    public List<Flower> flowerList(){
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM "+TABLE_FLOWERS);
            List<Flower> flowers = new ArrayList<>();
            while (result.next()){
                Flower flower = new Flower();
                flower.setFlowerId(result.getInt(COLUMN_FLOWER_ID));
                flower.setNamePL(result.getString(COLUMN_FLOWER_NAME_PL));
                flower.setNameLA(result.getString(COLUMN_FLOWER_NAME_LA));
                flowers.add(flower);
            }
            return flowers;
        } catch (SQLException e){
            System.out.println("Nie udało się zgrać danych.. "+e.getMessage());
            return null;
        }
    }

    public ObservableList<String> showFlowers(){
        List<String> newList = new ArrayList<>();
        List<Flower> flowers = flowerList();
        for(Flower flower : flowers){
            newList.add(flower.getNamePL());
        }
        ObservableList<String> observableList = FXCollections.observableList(newList);
        return observableList;
    }

    public List<Passport> passportList(){
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM "+TABLE_PASSPORTS);
            List<Passport> passports = new ArrayList<>();
            while (result.next()){
                Passport passport = new Passport();
                passport.setPassportId(result.getInt(COLUMN_PASSPORT_ID));
                passport.setDate(result.getString(COLUMN_PASSPORT_DATE));
                passport.setShopId(result.getInt(COLUMN_PASSPORT_SHOP_ID));
                passports.add(passport);
            }
            return passports;
        } catch (SQLException e){
            System.out.println("Nie udało się zgrać danych.. "+e.getMessage());
            return null;
        }
    }

    public ObservableList<String> showPassports(){
        List<String> newList = new ArrayList<>();
        List<Passport> passports = passportList();
        for(Passport passport : passports){
            newList.add(String.valueOf(passport.getPassportId()));
        }
        ObservableList<String> observableList = FXCollections.observableList(newList);
        return observableList;
    }

    public List<FlowersArrangement> flowersArrangementList(){
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM "+TABLE_FLOWERS_ARRANGEMENTS);
            List<FlowersArrangement> flowersArrangements = new ArrayList<>();
            while (result.next()){
                FlowersArrangement flowersArrangement = new FlowersArrangement();
                flowersArrangement.setFlowerId(result.getInt(COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID));
                flowersArrangement.setPassportId(result.getInt(COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID));
                flowersArrangements.add(flowersArrangement);
            }
            return flowersArrangements;
        } catch (SQLException e){
            System.out.println("Nie udało się zgrać danych.. "+e.getMessage());
            return null;
        }
    }

    public List<Shop> shopList(){
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM "+TABLE_SHOPS);
            List<Shop> shops = new ArrayList<>();
            while(result.next()){
                Shop shop = new Shop();
                shop.setShopId(result.getInt(COLUMN_SHOP_ID));
                shop.setAddress(result.getString(COLUMN_SHOP_ADDRESS));
                shop.setName(result.getString(COLUMN_SHOP_NAME));
                shops.add(shop);
            }
            return shops;
        } catch (SQLException e){
            System.out.println("Nie udało się zgrać danych.. "+e.getMessage());
            return null;
        }
    }

    public ObservableList<String> showShops(){
        List<String> newList = new ArrayList<>();
        List<Shop> shops = shopList();
        for(Shop shop : shops){
            newList.add(shop.getName());
        }
        newList.remove(0);
        ObservableList<String> observableList = FXCollections.observableList(newList);
        return observableList;
    }

    public boolean addFlower(String flowerNamePL, String flowerNameLA){
        try{
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO "+TABLE_FLOWERS+" ("+ COLUMN_FLOWER_NAME_PL+", "+COLUMN_FLOWER_NAME_LA +") VALUES('"+flowerNamePL+"', '"+flowerNameLA+"')");
            System.out.println("Udało się dodać "+flowerNamePL+", "+flowerNameLA+" do "+TABLE_FLOWERS);
            return true;
        } catch (SQLException e){
            System.out.println("Problem z dodaniem rekoru.. " + e.getMessage());
            return false;
        }
    }

    public boolean addShop(String shopName, String shopAdress){
        try{
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO "+TABLE_SHOPS+" ("+ COLUMN_SHOP_NAME+", "+COLUMN_SHOP_ADDRESS +") VALUES('"+shopName+"', '"+shopAdress+"')");
            System.out.println("Udało się dodać "+shopName+", "+shopAdress+" do "+TABLE_SHOPS);
            return true;
        } catch (SQLException e){
            System.out.println("Problem z dodaniem rekoru.. " + e.getMessage());
            return false;
        }
    }

    public boolean addFlowersToPassport(String shopName, String date, List<Integer> flowersValues){
        try{
            int shopId = -1;
            List<Shop> shopsList = shopList();
            for(Shop shop : shopsList){
                if(shop.getName().equals(shopName))
                    shopId = shop.getShopId();
            }
            System.out.println("Tworzenie paszportu..");
            Statement createPassport = connection.createStatement();
            createPassport.execute("INSERT INTO "+TABLE_PASSPORTS+"("+COLUMN_PASSPORT_DATE+", "+COLUMN_PASSPORT_SHOP_ID+") VALUES('"+date+"', "+shopId+")");


            int size2 = 0;
            List<Passport> listOfPassport = passportList();
            Iterator<Passport> passportIterator = listOfPassport.iterator();
            Passport obecnyPasssport;
            while(passportIterator.hasNext()){
                obecnyPasssport = passportIterator.next();
                System.out.println(obecnyPasssport.getPassportId());
                size2 = obecnyPasssport.getPassportId();
            }

            System.out.println("S:"+size2);

            String list = new String();
            for(int i=0; i<flowersValues.size(); i++){
                list=list+"("+flowersValues.get(i)+", "+size2+")";
                if(i<flowersValues.size()-1)
                    list=list+", ";
            }

            System.out.println("Tworzenie paszportu..");
            System.out.println(list);

            Statement addFlowers = connection.createStatement();
            addFlowers.execute("INSERT INTO "+TABLE_FLOWERS_ARRANGEMENTS+"("+COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID+", "+COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID+") VALUES "+list);
            System.out.println("Stworzono tabele");
            return true;
        } catch (SQLException e){
            System.out.println("Problem ze stworzeniem paszportu.." + e.getMessage());
            return false;
        }
    }

    public boolean deleteFlower(List<String> values){
        try{
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM "+TABLE_FLOWERS_ARRANGEMENTS+" WHERE "+COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID+" = "+values.get(0));
            statement.execute("DELETE FROM "+TABLE_FLOWERS+" WHERE "+COLUMN_FLOWER_NAME_PL+" = '"+values.get(1)+"'");

            System.out.println("Udało się usunąć "+values+" z " + TABLE_FLOWERS);
            return true;
        } catch (SQLException e){
            System.out.println("Problem z usunięciem rekoru.. "+e.getMessage());
            return false;
        }
    }

    public boolean deleteShop(List<String> values){
        try{
            Statement statement = connection.createStatement();
            statement.execute("UPDATE "+TABLE_PASSPORTS+" SET "+COLUMN_PASSPORT_SHOP_ID+" = -1 WHERE "+COLUMN_PASSPORT_SHOP_ID+" = "+values.get(0));
            statement.execute("DELETE FROM "+TABLE_SHOPS+" WHERE "+COLUMN_SHOP_NAME+" = '"+values.get(1)+"'");
            System.out.println("Udało się usunąć "+values.get(1)+" z " + TABLE_SHOPS);
            return true;
        } catch (SQLException e){
            System.out.println("Problem z usunięciem rekoru.. "+e.getMessage());
            return false;
        }
    }

    public boolean deletePassport(String values){
        try{
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM "+TABLE_FLOWERS_ARRANGEMENTS+" WHERE "+COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID+" = "+values);
            statement.execute("DELETE FROM "+TABLE_PASSPORTS+" WHERE "+COLUMN_PASSPORT_ID+" = "+values);
            System.out.println("Udało się usunąć "+values+" z " + TABLE_SHOPS);
            return true;
        } catch (SQLException e){
            System.out.println("Problem z usunięciem rekoru.. "+e.getMessage());
            return false;
        }
    }

    public boolean updateShop(String shopName, String shopAdress, int shopId){
        try{
            Statement statement = connection.createStatement();
            statement.execute("UPDATE "+TABLE_SHOPS+" SET "+COLUMN_SHOP_NAME+" = '"+shopName+"', "+COLUMN_SHOP_ADDRESS+" = '"+shopAdress+"' WHERE "+COLUMN_SHOP_ID+" = "+shopId);
            System.out.println("Udało się edytować sklep");
            return true;
        } catch (SQLException e){
            System.out.println("Problem z edyją rekoru.. "+e.getMessage());
            return false;
        }
    }

    public boolean updateFlower(String flowerNamePL, String flowerNameLA, int flowerId){
        try{
            Statement statement = connection.createStatement();
            statement.execute("UPDATE "+TABLE_FLOWERS+" SET "+COLUMN_FLOWER_NAME_PL+" = '"+flowerNamePL+"', "+COLUMN_FLOWER_NAME_LA+" = '"+flowerNameLA+"' WHERE "+COLUMN_FLOWER_ID+" = "+flowerId);
            System.out.println("Udało się edytować kwiat");
            return true;
        }catch (SQLException e){
            System.out.println("Problem z edycją rekoru.. "+e.getMessage());
            return false;
        }
    }

    public boolean updatePassport(int shopId, String passportDate, int passportId){
        try{
            Statement statement = connection.createStatement();
            statement.execute("UPDATE "+TABLE_PASSPORTS+" SET "+COLUMN_PASSPORT_SHOP_ID+" = "+shopId+", "+COLUMN_PASSPORT_DATE+" = '"+passportDate+"' WHERE "+COLUMN_PASSPORT_ID+" = "+passportId);
            System.out.println("Udało się edytować paszport");
            return true;
        } catch (SQLException e){
            System.out.println("Problem z edycją rekordu.. "+e.getMessage());
            return  false;
        }
    }

    public boolean updateFlowerInPassport(int passportId, List<Integer> flowersValues) {

        try {
            int size2 = passportId;
            String list = new String();
            for (int i = 0; i < flowersValues.size(); i++) {
                list = list + "(" + flowersValues.get(i) + ", " + size2 + ")";
                if (i < flowersValues.size() - 1)
                    list = list + ", ";
            }
            Statement addFlowers = connection.createStatement();
            addFlowers.execute("INSERT INTO " + TABLE_FLOWERS_ARRANGEMENTS + "(" + COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID + ", " + COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID + ") VALUES " + list);
            System.out.println("Dodano nowe kwiaty");
            return true;
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem kwiatów.. " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFlowerInPassport(int passportId, List<Integer> flowersValues){
        try {
            int size2 = passportId;
            String list = new String();
            for (int i = 0; i < flowersValues.size(); i++) {
                list = list + "(" + flowersValues.get(i) + ", " + size2 + ")";
                if (i < flowersValues.size() - 1)
                    list = list + ", ";
            }
            Statement deleteFlowers = connection.createStatement();
            //deleteFlowers.execute("DELETE FROM "+TABLE_FLOWERS_ARRANGEMENTS+" WHERE "+COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID+" = "+passportId+" AND "+COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID+" = "+flowersValues);
            for(int i=0; i<flowersValues.size(); i++){
                deleteFlowers.execute("DELETE FROM "+TABLE_FLOWERS_ARRANGEMENTS+" WHERE "+COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID+" = "+passportId+" AND "+COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID+" = "+flowersValues.get(i));
            }
            //addFlowers.execute("INSERT INTO " + TABLE_FLOWERS_ARRANGEMENTS + "(" + COLUMN_FLOWERS_ARRANGEMENT_FLOWER_ID + ", " + COLUMN_FLOWERS_ARRANGEMENT_PASSPORT_ID + ") VALUES " + list);
            System.out.println("Usunięto kwiaty z paszportu");
            return true;
        } catch (SQLException e) {
            System.out.println("Problem z dodaniem kwiatów.. " + e.getMessage());
            return false;
        }
    }

    public String getPassportPath() {
        return passportPath;
    }

    public void setPassportPath(String passportPath) {
        this.passportPath = passportPath;
    }

    public String getPathDB() {
        return pathDB;
    }

    public void setPathDB(String pathDB) {
        this.pathDB = pathDB;
    }

    public String getGardenNumber() {
        return gardenNumber;
    }

    public void setGardenNumber(String gardenNumber) {
        this.gardenNumber = gardenNumber;
    }
}
