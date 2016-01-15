/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiciel_qcm;

import java.sql.SQLException;
import packControleur.ContrAjout;
import packControleur.ContrAuth;
import packControleur.ContrReponse;
import packControleur.ContrSuppr;
import packControleur.ControleurAbstrait;
import packModele.Enseignant;
import packModele.Etudiant;
import packModele.Personne;
import packVue.Fenetre;
import packVue.VueReponse;

/**
 *
 * @author Admin
 */
public class Logiciel_QCM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        /* Personne persTest = new Etudiant();
        System.out.println(persTest.toString()); */
        
        // Création de la personne
        Personne p = null;
        
        // Création des contrôleurs
        ControleurAbstrait contrAuth = new ContrAuth(p);
        ControleurAbstrait contrReponse = new ContrReponse(p);
        ControleurAbstrait contrSuppr = new ContrSuppr(p);
        ControleurAbstrait contrAjout = new ContrAjout(p);
        
        // Création de la fenêtre
        Fenetre f = new Fenetre(p, contrAuth, contrAjout, contrReponse, contrSuppr);
    }
    
}
