/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packControleur;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import packModele.Enseignant;
import packModele.Etudiant;
import packModele.Personne;
import packVue.Fenetre;
import packVue.Observateur;

/**
 *
 * @author Admin
 */
public class ContrAuth extends ControleurAbstrait {
    public ContrAuth(Personne personne) {
        super(personne);
    }
    
    @Override
    public Object control(ArrayList<String> donnees) {
        /*
            Format des données du contrôleur d'authentification :
                - Tableau de chaînes de caractères ;
                    - donnees.get(0) est l'identifiant préfixé
                    - donnees.get(1) est le mot de passe
        */
        
        if (donnees.get(0).startsWith("etu")) {
            try {
                personne = new Etudiant();
                personne.connecter(donnees.get(0), donnees.get(1), false);
                
                return personne;
            } catch (SQLException ex) { Logger.getLogger(ContrAuth.class.getName()).log(Level.SEVERE, null, ex); }
            
        } else if (donnees.get(0).startsWith("ens")) {
            try {
                personne = new Enseignant();
                personne.connecter(donnees.get(0), donnees.get(1), true);
                
                return personne;
            } catch (SQLException ex) { Logger.getLogger(ContrAuth.class.getName()).log(Level.SEVERE, null, ex); }
            
        } else {
            // On crée un faux utilisateur pour informer Fenêtre que VueAuth a mal été renseignée
            personne = new Etudiant();      
            return personne;
        }
        
        return null;
    }
}
