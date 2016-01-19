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
public class Enseignant extends Personne {
    // Attributs de la classe Enseignant
    private int id_enseignant;
    private ArrayList<String> classes;
    private ArrayList<QCM> questionnaires;      // QCM que l'enseignant a créés.
    
    // Constructeur de l'enseignant
    public Enseignant() {
        super();
        this.id_enseignant = 0;
        this.classes = new ArrayList<>();
        this.questionnaires = new ArrayList<>();
    }
    
    // Méthodes héritées de la personne
    /*
        La surcharge permet de définir ses classes
    */
    @Override
    public boolean connecter(String login, String mot_de_passe, boolean ens) throws SQLException {
        // Si l'authentification a fonctionné sur les logins
        if (super.connecter(login, mot_de_passe, ens)) {
            BD bd = new BD();
            
            // 1. On assigne à l'enseignant son identifiant unique
            id_enseignant = Integer.parseInt(login.substring(3));
            
            // 2. On connecte l'enseignant à ses classes via une requête
            ResultSet rsClasses;
            rsClasses = bd.SELECT("SELECT c.nom_classe "
                                + "FROM Classe c INNER JOIN Enseigne_dans ed ON c.id_classe = ed.id_classe "
                                + "WHERE ed.id_enseignant LIKE '" + id_enseignant + "'");
            
            while (rsClasses.next()) {
                classes.add(rsClasses.getString(1));
            }
            
            bd.terminerRequete();
            bd.fermerBase();
            
            // 3. On récupère les QCM de l'enseignant (passés, en cours, à venir)
            questionnaires = recupererQCM();
            
            return true;
        }
        
        return false;
    }
    
    // Méthodes de l'enseignant
    public QCM creerQCM(String nom, String description,
                            String classe, Date date_debut,
                            Date date_fin, boolean conformite_relative) {    
        
        description = (description == null) ? "Aucune." : description;
        return new QCM(nom, description, classe, date_debut, date_fin, conformite_relative);
    }
    
    public void validerQCM(QCM qcm) {
        System.out.println("Nombre de QCM avant création : " + this.getQCM().size());
        
        try {
            int classeId = qcm.getClasseId(this.id_enseignant);
            BD bd = new BD();
            
            // Insertion du QCM dans la base
            bd.ecrire("INSERT INTO QCM(id_enseignant, id_classe, nom_qcm, description_qcm, date_debut, date_fin, conformite_relative) "
                            + "VALUES (" + this.id_enseignant + ", " + classeId
                            + ", '" + qcm.getNom().replaceAll("'", "''") + "', '" + qcm.getConsignes().replaceAll("'", "''")
                            + "', " + (qcm.getDateDebut_D().getTime() / 1000) + ", " + (qcm.getDateFin_D().getTime() / 1000)
                            + ", " + ((qcm.getConformiteRelative()) ? 1 : 0) + ");");
            
            bd.terminerRequete();
            
            // Récupération de l'identifiant du QCM inséré
            ResultSet rsLast;
            rsLast = bd.SELECT("SELECT MAX(id_qcm) FROM QCM;");
            while (rsLast.next())
                qcm.setId(rsLast.getInt(1));
            
            bd.terminerRequete();
            
            // Insertion des questions dans la base de données
            for (Question question : qcm.getQuestions()) {
                bd.ecrire("INSERT INTO Question(id_qcm, description_question) "
                                     + "VALUES (" + qcm.getId() + ", '" + question.getDescription().replaceAll("'", "''") + "');");
                
                bd.terminerRequete();
                
                // Récupération de l'identifiant de la question
                ResultSet rsQuestion;
                rsQuestion = bd.SELECT("SELECT MAX(id_question) FROM Question;");
                while (rsQuestion.next())
                    question.setId(rsQuestion.getInt(1));
                
                bd.terminerRequete();
                
                // Insertion des réponses dans la base de données
                for (Reponse reponse : question.getReponses()) {
                    bd.ecrire("INSERT INTO Reponse(id_question, description_reponse, est_juste) "
                                        + "VALUES (" + question.getId() + ", '" + reponse.getDescription().replaceAll("'", "''") + "', " + ((reponse.est_juste()) ? 1 : 0) + ");");
                    
                    bd.terminerRequete();
                }
            }
            
            bd.fermerBase();
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<QCM> recupererQCM() throws SQLException {
        BD bd = new BD();
        ResultSet rsQCM;
        ArrayList<QCM> tmp = new ArrayList<>();
        
        rsQCM = bd.SELECT("SELECT q.id_qcm, q.nom_qcm, q.description_qcm, c.nom_classe, q.date_debut, q.date_fin, q.conformite_relative "
                        + "FROM QCM q INNER JOIN Classe c ON q.id_classe = c.id_classe "
                        + "WHERE q.id_enseignant LIKE '" + id_enseignant + "'");
        
        while(rsQCM.next()) {
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
        return tmp;
    }
    
    public void supprimerQCM(int id_qcm) {
        try {
            BD bd = new BD();
            
            // Suppression des réponses des questions associées au QCM
            System.out.println("DELETE FROM Reponse "
                    + "WHERE id_question IN (SELECT q.id_question "
                                          + "FROM Question q "
                                          + "WHERE q.id_qcm LIKE '" + id_qcm + "');");
            bd.ecrire("DELETE FROM Reponse "
                    + "WHERE id_question IN (SELECT q.id_question "
                                          + "FROM Question q "
                                          + "WHERE q.id_qcm LIKE '" + id_qcm + "');");
            bd.terminerRequete();
            
            // Suppression des questions qui sont associées au QCM
            bd.ecrire("DELETE FROM Question "
                    + "WHERE id_qcm LIKE '" + id_qcm + "';");
            bd.terminerRequete();
            
            // Suppression des liens Élève – QCM
            bd.ecrire("DELETE FROM Repond_a "
                    + "WHERE id_qcm LIKE '" + id_qcm + "';");
            bd.terminerRequete();
            
            // Suppression du QCM en lui-même
            bd.ecrire("DELETE FROM QCM "
                    + "WHERE id_qcm LIKE '" + id_qcm + "';");
            bd.terminerRequete();
            
            bd.fermerBase();
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() {
        return "Enseignant";
    }
    
    public ArrayList<QCM> getQCM() {
        return questionnaires;
    }
    
    public void setQCM(ArrayList<QCM> qcm) {
        this.questionnaires = qcm;
    }
    
    public ArrayList<String> getClasses() {
        return classes;
    }
}
