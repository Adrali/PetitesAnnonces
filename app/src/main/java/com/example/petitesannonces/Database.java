package com.example.petitesannonces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static volatile Database database = null;
    Connection connexion = null;
    private Database() {
        // create three connections to three different databases on localhost
        try {
            // Connect method #1
            String dbURL1 = "jdbc:postgresql:ProductDB1?user=root&password=secret";
            connexion = DriverManager.getConnection(dbURL1);
            if (connexion != null) {
                System.out.println("Connected to database #1");
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

    public void closeDatabase(){
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
}


