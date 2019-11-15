package Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DIAKITE SOUMAILA
 */
public interface ServicesAdmin extends Remote  {
    
    public void Ajouter_livre(String ref, String titre, String genre, String edition, String nomAuteur, String prenomAuteur) throws RemoteException;
    public DefaultTableModel Consultation_livres() throws RemoteException;
    public DefaultTableModel Consultation_livresPar(String type, String info) throws RemoteException;
    
    public DefaultTableModel Consultation_livreReservation() throws RemoteException;  
    public DefaultTableModel Consultation_livreReserves() throws RemoteException;  
    public void ValiderReservation(String ref) throws RemoteException; 
    
    public void Rajouter_livre (String ref, int nbre) throws RemoteException;
    public void Modifier_livre(String ref,String titre, String genre, String edition, String nomAuteur, String PrenomAuteur) throws RemoteException;
    public void Supprimer_livre(String ref) throws RemoteException;
    
    public void Ajouter_adherent(String email, String nom, String prenom, String ville, String contact, String mdp) throws RemoteException;
    public void Supprimer_adherent(String email) throws RemoteException;
    public DefaultTableModel Consultation_adherent(String type, String info) throws RemoteException;  
    public DefaultTableModel Consultation_adherentTs() throws RemoteException;
    
    public boolean Enregistrer_Emprunt(String ref, int nbreLivre, String emailEmprunt) throws RemoteException;
    public void Enregistrer_RetourEmprunt(String ref, int nbreLivre, String emailEmprunt) throws RemoteException;
    public DefaultTableModel HistoriqueLivreEmprunt() throws RemoteException;
    
    //Partie user
    public DefaultTableModel Consultation_livreAll() throws RemoteException;
    public DefaultTableModel Consultation_livresParUser(String type, String info) throws RemoteException;
    public boolean connectClient(String  email,String mdp) throws RemoteException;
    public void Enregistrer_EmpruntUser(String ref, String emailEmprunt) throws RemoteException;
}