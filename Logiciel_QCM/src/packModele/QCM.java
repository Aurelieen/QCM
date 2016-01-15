/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import logiciel_qcm.BD;

/**
 *
 * @author Admin
 */
public class QCM {
    private final int id_qcm;
    private final String nom;
    private final String description;
    private final String classe;
    private final Date date_debut;
    private final Date date_fin;
    private final boolean conformite_relative;
    
    private ArrayList<Question> questions;
    
    // Constructeur du QCM
    public QCM(int id_qcm, String nom,
               String description, String classe,
               Date date_debut, Date date_fin, boolean conformite_relative) {
        this.id_qcm = id_qcm;
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
    
    // Accesseurs et mutateurs
    public String getNom() {
        return nom;
    }
    
    public int getId() {
        return id_qcm;
    }
    
    public String getClasse() {
        return classe;
    }
    
    public String getDateFin() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date_fin);
    }
    
    public String getEtat() {
        switch (estActuel()) {
            case -1:
                return "— Passé";
            case 0:
                return "En cours";
            case 1:
                return "Planifié";
        }
        
        return "{Erreur}";
    }
    
    @Override
    public String toString() {
        return nom;
    }
    
    public ArrayList<ArrayList<String>> recupererNotes() {
        ArrayList<ArrayList<String>> notes = new ArrayList<>();
        BD bd = new BD();
        ResultSet rsNotes;
        
        try {
            rsNotes = bd.SELECT("SELECT e.prenom_etudiant, e.nom_etudiant, ra.note " +
                                "FROM Etudiant e INNER JOIN Repond_a ra ON e.id_etudiant = ra.id_etudiant " +
                                "WHERE ra.id_qcm = '" + this.id_qcm + "';");
            
            while (rsNotes.next()) {
                ArrayList<String> releve = new ArrayList<>();
                releve.add(rsNotes.getString(1));
                releve.add(rsNotes.getString(2));
                releve.add(rsNotes.getString(3));
                
                notes.add(releve);
            }
            
            bd.terminerRequete();
            bd.fermerBase();
        } catch (SQLException ex) {
            Logger.getLogger(QCM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return notes;
    }
}
