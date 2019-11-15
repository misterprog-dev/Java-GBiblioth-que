package Controller;

import Modele.Connecter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.table.DefaultTableModel;
import Modele.RequeteAdmin;
import Modele.RequeteUser;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ImplServiceAdmin extends UnicastRemoteObject implements ServicesAdmin{
    
    Connecter conn = new Connecter();
    Statement stm, stm2, stm3;
    ResultSet Rs;
    
    public ImplServiceAdmin() throws RemoteException{
        super(); 
    }

    @Override
    public void Ajouter_livre(String ref, String titre, String genre, String edition, String nomAuteur, String prenomAuteur) throws RemoteException {
        RequeteAdmin.addBook(ref, titre, genre, edition, nomAuteur, prenomAuteur);
        
//        Boolean trouve = false;
//        try {
//            stm = conn.obtenirConnexion().createStatement();
//            ResultSet res = stm.executeQuery("SELECT ref FROM livre WHERE ref = "+AdaptToSqlString(ref));
//            while(res.next()){
//                trouve = true;
//            }
//            if (trouve) {
//                JOptionPane.showMessageDialog(null,"Ce livre existe déja, vous pouvez augmenter le nombre","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
//                
//            } else {
//                //On l'insère dans la BD
//                 String requete="INSERT INTO livre VALUES("+AdaptToSqlString(ref)+","+AdaptToSqlString(titre)+","+AdaptToSqlString(genre)+","+AdaptToSqlString(edition)
//                         +","+AdaptToSqlString(nomAuteur)+","+AdaptToSqlString(prenomAuteur)+", NOW(), 0)"; 
//                stm.executeUpdate(requete);
//                JOptionPane.showMessageDialog(null,"Livre enregistré avec succès","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
//                
//            }
//        }
//        catch (Exception e) {
//            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
//            
//        }
    }

    @Override
    public DefaultTableModel Consultation_livres() throws RemoteException {
        return RequeteAdmin.viewBooks();
    }
    
    @Override
    public DefaultTableModel Consultation_livresPar(String type, String info) throws RemoteException {
        return RequeteAdmin.viewBooksBy(type, info);
    }

    @Override
    public DefaultTableModel Consultation_livreReservation() throws RemoteException {
        return RequeteAdmin.viewBooksToReserve();
    }
    
    @Override
    public DefaultTableModel Consultation_livreReserves() throws RemoteException {
        return RequeteAdmin.viewBooksReserved();
    }

    @Override
    public void Modifier_livre(String ref,String titre, String genre, String edition, String nomAuteur, String PrenomAuteur) throws RemoteException {
        RequeteAdmin.updateBook(ref, titre, genre, edition, nomAuteur, PrenomAuteur);
        
//         try {
//                //On fait la mise à jour dans la BD
//                 String requete="UPDATE livre"
//                         + " SET titre="+AdaptToSqlString(titre)+", genre = "+AdaptToSqlString(genre)+
//                         ", edition="+AdaptToSqlString(edition)+", nomAuteur = "+AdaptToSqlString(nomAuteur)
//                         +", prenomAuteur="+AdaptToSqlString(PrenomAuteur)+
//                         " WHERE ref = "+AdaptToSqlString(ref)+" ;"; 
//                
//                stm = conn.obtenirConnexion().createStatement();
//                stm.executeUpdate(requete);
//                
//                JOptionPane.showMessageDialog(null,"Livre modifié avec succès","Modification de livre",JOptionPane.INFORMATION_MESSAGE);
//                    
//        }
//        catch (Exception e) {
//            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
//            
//        }
    }

    @Override
    public void Supprimer_livre(String ref) throws RemoteException {
        RequeteAdmin.deleteBook(ref);
        
//        try {
//                //On fait la suppression dans la BD
//                String requete="DELETE FROM livre WHERE ref = "+AdaptToSqlString(ref)+" ;"; 
//                stm = conn.obtenirConnexion().createStatement();
//                stm.executeUpdate(requete);
//                JOptionPane.showMessageDialog(null,"Livre supprimé avec succès","Suppression de livre",JOptionPane.INFORMATION_MESSAGE);            
//        }
//        catch (Exception e) {
//            JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","Ajout de livre",JOptionPane.INFORMATION_MESSAGE);
//            
//        }
    }

    @Override
    public void Ajouter_adherent(String email, String nom, String prenom, String ville, String contact, String mdp) throws RemoteException {
        RequeteAdmin.addClient(email, nom, prenom, ville, contact, mdp);
    }

    @Override
    public void Supprimer_adherent(String email) throws RemoteException {
        RequeteAdmin.deleteClient(email);
    }

    @Override
    public DefaultTableModel Consultation_adherent(String type, String info) throws RemoteException {
       return RequeteAdmin.viewadherent(type, info);
    }
    
    @Override
    public DefaultTableModel Consultation_adherentTs() throws RemoteException {
        return RequeteAdmin.viewadherentAll();
    }
    
    public boolean Enregistrer_Emprunt(String ref, int nbreLivre, String emailEmprunt) throws RemoteException {
        return RequeteAdmin.addLoaning(ref, nbreLivre, emailEmprunt);
    }

    @Override
    public void Enregistrer_RetourEmprunt(String ref, int nbreLivre, String emailEmprunt) throws RemoteException {
        RequeteAdmin.ReturnLoaning(ref, nbreLivre, emailEmprunt);
    }

    @Override
    public DefaultTableModel HistoriqueLivreEmprunt() throws RemoteException {
        return RequeteAdmin.viewHistoriqueEmprunt();
    }
@Override
    public DefaultTableModel Consultation_livreAll() throws RemoteException {
        return RequeteUser.viewBooks();
    }

    @Override
    public DefaultTableModel Consultation_livresParUser(String type, String info) throws RemoteException {
        return RequeteUser.viewBooksBy(type, info);
    }

    @Override
    public boolean connectClient(String email, String mdp) throws RemoteException {
        return RequeteUser.connectClient(email, mdp);
    }

    @Override
    public void Enregistrer_EmpruntUser(String ref, String emailEmprunt) throws RemoteException {
        RequeteUser.Save_Emprunt(ref, emailEmprunt);
    }
    
    
    public String AdaptToSqlString(String str)
    {
        str=str.replace("'", "''");
        return "'"+str+"'";
    }

    @Override
    public void Rajouter_livre(String ref, int nbre) throws RemoteException {
        RequeteAdmin.AddNumberBook(ref, nbre);
    }

    @Override
    public void ValiderReservation(String ref) throws RemoteException {
        RequeteAdmin.saveReservation(ref);
    }
}