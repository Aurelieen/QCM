/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import logiciel_qcm.BD;

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
        this.id_etudiant = 0;
        this.classe = null;
        this.questionnaires = new ArrayList<>();
    }
    
    // Méthodes héritées de la personne
    /*
        La surcharge permet de définir sa classe
    */
    @Override
    public boolean connecter(String login, String mot_de_passe, boolean ens) throws SQLException {
        if (super.connecter(login, mot_de_passe, ens)) {
            BD bd = new BD();
            
            // 1. On assigne à l'étudiant son identifiant unique
            id_etudiant = Integer.parseInt(login.substring(3));
            
            // 2. On connecte l'étudiant à sa classe via une requête
            ResultSet rsClasse;
            rsClasse = bd.SELECT("SELECT c.nom_classe " +
                                 "FROM Classe c INNER JOIN Etudiant e ON e.id_classe = c.id_classe " +
                                 "WHERE e.id_etudiant LIKE '" + id_etudiant + "';");
            
            while (rsClasse.next()) {
                classe = rsClasse.getString(1);
            }
            
            bd.terminerRequete();
            bd.fermerBase();
            
            // 3. On récupère les questionnaires qui lui sont liés
            questionnaires = recupererQCM();
            
            return true;
        }
        
        return false;
    }
    
    // Méthodes de l'étudiant
    public float repondreQCM(QCM qcm, ArrayList<Boolean> reponses) {
        int i = 0;
        float note = 0;
        
        for (Question question : qcm.getQuestions()) {
            if (qcm.getConformiteRelative()) {
                System.out.println("Pas encore géré —");
            } else {
                boolean question_juste = true;
                
                for (Reponse reponse : question.getReponses()) {
                    if (reponse.est_juste() != reponses.get(i))
                        question_juste = false;
                    
                    i++;
                }
                
                if (question_juste)
                    note++;
            }  
        }
        
        note = (note * 20) / (float) qcm.getQuestions().size();
        
        // Écriture de la note dans la base de données
        try {
            BD bd = new BD();
            bd.ecrire("INSERT INTO Repond_a VALUES (" + qcm.getId() + ", " + this.id_etudiant + ", " + note + ");");
            
            bd.terminerRequete();
            bd.fermerBase();
        } catch (SQLException ex) {
            Logger.getLogger(Etudiant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return note;
    }
    
    public ArrayList<QCM> recupererQCM() {
        BD bd = new BD();
        ResultSet rsQCM;
        ArrayList<QCM> tmp = new ArrayList<>();
        
        try {
            rsQCM = bd.SELECT("SELECT q.id_qcm, q.nom_qcm, q.description_qcm, c.nom_classe, q.date_debut, q.date_fin, q.conformite_relative " +
                    "FROM QCM q INNER JOIN Classe c ON q.id_classe = c.id_classe INNER JOIN Etudiant e ON e.id_classe = c.id_classe " +
                    "WHERE e.id_etudiant = '" + id_etudiant + "';");
            
            while (rsQCM.next()) {
                int     tmp_id_qcm          = rsQCM.getInt(1);
                String  tmp_nom_qcm         = rsQCM.getString(2);
                String  tmp_description_qcm = rsQCM.getString(3);
                String  tmp_nom_classe      = rsQCM.getString(4);
                Date    tmp_date_debut      = new Date((long) rsQCM.getTimestamp(5).getTime() * 1000);
                Date    tmp_date_fin        = new Date((long) rsQCM.getTimestamp(6).getTime() * 1000);
                boolean tmp_conformite      = rsQCM.getBoolean(7);
                
                tmp.add(new QCM(tmp_id_qcm, tmp_nom_qcm, tmp_description_qcm, tmp_nom_classe, 
                                tmp_date_debut, tmp_date_fin, tmp_conformite));
            }
            
            bd.terminerRequete();
            bd.fermerBase();
        } catch (SQLException ex) {
            Logger.getLogger(Etudiant.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tmp;
    }
    
    @Override
    public String toString() {
        return "Etudiant";
    }
    
    public String getNote(QCM qcm) {
        String note = "Non noté.";
        
        try {
            BD bd = new BD();
            ResultSet rsNote;
            rsNote = bd.SELECT("SELECT ra.note " +
                               "FROM Repond_a ra " +
                               "WHERE ra.id_qcm = '" + qcm.getId() + "' AND ra.id_etudiant = '" + this.id_etudiant + "';");
            
            while (rsNote.next()) {
                note = Float.toString(rsNote.getFloat(1));
            }
            
            rsNote = null;
            bd.terminerRequete();
            bd.fermerBase();
        } catch (SQLException ex) {
            Logger.getLogger(Etudiant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return note;
    }
    
    public float getMoyenne() {
        float moyenne = 0;
        int nbQuestionnaires = 0;
        
        for (QCM qcm : questionnaires) {
            String note = getNote(qcm);
            if (!"Non noté.".equals(note)) {
                moyenne += Float.parseFloat(note);
                nbQuestionnaires++;
            }
        }
        
        if (nbQuestionnaires > 0) {
            return (moyenne /= (float) nbQuestionnaires);
        } else return (float) 0;
    }
    
    public ArrayList<QCM> getQCM() {
        return questionnaires;
    }
}
