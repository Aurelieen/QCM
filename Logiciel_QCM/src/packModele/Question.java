/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logiciel_qcm.BD;

/**
 *
 * @author Admin
 */
public class Question {
    private int id_qcm;
    private int id_question;
    private String description;
    private ArrayList<Reponse> reponses;
    
    // Constructeur de la question
    public Question(int id_question, int id_qcm, String description) {
        this.id_question = id_question;
        this.id_qcm = id_qcm;
        this.description = description;
        this.reponses = new ArrayList();
        
        construireReponses();
    }
    
    public Question(int id_qcm, String description) {
        this.id_qcm = id_qcm;
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
    
    public String getDescription() {
        return description;
    }
    
    public ArrayList<Reponse> getReponses() {
        return reponses;
    }
    
    private void construireReponses() {
        try {
            BD bd = new BD();
            ResultSet rsReponses;
            
            rsReponses = bd.SELECT("SELECT id_reponse, description_reponse, est_juste "
                                 + "FROM Reponse r INNER JOIN Question q ON r.id_question = q.id_question "
                                 + "WHERE q.id_qcm LIKE '" + this.id_qcm + "' AND q.id_question LIKE '" + this.id_question + "';");
            
            while (rsReponses.next()) {
                reponses.add(new Reponse(rsReponses.getInt(1), rsReponses.getString(2), rsReponses.getBoolean(3)));
            }
            
            bd.terminerRequete();
            bd.fermerBase();
        } catch (SQLException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
