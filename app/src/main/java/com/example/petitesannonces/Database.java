package com.example.petitesannonces;

import android.media.Image;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Database est un singleton qui se connecte à une base de donnée PSQL**/
public class Database {
    private static volatile Database database = null;
    public Connection connexion = null;

    /** Contructeur privé de la BDD**/
    private Database() {
        try {
            String dbURL = "jdbc:postgresql://localhost:5432/petites_annonces";
            String user = "appUser";
            String pass = "azerty";
            Class.forName("org.postgresql.Driver");
            connexion = DriverManager.getConnection(dbURL, user, pass);
            if (connexion != null) {
                System.out.println("DB : Connected to database");
            }else {
                System.out.println("DB : Echec de connexion");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /** Retourne l'instantce de la bbd**/
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
    /** Ferme la connexion**/
    private void closeDatabase(){
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /** Retourne l'id de l'utilisateur, -1 si celui ci n'existe pas**/
    public int connectUser(String username, String password){
        String request = "SELECT id FROM \"Profil\" WHERE username = ? AND password = ?";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt("ID");
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return -1; // Id non trouvé
    }
    /** Retourne le UserModel d'un utilisateur **/
    public UserModel getUser(int idUser){
        String request = "SELECT * FROM \"Profil\" WHERE id = ?" +
                "";
        ResultSet result_gene = null,result_name = null;
        String name = "";
        try{
            //On cherche les infos principales
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,idUser);
            result_gene = statement.executeQuery();
            if(result_gene.next()){
                //On regarde si c'est un particulier
                request = "SELECT * FROM \"Profil_Pers\" WHERE id = ?";
                statement = connexion.prepareStatement(request);
                statement.setInt(1,idUser);
                result_name = statement.executeQuery();
                if(result_name.next()){
                    name = result_name.getString("firstName") +" "+ result_name.getString("lastName");
                }else{
                    //Sinon c'est un pro
                    request = "SELECT * FROM \"Profil_Pro\" WHERE id = ?";
                    statement = connexion.prepareStatement(request);
                    statement.setInt(1,idUser);
                    result_name = statement.executeQuery();
                    if(!result_name.next()) {
                        //Il y a une erreur sur le profil
                        return null;
                    }else{
                        name = result_name.getString("companyName");
                    }
                }
                return new UserModel(idUser,name,result_gene.getString("contactPhone"),result_gene.getString("contactMail"));
            }else{
                return null;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return null;
    }
    /** Retourne vrai si l'utilisateur existe a partir de son username**/
    public boolean userExist(String username){
        String request = "SELECT id FROM \"Profil\" WHERE username = ?";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return false; // Id non trouvé
    }

    /** Inscrit un particulier, retourne vrai si l'ajout a réussi **/
    public boolean signInUser(String username, String password, String firstName, String name, String contactPhone, String contactMail){
        String requestVerif = "SELECT id FROM \"Profil\" WHERE username = ?";
        String requestAdd = "with ajout_profile as ( " +
                " insert into \"Profil\" (ID, username,password, \"contactPhone\",\"contactMail\") values (default, ?,?,?,?) " +
                " returning id " +
                ") " +
                "insert into \"Profil_Pers\" (ID,\"firstName\",\"lastName\") " +
                "values ((select id from ajout_profile),?,?)";
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

    /**Inscrit un professionel, retourne vrai si l'ajout a réussi **/
    public boolean signInPro(String username, String password, String companyName, String website, String contactPhone, String contactMail){
        String requestVerif = "SELECT id FROM \"Profil\" WHERE username = ?";
        String requestAdd = "with ajout_profile as ( " +
                " insert into \"Profil\" (ID, username,password,\"contactPhone\",\"contactMail\") values (default, ?,?,?,?) " +
                " returning id " +
                ") " +
                "insert into \"Profil_Pro\" (ID, \"companyName\", website) " +
                "values ((select id from ajout_profile),?,?)";
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

    /** Ajoute une annonce, retourne vrai si cela a réussi**/
    public boolean ajoutAnnonce(int id_annonceur,String nom, Double prix, String description, byte[] image,String localisation,String categorie){
        String request = "INSERT INTO \"Annonce\" (id_annonce,titre,description,id_annonceur,image,localisation,prix,categorie) values (default,?,?,?,?,?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,nom);
            statement.setString(2,description);
            statement.setInt(3,id_annonceur);
            statement.setBytes(4,image);
            statement.setString(5,localisation);
            statement.setDouble(6,prix);
            statement.setString(7,categorie);

            int rows = statement.executeUpdate();
            if(rows>0){ // L'utilisateur à été inséré
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return false;
    }

    /** Report une annonce, vrai si cela a réussi**/
    public boolean reportAnnonce(int id_profil, int id_annonce, String raison){
        /**Report une annonce**/

        String request = "INSERT INTO \"ReportAnnonce\" (id_profil,id_annonce,raison) values (?,?,?)";
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_profil);
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

    /** Ajoute une annonce aux favoris d'un user, vrai si cela a réussi**/
    public boolean ajoutFavoris(int id_profile, int id_annonce){
        /**Ajoute une annonce des favoris d'un utilisateur**/
        String request = "INSERT INTO \"AnnoncesSaves\" (id_profil,id_annonce) values (?,?)";
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

    /** Retire une annonce des favoris d'un user, vrai si cela a réussi**/
    public boolean retirerFavoris(int id_profile, int id_annonce){
        /**Retire une annonce des favoris d'un utilisateur**/
        String request = "DELETE FROM \"AnnoncesSaves\" WHERE id_profil = ? AND id_annonce = ?";
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
    /** Retourne vrai si une annonce est dans les favoris d'un profile**/
    public boolean estFavoris(int id_profile, int id_annonce){
        String request = "SELECT * FROM \"AnnoncesSaves\" WHERE (id_profil = ? AND id_annonce = ?)";
        ArrayList<String> messages = new ArrayList<String>();
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_profile);
            statement.setInt(2,id_annonce);

            ResultSet result = statement.executeQuery();
            if(result.next()){
                return true;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return false; // Id non trouvé
    }
    /** Envoie un message d'un utilisateur vers un autre**/
    public boolean envoyerMessage(int id_profile_emmeteur, int id_profile_recepteur, String message){
        /**Retire une annonce des favoris d'un utilisateur**/
        String request = "INSERT INTO \"Messages\" (id_profil_emetteur,id_profil_recepteur,message) values (?,?,?)";
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

    /** Retourne la liste des message entre deux utilisateurs**/
    public List<MessageModel> obtenirMessage(int idUser, int idInterlocuteur){
        String request = "SELECT * FROM \"Messages\" WHERE (id_profil_emetteur = ? AND id_profil_recepteur = ?) OR (id_profil_emetteur = ? AND id_profil_recepteur = ?) ORDER BY date_envoie ASC";
        ArrayList<MessageModel> messages = new ArrayList<MessageModel>();
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,idUser);
            statement.setInt(2,idInterlocuteur);
            statement.setInt(3,idInterlocuteur);
            statement.setInt(4,idUser);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                messages.add(new MessageModel(result.getString("message"),result.getInt("id_profil_emetteur") == idUser ? true:false));
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return messages; // Id non trouvé
    }
    /** Retourne la liste des user anyant une conversation avec idUser **/
    public List<UserModel> obtenirInterlocuteurs(int idUser){
        String request = "SELECT * FROM \"Profil\",\"Messages\" WHERE (id_profil_emetteur = ? OR id_profil_recepteur = ?) ORDER BY date_envoie ASC";
        ArrayList<UserModel> users = new ArrayList<UserModel>();
        Set<Integer> idUserObtains = new HashSet<Integer>();
        idUserObtains.add(idUser);
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,idUser);
            statement.setInt(2,idUser);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                if(!idUserObtains.contains(result.getInt("id_profil_emetteur"))){
                    idUserObtains.add(result.getInt("id_profil_emetteur"));
                    users.add(getUser(result.getInt("id_profil_emetteur")));
                }
                if(!idUserObtains.contains(result.getInt("id_profil_recepteur"))) {
                    idUserObtains.add(result.getInt("id_profil_recepteur"));
                    users.add(getUser(result.getInt("id_profil_recepteur")));
                }
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }

        return users; // Id non trouvé
    }

    /** Retourne la liste d'une recherche d'annonce**/
    public List<AnnonceModel> rechercheAnnonces(String nom, String localisation,String categorie){
        String request = "SELECT * FROM \"Annonce\" WHERE (titre LIKE ?) AND categorie = ? AND localisation = ?";
        ArrayList<AnnonceModel> annonces = new ArrayList<AnnonceModel>();
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setString(1,"%"+nom+"%");
            statement.setString(2,categorie);
            statement.setString(3,localisation);
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

    /** Retourne le AnnonceModel d'une annonce a partir de son Id**/
    public AnnonceModel rechercheAnnonce(int id_annonce){
        String request = "SELECT * FROM \"Annonce\" WHERE id_annonce = ?";
        AnnonceModel annonce;
        try{
            PreparedStatement statement = connexion.prepareStatement(request);
            statement.setInt(1,id_annonce);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return new AnnonceModel(result.getInt("id_annonce"),
                        result.getInt("id_annonceur"),
                        result.getString("description"),
                        result.getDouble("prix"),
                        ImageProcessing.byteArrayToBitmap(result.getBytes("image")),
                        result.getString("titre"));

            }else{
                return null;
            }
        }catch(java.sql.SQLException e){
            System.out.println("Erreur sql : " + e);
        }
        return null;
    }

    /** Retourne la liste des annonces favoris d'un utilisateur**/
    public List<AnnonceModel> rechercheAnnoncesFavori(int idPersonne){
        String request = "SELECT * FROM \"Annonce\", \"AnnoncesSaves\" WHERE \"AnnoncesSaves\".id_profil = ? AND \"AnnoncesSaves\".id_annonce = \"Annonce\".id_annonce";
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

    /** Retourne la liste des annonces publiée par un utilisateur**/
    public List<AnnonceModel> rechercheMesAnnonces(int idPersonne){
        String request = "SELECT * FROM \"Annonce\" WHERE \"Annonce\".id_annonceur = ?";
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
    /** Supprime une annonce,  retourne vrai si elle a bien été supprimée**/
    public boolean supprimerAnnonce(int id_annonce){
        String request = "DELETE FROM \"Annonce\" WHERE id_annonce = ?";
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

    /** Retourne vrai si le signleton est connecté à la BDD**/
    public boolean isConnected(){
        return connexion != null;
    }


}
