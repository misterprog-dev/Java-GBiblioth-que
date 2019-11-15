/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import Controller.ImplServiceAdmin;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author HP
 */
public class SERVEUR {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
           
            //On démarre l'annuaire
            LocateRegistry.createRegistry(1099);
            //On crée l'objet distant
            ImplServiceAdmin serv = new ImplServiceAdmin();
            //Afficher le références
            System.out.println(serv.toString());
            
            try {
                //Publier les références dans l'annuaire
                Naming.rebind("rmi://localhost:1099/80", serv);  
                System.out.println("\n\nLE SERVEUR EST LANCE !!!!!!!!!!");
             } catch (Exception e) {
                 System.out.println("Impossible de me connecter au serveur ....");
             }        
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
