/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

/**
 *
 * @author Admin
 */
public class Reponse {
    private String description;
    private boolean est_juste;
    
    // Constructeur de la réponse
    public Reponse(String description, boolean est_juste) {
        this.description = description;
        this.est_juste = est_juste;
    }
}
