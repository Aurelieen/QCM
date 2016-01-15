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
import logiciel_qcm.BD;

/**
 *
 * @author Admin
 */
public class Enseignant extends Personne {
    // Attributs de la classe Enseignant
    private int id_enseignant;
    public ArrayList<String> classes;
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
    public boolean creerQCM(String nom, String description,
                            String classe, Date date_debut,
                            Date date_fin, boolean conformite_relative) {
        return true;
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
    
    public void supprimerQCM() {
        ;
    }
    
    @Override
    public String toString() {
        return "Enseignant";
    }
    
    public ArrayList<QCM> getQCM() {
        return questionnaires;
    }
}
