/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Enseignant extends Personne {
    // Attributs de la classe Enseignant
    private int id_enseignant;
    private ArrayList<String> classes;
    private ArrayList<QCM> questionnaires;      // QCM que l'enseignant a créés.
    
    // Constructeur de l'enseignant
    public Enseignant() {
        super();
    }
    
    // Méthodes de l'enseignant
    public boolean creerQCM(String nom, String description,
                            String classe, Date date_debut,
                            Date date_fin, boolean conformite_relative) {
        return true;
    }
    
    public ArrayList<String> recupererQCM() {
        return null;
    }
    
    public void supprimerQCM() {
        ;
    }
}
