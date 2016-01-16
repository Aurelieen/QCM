/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packControleur;

import java.util.ArrayList;
import packModele.Etudiant;
import packModele.Personne;
import packModele.QCM;

/**
 *
 * @author Admin
 */
public class ContrReponse extends ControleurAbstrait {
    private Etudiant etudiant;
    
    public ContrReponse(Personne personne) {
        super(personne);
    }
    
    @Override
    public Object control(ArrayList<String> donnees) {
        /*
            - donnees.get(0) est l'identifiant du QCM.
            - Les éléments suivants sont les réponses aux questions dans l'ordre
        */
        
        ArrayList<Boolean> reponses = new ArrayList<>();
        int id = Integer.parseInt(donnees.get(0));
        
        for (QCM qcm : etudiant.getQCM()) {
            if (qcm.getId() == id) {
                for (int i = 1; i < donnees.size(); i++) {
                    reponses.add(Boolean.parseBoolean(donnees.get(i)));
                }
                
                return etudiant.repondreQCM(qcm, reponses);
            }
        }
        
        return null;
    }
    
    public void donnerEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
