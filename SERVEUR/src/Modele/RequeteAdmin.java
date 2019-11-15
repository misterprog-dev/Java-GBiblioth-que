/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class RequeteAdmin {
    private static Connecter conn = new Connecter();
    private static Statement stm, stm2, stm3;
    private static ResultSet Rs;
    
    //Les attributs
    private String ref;
    private String titre;
    private String genre;
    private String edition;
    private String nomAuteur;
    private String prenomAuteur;
    private String emailAd;
    private String nomAdh;
    private String prenomAdh;
    private String villeAdh;
    private String contactAdh;

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public void setPrenomAuteur(String prenomAuteur) {
        this.prenomAuteur = prenomAuteur;
    }

    public void setNomAdh(String nomAdh) {
        this.nomAdh = nomAdh;
    }

    public void setPrenomAdh(String prenomAdh) {
        this.prenomAdh = prenomAdh;
    }

    public void setVilleAdh(String villeAdh) {
        this.villeAdh = villeAdh;
    }

    public void setContactAdh(String contactAdh) {
        this.contactAdh = contactAdh;
    }
    private String mdpAdh;
    

    public String getTitre() {
        return titre;
    }

    public String getGenre() {
        return genre;
    }

    public String getEdition() {
        return edition;
    }

    public String getNomAuteur() {
        return nomAuteur;
    }

    public String getPrenomAuteur() {
        return prenomAuteur;
    }

    public String getNomAdh() {
        return nomAdh;
    }

    public String getPrenomAdh() {
        return prenomAdh;
    }

    public String getVilleAdh() {
        return villeAdh;
    }

    public String getContactAdh() {
        return contactAdh;
    }
 
    public RequeteAdmin(){
        
    }
    
    public static void addBook(String ref, String titre, String genre, String edition, String nomAuteur, String prenomAuteur){
        
        Boolean trouve = false;
        try {
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery("SELECT ref FROM livre WHERE ref = "+AdaptToSqlString(ref));
            while(res.next()){
                trouve = true;
            }
            if (trouve) {
                JOptionPane.showMessageDialog(null,"Ce livre existe déja, vous pouvez augmenter le nombre","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                //On l'insère dans la BD
                 String requete="INSERT INTO livre VALUES("+AdaptToSqlString(ref)+","+AdaptToSqlString(titre)+","+AdaptToSqlString(genre)+","+AdaptToSqlString(edition)
                         +","+AdaptToSqlString(nomAuteur)+","+AdaptToSqlString(prenomAuteur)+", NOW(), 1)"; 
                stm2.executeUpdate(requete);
                //JOptionPane.showMessageDialog(null,"Livre enregistré avec succès","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
                stm.close();
                stm2.close();
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    public static void updateBook(String ref, String titre, String genre, String edition, String nomAuteur, String prenomAuteur){
        
        try {
                //On fait la mise à jour dans la BD
                 String requete="UPDATE livre"
                         + " SET titre="+AdaptToSqlString(titre)+", genre = "+AdaptToSqlString(genre)+
                         ", edition="+AdaptToSqlString(edition)+", nomAuteur = "+AdaptToSqlString(nomAuteur)
                         +", prenomAuteur="+AdaptToSqlString(prenomAuteur)+
                         " WHERE ref = "+AdaptToSqlString(ref)+" ;"; 
                
                stm = conn.obtenirConnexion().createStatement();
                stm.executeUpdate(requete);
                
                    
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    public static void deleteBook(String ref){
        
        try {
                //On fait la suppression dans la BD
                String requete="DELETE FROM livre WHERE ref = "+AdaptToSqlString(ref)+" ;"; 
                stm = conn.obtenirConnexion().createStatement();
                stm.executeUpdate(requete);
                            
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
     public static void AddNumberBook(String ref,int nbre){
        
        try {
            
                stm = conn.obtenirConnexion().createStatement();
                
                ResultSet nbreExpl = stm.executeQuery("SELECT nbreExemplaire FROM livre WHERE ref = "+AdaptToSqlString(ref));
                nbreExpl.next();
                System.out.println(nbreExpl.getString("nbreExemplaire"));
                
                int nbreLivre = Integer.valueOf(nbreExpl.getString("nbreExemplaire")).intValue();
                
                //On fait la mise à jour dans la BD
                String requete="UPDATE livre SET nbreExemplaire = "+(nbre+nbreLivre)+" WHERE ref = "+AdaptToSqlString(ref)+";"; 
                stm.executeUpdate(requete);
                            
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public static DefaultTableModel viewBooks(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Réf");
        model.addColumn("Titre");
        model.addColumn("Genre");
        model.addColumn("Edition");
        model.addColumn("Nom auteur");
        model.addColumn("Prénom auteur");
        model.addColumn("Date enregistrement");
        model.addColumn("Nombre exemplaire");

        try {
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(" SELECT ref,titre,genre,edition,nomAuteur,prenomAuteur,dateEnregistrement,nbreExemplaire FROM livre "); 
            //res.beforeFirst();
            while(res.next()){
           
                    model.addRow(new Object[]{res.getString("ref"),res.getString("titre"),
                        res.getString("genre"),res.getString("edition"),
                        res.getString("nomAuteur"),res.getString("prenomAuteur"),
                        res.getString("dateEnregistrement"),res.getString("nbreExemplaire")});       
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
    
    public static DefaultTableModel viewBooksBy(String type, String info){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Réf");
        model.addColumn("Titre");
        model.addColumn("Genre");
        model.addColumn("Edition");
        model.addColumn("Nom auteur");
        model.addColumn("Prénom auteur");
        model.addColumn("Date enregistrement");
        model.addColumn("Nombre exemplaire");

        try {
            
            String requete="";
            if (type == "Par auteur") {
                requete = " SELECT ref,titre,genre,edition,nomAuteur,prenomAuteur,dateEnregistrement,nbreExemplaire FROM livre WHERE nomAuteur LIKE %" + 
                        AdaptToSqlString(info) +"% OR prenomAuteur LIKE %"+AdaptToSqlString(info) +"%;";
            } else if(type == "Par titre"){
                requete = " SELECT ref,titre,genre,edition,nomAuteur,prenomAuteur,dateEnregistrement,nbreExemplaire FROM livre WHERE titre LIKE %" +AdaptToSqlString(info) +"% ;";
            }else{
                requete = " SELECT ref,titre,genre,edition,nomAuteur,prenomAuteur,dateEnregistrement,nbreExemplaire FROM livre WHERE genre LIKE %" +AdaptToSqlString(info) +"% ;";
            }
            
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(requete); 
            //res.beforeFirst();
            while(res.next()){
           
                    model.addRow(new Object[]{res.getString("ref"),res.getString("titre"),
                        res.getString("genre"),res.getString("edition"),
                        res.getString("nomAuteur"),res.getString("prenomAuteur"),
                        res.getString("dateEnregistrement"),res.getString("nbreExemplaire")});       
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
    
    public static DefaultTableModel viewBooksReserved(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nom demandeur");
        model.addColumn("Réf livre");
        model.addColumn("Titre livre");
        model.addColumn("Auteur livre");
        model.addColumn("Date de demande");
        model.addColumn("Date de confirmation");
        try {
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(" SELECT nomdemandeur,reflivre ,titreoeuvre ,auteuroeuvre ,datedemande,datereservation FROM demandereservation WHERE decision='O' "); 
            //res.beforeFirst();
            while(res.next()){
           
                    model.addRow(new Object[]{res.getString("nomdemandeur"),res.getString("reflivre"),
                        res.getString("titreoeuvre"),res.getString("auteuroeuvre"),
                        res.getString("datedemande"),res.getString("datereservation")});       
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
    
    public static DefaultTableModel viewBooksToReserve(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nom demandeur");
        model.addColumn("Réf livre");
        model.addColumn("Titre livre");
        model.addColumn("Auteur livre");
        model.addColumn("Date de demande");
        try {
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(" SELECT nomdemandeur,reflivre ,titreoeuvre ,auteuroeuvre ,datedemande FROM demandereservation WHERE decision='N' "); 
            //res.beforeFirst();
            while(res.next()){
           
                    model.addRow(new Object[]{res.getString("nomdemandeur"),res.getString("reflivre"),
                        res.getString("titreoeuvre"),res.getString("auteuroeuvre"),
                        res.getString("datedemande")});       
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
    
    public static void saveReservation(String ref) {
        try {
                stm = conn.obtenirConnexion().createStatement();
                
                //On fait la mise à jour dans la BD
                String requete="UPDATE demandereservation SET decision = 'O' WHERE reflivre = "+AdaptToSqlString(ref); 
                
                stm.executeUpdate(requete);
                          
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public static void addClient(String email, String nom, String prenom, String ville, String contact, String mdp){
        Boolean trouve = false;
        try {
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery("SELECT email FROM adherent WHERE email = "+AdaptToSqlString(email));
            while(res.next()){
                trouve = true;
            }
            if (trouve) {
                JOptionPane.showMessageDialog(null,"Cet émail existe déja !!!","Ajout de d'adhérent",JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                //On l'insère dans la BD
                 String requete="INSERT INTO adherent VALUES("+AdaptToSqlString(email)+","+AdaptToSqlString(nom)+
                         ","+AdaptToSqlString(prenom)+","+AdaptToSqlString(ville)
                         +","+AdaptToSqlString(contact)+","+AdaptToSqlString(mdp)+", NOW())"; 
                stm = conn.obtenirConnexion().createStatement();
                stm.executeUpdate(requete);
                
                
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public static void deleteClient(String email){
        try {
                //On fait la suppression dans la BD
                String requete="DELETE FROM adherent WHERE email = "+AdaptToSqlString(email)+" ;"; 
                stm = conn.obtenirConnexion().createStatement();
                stm.executeUpdate(requete);
                
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public static DefaultTableModel viewadherent(String type, String info){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Email");
        model.addColumn("Nom");
        model.addColumn("Prenom");
        model.addColumn("Ville");
        model.addColumn("Contact");
        model.addColumn("Date Inscription");

        try {
            
            String requete="";
            if (type.equals("Nom")) {
                requete = " SELECT email,nom,prenom,ville,contact,dateInscription FROM adherent WHERE nom LIKE %" + 
                        AdaptToSqlString(info) +"% OR prenom LIKE %"+AdaptToSqlString(info) +"%;";
            } else if(type.equals("Prenom")){
               requete = " SELECT email,nom,prenom,ville,contact,dateInscription FROM adherent WHERE nom LIKE %" + 
                        AdaptToSqlString(info) +"% OR prenom LIKE %"+AdaptToSqlString(info) +"%;";
            }else if(type.equals("Email")){
                requete = " SELECT email,nom,prenom,ville,contact,dateInscription FROM adherent WHERE email LIKE %" +AdaptToSqlString(info) +"%;";
            }else if(type.equals("Ville")){
                requete = " SELECT email,nom,prenom,ville,contact,dateInscription FROM adherent WHERE ville LIKE %" +AdaptToSqlString(info) +"%;";
            }
            else{
               requete = " SELECT email,nom,prenom,ville,contact,dateInscription FROM adherent WHERE contact LIKE %" +AdaptToSqlString(info) +"%;"; 
            }
            
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(requete); 
            //res.beforeFirst();
            while(res.next()){
           
                    model.addRow(new Object[]{res.getString("email"),res.getString("nom"),
                        res.getString("prenom"),res.getString("ville"),
                        res.getString("contact"),res.getString("dateInscription")});       
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
    
    public static DefaultTableModel viewadherentAll(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Email");
        model.addColumn("Nom");
        model.addColumn("Prenom");
        model.addColumn("Ville");
        model.addColumn("Contact");
        model.addColumn("Date Inscription");

        try {
               
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(" SELECT email,nom,prenom,ville,contact,dateInscription FROM adherent "); 
            //res.beforeFirst();
            while(res.next()){
           
                    model.addRow(new Object[]{res.getString("email"),res.getString("nom"),
                        res.getString("prenom"),res.getString("ville"),
                        res.getString("contact"),res.getString("dateInscription")});       
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
    
    public static boolean  addLoaning(String ref, int nbreLivre, String emailEmprunt){
        try {
            Boolean trouve = false;
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery("SELECT ref FROM livre WHERE ref = "+AdaptToSqlString(ref));
            while(res.next()){
                trouve = true;
                
            }
            
            if(!trouve){
                return false;
            }else{
                ResultSet nbreExpl = stm2.executeQuery("SELECT nbreExemplaire FROM livre WHERE ref = "+AdaptToSqlString(ref));
                nbreExpl.next();
                int nbre = Integer.valueOf(nbreExpl.getString("nbreExemplaire")).intValue();
                if (nbre < nbreLivre) {
                    return false;
                }else{
                    //On fait la mise à jour dans la BD
                   stm.executeUpdate("UPDATE livre SET nbreExemplaire="+(nbre-nbreLivre));
                   //On enregistre dans l'historique
                   stm.executeUpdate("INSERT INTO historiqueemprunt VALUES(NULL,"
                           + AdaptToSqlString(ref)+","+ nbreLivre +","+ AdaptToSqlString(emailEmprunt)+",NOW(),NULL)");
                   return true;
                   
                }    
            }           
        }
        catch (Exception e) {
            return  false;
            
        }
    }
    
    public static void ReturnLoaning(String ref, int nbreLivre, String emailEmprunt){
        try {
                stm = conn.obtenirConnexion().createStatement();
                stm2 = conn.obtenirConnexion().createStatement();
                ResultSet nbreExpl = stm.executeQuery("SELECT nbreExemplaire FROM livre WHERE ref = "+AdaptToSqlString(ref));
                nbreExpl.next();
                int nbre = Integer.valueOf(nbreExpl.getString("nbreExemplaire")).intValue();
                //On fait la mise à jour dans la BD
                stm.executeUpdate("UPDATE livre SET nbreExemplaire="+(nbre+nbreLivre));
                //On enregistre dans l'historique
                stm2.executeUpdate("UPDATE historiqueemprunt SET dateRetour = NOW() WHERE emailEmprunt = "
                           + AdaptToSqlString(emailEmprunt)+" AND ref = "+ AdaptToSqlString(emailEmprunt));
                stm.close();
                stm2.close();
                                                       
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);     
        }
    }
    
    public static DefaultTableModel viewHistoriqueEmprunt(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ref");
        model.addColumn("Titre livre");
        model.addColumn("Genre livre");
        model.addColumn("Auteur livre");
        model.addColumn("Nom emprunteur");
        model.addColumn("Email emprunteur");
        model.addColumn("Nombre livre");
        model.addColumn("Date emprunt");
        model.addColumn("Date retour");

        try {
               
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            stm3 = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(" SELECT ref,nbreLivre,emailEmprunt,dateEmprunt,dateRetour FROM historiqueemprunt "); 
            //res.beforeFirst();
            while(res.next()){
                
                ResultSet resAdh = stm2.executeQuery(" SELECT nom,prenom FROM adherent WHERE email = " + AdaptToSqlString(res.getString("emailEmprunt"))); 
                ResultSet resAut = stm3.executeQuery(" SELECT titre,genre,nomAuteur,prenomAuteur FROM livre WHERE ref ="+ AdaptToSqlString(res.getString("ref"))); 
                
                model.addRow(new Object[]{res.getString("ref"),resAut.getString("titre"),
                        resAut.getString("genre"),resAut.getString("nomAuteur")+" "+resAut.getString("prenomAuteur"),
                        resAdh.getString("nom")+" "+resAdh.getString("prenom"),res.getString("emailEmprunt")
                ,res.getString("nbreLivre"),res.getString("dateEmprunt"),res.getString("dateRetour")});       
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
    
      public static void Save_Emprunt(String ref, String emailEmprunt){
            try {
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            stm3 = conn.obtenirConnexion().createStatement();
            
              ResultSet res = stm.executeQuery("SELECT nom,prenom FROM adherent "
                            + " WHERE email = "+AdaptToSqlString(emailEmprunt));
              ResultSet InfOeuvre = stm2.executeQuery("SELECT titre,nomAuteur,prenomAuteur FROM livre "
                            + " WHERE ref = "+AdaptToSqlString(ref));
              
              
                //On l'insère dans la BD
                 String requete="INSERT INTO demandereservation VALUES(NULL,"+AdaptToSqlString( res.getString("nom") + " "+  res.getString("prenom"))
                         +","+AdaptToSqlString(ref)+","+AdaptToSqlString( InfOeuvre.getString("titre"))+","+
                         AdaptToSqlString( InfOeuvre.getString("nomAuteur")+" "+InfOeuvre.getString("prenomAuteur"))+", NOW(),'N',NULL)"; 
                
                 stm3.executeUpdate(requete);
                JOptionPane.showMessageDialog(null,"Réservation effectuée avec succès","Demande de réservation",JOptionPane.INFORMATION_MESSAGE);
                
            
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
     }
     
    public static String AdaptToSqlString(String str)
    {
        str=str.replace("'", "''");
        return "'"+str+"'";
    }
    
}
