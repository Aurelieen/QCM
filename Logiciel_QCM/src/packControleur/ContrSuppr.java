/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packControleur;

import java.util.ArrayList;
import packModele.Enseignant;
import packModele.Personne;

/**
 *
 * @author Admin
 */
public class ContrSuppr extends ControleurAbstrait {
    private Enseignant enseignant;
    
    public ContrSuppr(Personne personne) {
        super(personne);
    }

    @Override
    public Object control(ArrayList<String> donnees) {
        enseignant.supprimerQCM(Integer.parseInt(donnees.get(0)));
        enseignant.notifyObservateurs("actualisationEns");
        return null;
    }
    
    public void donnerEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
}
