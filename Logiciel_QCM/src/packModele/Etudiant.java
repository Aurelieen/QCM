/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Etudiant extends Personne {
    // Attributs de la classe Etudiant
    private int id_etudiant;
    private String classe;
    private ArrayList<QCM> questionnaires;      // Garder les QCM auxquels l'étudiant a répondu, répond ou doit répondre.
    
    // Constructeur de l'étudiant
    public Etudiant() {
        super();
    }
    
    // Méthodes héritées de la personne
    /*
        La surcharge permet de définir sa classe
    */
    @Override
    public boolean connecter(String login, String mot_de_passe, boolean ens) throws SQLException {
        return super.connecter(login, mot_de_passe, ens);
    }
    
    // Méthodes de l'étudiant
    public float repondreQCM() {
        return (float) 0.0;
    }
    
    public ArrayList<QCM> recupererQCM() {
        return null;
    }
}
