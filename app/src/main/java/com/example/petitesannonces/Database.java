package com.example.petitesannonces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static volatile Database database = null;
    public Connection connexion = null;
    private Database() {
        // create three connections to three different databases on localhost
        try {
            String dbURL3 = "jdbc:postgresql://localhost:5432/ProductDB3";
            Properties parameters = new Properties();
            parameters.put("user", "root");
            parameters.put("password", "secret");

            connexion = DriverManager.getConnection(dbURL3, parameters);
            if (connexion != null) {
                System.out.println("Connected to database #3");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Database getInstance() {
        Database result = database;
        if (result != null) {
            return result;
        }
        synchronized(Database.class) {
            if (database == null) {
                database = new Database();
            }
            return database;
        }
    }

    private void closeDatabase(){
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public long connectUser(String username, String password){
        String request = "SELECT id FROM Profile WHERE username = ? AND password = ?";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                return result.getLong("ID");
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return -1; // Id non trouvé
    }


    public boolean signInUser(String username, String password, String firstName, String name, String contactPhone, String contactMail){
        String requestVerif = "SELECT id FROM Profile WHERE username = ?";
        String requestAdd = "with ajout_profile as ( " +
                " insert into Profile (ID, username,password) values (default, ?, ?) " +
                " returning id " +
                ") " +
                "insert into Profile_Pers (ID, first_name, name, contactPhone,contactMail) " +
                "values ((select id from new_order),?,?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(requestVerif);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            while(result.next()){ //L'utilisateur existe déjà
                return false;
            }

            statement = connexion.prepareStatement(requestAdd);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setString(3,firstName);
            statement.setString(4,name);
            statement.setString(5,contactPhone);
            statement.setString(6,contactMail);
            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return false; // Non inséré
    }

    public boolean signInpro(String username, String password, String companyName, String website, String contactPhone, String contactMail){
        String requestVerif = "SELECT id FROM Profile WHERE username = ?";
        String requestAdd = "with ajout_profile as ( " +
                " insert into Profile (ID, username,password) values (default, ?, ?) " +
                " returning id " +
                ") " +
                "insert into Profile_Pers (ID, companyName, website, contactPhone,contactMail) " +
                "values ((select id from new_order),?,?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(requestVerif);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            while(result.next()){ //L'utilisateur existe déjà
                return false;
            }

            statement = connexion.prepareStatement(requestAdd);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setString(3,companyName);
            statement.setString(4,website);
            statement.setString(5,contactPhone);
            statement.setString(6,contactMail);
            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return false; // Non inséré
    }



}


