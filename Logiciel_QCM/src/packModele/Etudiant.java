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
public class Etudiant extends Personne {
    // Attributs de la classe Etudiant
    private int id_etudiant;
    private String classe;
    private ArrayList<QCM> questionnaires;      // Garder les QCM auxquels l'étudiant a répondu, répond ou doit répondre.
}
