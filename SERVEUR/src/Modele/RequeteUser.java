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
public class RequeteUser {
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
 
    public RequeteUser(){
        
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
                JOptionPane.showMessageDialog(null,"Adhérent enregistré avec succès","Ajout d'adhérent",JOptionPane.INFORMATION_MESSAGE);
                
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public static boolean connectClient(String email, String mdp){
             Boolean trouve = false;
            try {
                stm = conn.obtenirConnexion().createStatement();
                ResultSet res = stm.executeQuery("SELECT email,mdp FROM adherent "
                            + " WHERE email = "+AdaptToSqlString(email)+" AND mdp="+AdaptToSqlString(mdp));
                while(res.next()){
                    trouve = true;
                }
            }
            catch (Exception e) {
                 JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","A propos",JOptionPane.INFORMATION_MESSAGE);
            }
        return trouve;
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
              
              res.next();
              InfOeuvre.next();
                //On l'insère dans la BD
                 String requete="INSERT INTO demandereservation VALUES(NULL,"+AdaptToSqlString( res.getString("nom") + " "+  res.getString("prenom"))
                         +","+AdaptToSqlString(ref)+","+AdaptToSqlString( InfOeuvre.getString("titre"))+","+
                         AdaptToSqlString( InfOeuvre.getString("nomAuteur")+" "+InfOeuvre.getString("prenomAuteur"))+", NOW(),'N',NULL)"; 
                
                 stm3.executeUpdate(requete);
                
                
            
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
