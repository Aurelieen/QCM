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
public class ContrAjout extends ControleurAbstrait {
    public ContrAjout(Personne personne) {
        super(personne);
    }
    
    @Override
    public Object control(ArrayList<String> donnees) {
        return null;
    }    
}
