/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packControleur;

import java.util.ArrayList;
import packModele.Enseignant;
import packModele.Etudiant;
import packModele.Personne;

/**
 *
 * @author Admin
 */
public class ContrAuth extends ControleurAbstrait {
    public ContrAuth(Personne personne) {
        super(personne);
    }
    
    @Override
    public void control(ArrayList<String> donnees) {
        /*
            Format des données du contrôleur d'authentification :
                - Tableau de chaînes de caractères ;
                    - donnees.get(0) est l'identifiant préfixé
                    - donnees.get(1) est le mot de passe
        */
        
        if (donnees.get(0).startsWith("etu")) {
            personne = new Etudiant();
            
        } else if (donnees.get(0).startsWith("ens")) {
            personne = new Enseignant();
        } else {
            ;       // Identifiant correct
        }
    }
}
