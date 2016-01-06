/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packControleur;

import java.util.ArrayList;
import packModele.Personne;

/**
 *
 * @author Admin
 */
public abstract class ControleurAbstrait {
    protected Personne personne;                    // Accès au modèle depuis le contrôleur.
    
    // Constructeur et affectation de personne
    public ControleurAbstrait(Personne personne) {
        this.personne = personne;
    }
    
    // Méthode de contrôle abstraite, à spécifier dans les classes filles
    public abstract void control(ArrayList<String> donnees);
}
