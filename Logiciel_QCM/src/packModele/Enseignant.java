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
public class Enseignant extends Personne {
    // Attributs de la classe Enseignant
    private int id_enseignant;
    private ArrayList<String> classes;
    private ArrayList<QCM> questionnaires;      // QCM que l'enseignant a créés.
}
