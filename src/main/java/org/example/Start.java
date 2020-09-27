package org.example;

import java.sql.*;

public class Start {

    private static final String DB_NAME = "Garden.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\FM\\IdeaProjects\\GOTOWE_PROJEKTY\\GardenDataBase\\" + DB_NAME;

    private static final String TABLE_CONTACTS = "Sklepy";

    private static final String COLUMN_NAME = "Nazwa";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";

    public void weCanStart() {

        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

            statement.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            statement.execute("CREATE TABLE IF NOT EXISTS "+TABLE_CONTACTS+"("+COLUMN_NAME+" text)");

            insertContact(statement, "AAA");
            insertContact(statement, "AAB");
            insertContact(statement, "AAC");

            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CONTACTS);
            while (results.next()){
                System.out.println(results.getString(COLUMN_NAME) + " "+ results.getInt(COLUMN_PHONE)+" "+results.getString(COLUMN_EMAIL));
            }

            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    private void insertContact(Statement statement, String name) throws SQLException{
        statement.execute("INSERT INTO "+TABLE_CONTACTS+" ("+COLUMN_NAME+") VALUES('"
            +name+"')");
    }
}
