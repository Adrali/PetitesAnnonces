package com.example.petitesannonces;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Database {
    private static volatile Database database = null;
    public Connection connexion = null;

    private Database() {
        try {
            String dbURL = "jdbc:postgresql://localhost:5432/petites_annonces";
            String user = "appUser";
            String pass = "azerty";
            Class.forName("org.postgresql.Driver");
            connexion = DriverManager.getConnection(dbURL, user, pass);
            if (connexion != null) {
                Log.i("DB :","Connected to database");
            }else {
                Log.e("DB :","Echec de connexion");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    public int connectUser(String username, String password){
        String request = "SELECT id FROM \"Profile\" WHERE username = ? AND password = ?";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                return result.getInt("ID");
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return -1; // Id non trouvé
    }


    public boolean userExist(String username){
        String request = "SELECT id FROM \"Profile\" WHERE username = ?";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return false; // Id non trouvé
    }


    public boolean signInUser(String username, String password, String firstName, String name, String contactPhone, String contactMail){
        String requestVerif = "SELECT id FROM Profile WHERE username = ?";
        String requestAdd = "with ajout_profile as ( " +
                " insert into Profile (ID, username,password,, contactPhone,contactMail) values (default, ?, ?,?,?) " +
                " returning id " +
                ") " +
                "insert into Profile_Pers (ID, first_name, name) " +
                "values ((select id from new_order),?,?)";
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
            statement.setString(3,contactPhone);
            statement.setString(4,contactMail);
            statement.setString(5,firstName);
            statement.setString(6,name);

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
                " insert into Profile (ID, username,password,contactPhone,contactMail) values (default, ?,?,?,?) " +
                " returning id " +
                ") " +
                "insert into Profile_Pers (ID, companyName, website) " +
                "values ((select id from new_order),?,?)";
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
            statement.setString(3,contactPhone);
            statement.setString(4,contactMail);
            statement.setString(5,companyName);
            statement.setString(6,website);

            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return false; // Non inséré
    }

    public boolean ajoutAnnonce(int id_annonceur,String nom, Double prix, String description, byte[] image,String localisation){
        String request = "INSERT INTO annonce (id_annonce,titre,description,id_annonceur,image,localisation,prix) values (default,?,?,?,?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,nom);
            statement.setString(2,description);
            statement.setInt(3,id_annonceur);
            statement.setBytes(4,image);
            statement.setString(5,localisation);
            statement.setDouble(6,prix);

            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return false;
    }

    public boolean reportAnnonce(int id_profile, int id_annonce, String raison){
        /**Report une annonce**/

        String request = "INSERT INTO ReportAnnonce (id_profile,id_annonce,raison) values (?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_profile);
            statement.setInt(2,id_annonce);
            statement.setString(3,raison);
            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return false;
    }

    public boolean ajoutFavoris(int id_profile, int id_annonce){
        /**Ajoute une annonce des favoris d'un utilisateur**/
        String request = "INSERT INTO ReportAnnonce (id_profile,id_annonce,raison) values (?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_profile);
            statement.setInt(2,id_annonce);
            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return false;
    }

    public boolean retirerFavoris(int id_profile, int id_annonce){
        /**Retire une annonce des favoris d'un utilisateur**/
        String request = "DELETE FROM ReportAnnonce WHERE id_profile = ? AND id_annonce = ?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_profile);
            statement.setInt(2,id_annonce);
            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return false;
    }

    public boolean envoyerMessage(int id_profile_emmeteur, int id_profile_recepteur, String message){
        /**Retire une annonce des favoris d'un utilisateur**/
        String request = "INSERT INTO Messages (id_profile_emetteur,id_profile_recepteur,message) values (?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_profile_emmeteur);
            statement.setInt(2,id_profile_recepteur);
            statement.setString(3,message);
            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return false;
    }


    public List<String> obtenirMessage(int id_profile_1, int id_profile_2, String message){
        String request = "SELECT message FROM \"Profile\" WHERE (id_profile_emetteur = ? AND id_profile_recepteur = ?) OR (id_profile_emetteur = ? AND id_profile_recepteur = ?) ORDER BY date_envoie ASC";
        ArrayList<String> messages = new ArrayList<String>();
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_profile_1);
            statement.setInt(2,id_profile_2);
            statement.setInt(3,id_profile_2);
            statement.setInt(4,id_profile_1);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                messages.add(result.getString("message"));
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return messages; // Id non trouvé
    }

    public List<AnnonceModel> rechercheAnnonces(String nom, String localisation){
        String request = "SELECT * FROM \"Annonce\" WHERE nom LIKE ?";
        ArrayList<AnnonceModel> annonces = new ArrayList<AnnonceModel>();
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,"%"+localisation+"%");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                AnnonceModel a = new AnnonceModel(result.getInt("id_annonce"),
                        result.getInt("id_annonceur"),
                        result.getString("description"),
                        result.getDouble("prix"),
                        ImageProcessing.byteArrayToBitmap(result.getBytes("image")),
                        result.getString("titre"));

                annonces.add(a);
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return annonces;
    }

    public List<AnnonceModel> rechercheAnnoncesFavori(int idPersonne){
        String request = "SELECT * FROM \"Annonce\",\"AnnoncesSave\" WHERE AnnoncesSave.id_profile = ? AND AnnoncesSave.id_profile = Annonce.id_annonceur";
        ArrayList<AnnonceModel> annonces = new ArrayList<AnnonceModel>();
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,idPersonne);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                AnnonceModel a = new AnnonceModel(result.getInt("id_annonce"),
                        result.getInt("id_annonceur"),
                        result.getString("description"),
                        result.getDouble("prix"),
                        ImageProcessing.byteArrayToBitmap(result.getBytes("image")),
                        result.getString("titre"));

                annonces.add(a);
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return annonces;
    }

    public List<AnnonceModel> rechercheMesAnnonces(int idPersonne){
        String request = "SELECT * FROM \"Annonce\" WHERE Annonce.id_annonceur = ?";
        ArrayList<AnnonceModel> annonces = new ArrayList<AnnonceModel>();
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,idPersonne);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                AnnonceModel a = new AnnonceModel(result.getInt("id_annonce"),
                        result.getInt("id_annonceur"),
                        result.getString("description"),
                        result.getDouble("prix"),
                        ImageProcessing.byteArrayToBitmap(result.getBytes("image")),
                        result.getString("titre"));

                annonces.add(a);
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return annonces;
    }

    public boolean supprimerAnnonce(int id_annonce){
        String request = "DELETE FROM Annonce WHERE id_annonce = ?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_annonce);
            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return false;
    }

    public boolean isConnected(){
        return connexion != null;
    }


}
