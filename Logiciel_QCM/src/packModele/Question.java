/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Question {
    private String description;
    private ArrayList<Reponse> reponses;
    
    // Constructeur de la question
    public Question(String description) {
        this.description = description;
        this.reponses = new ArrayList();
    }
    
    // Méthodes de la question
    public boolean ajoutReponse(String description, boolean est_juste) {
        if (!description.isEmpty()) {
            reponses.add(new Reponse(description, est_juste));
            return true;
        } else {
            return false;
        }
    }
}
