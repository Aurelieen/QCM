/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class QCM {
    private int id_qcm;
    private String nom;
    private String description;
    private String classe;
    private Date date_debut;
    private Date date_fin;
    private boolean conformite_relative;
    
    private ArrayList<Question> questions;
    
    // Constructeur du QCM
    public QCM(int id_qcm, String nom,
               String description, String classe,
               Date date_debut, Date date_fin, boolean conformite_relative) {
        this.nom = nom;
        this.description = description;
        this.classe = classe;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.conformite_relative = conformite_relative;
        
        this.questions = new ArrayList();
    }
    
    // Méthodes du QCM
    public boolean ajoutQuestion(String description) {
        if (!description.isEmpty()) {
            questions.add(new Question(description));
            return true;
        } else {
            return false;
        }
    }
    
    public boolean estValide() {
        return true;
    }
    
    /*
        Fonctionnement de la méthode estActuel() :
            - Renvoie -1 si la date de fin du QCM est passée (ancien QCM) ;
            - Renvoie 0 si le QCM est en cours de programmation ;
            - Renvoie 1 si la date de début du QCM n'est pas encore passée (QCM planifié) ;
    */
    public int estActuel() {
        Date date_actuelle = new Date();
        
        // Comparaison des dates
        if (date_fin.before(date_actuelle)) {
            return -1;
        } else if (date_fin.after(date_actuelle) && date_debut.before(date_actuelle)) {
            return 0;
        } else {
            return 1;
        }
    }
}
