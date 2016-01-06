/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.sql.ResultSet;
import java.sql.SQLException;
import logiciel_qcm.BD;

/**
 *
 * @author Admin
 */
public class Personne {
    protected String nom;
    protected String prenom;
    
    // Constructeur de la personne
    public Personne() {
        this.nom = null;
        this.prenom = null;
    }
    
    // Méthodes de la personne
    /*
        La fonction connecter() de Personne n'affecte que le nom et le prénom.
        On surcharge cette méthode dans les classes filles pour affecter les classes, etc.
    */
    public boolean connecter(String id, String mot_de_passe, boolean ens) throws SQLException {
        BD bd = new BD();
        ResultSet authentification;
        int id_personne = -1;
        
        // Vérification de l'existence du compte
        if (ens) {
            authentification = bd.SELECT("SELECT id_enseignant "
                                       + "FROM Enseignant e "
                                       + "WHERE e.id_enseignant LIKE '" + id + "' AND e.password_enseignant LIKE '" + mot_de_passe + "'");
        
            while (authentification.next()) {
                id_personne = authentification.getInt(1);
            }
        } else {
            authentification = bd.SELECT("SELECT id_etudiant "
                                       + "FROM Etudiant e "
                                       + "WHERE e.id_etudiant LIKE '" + id + "' AND e.password_etudiant LIKE '" + mot_de_passe + "'");
            
            while (authentification.next()) {
                id_personne = authentification.getInt(1);
            }
        }
        
        bd.terminerRequete();

        // Récupération des informations de compte
        if (id_personne != -1) {
            ResultSet informations;            
            String membre = (ens) ? "enseignant" : "etudiant";
            String Membre = (ens) ? "Enseignant" : "Etudiant";
            
            informations = bd.SELECT("SELECT nom_" + membre + ", prenom_" + membre + " "
                                   + "FROM " + Membre + " e "
                                   + "WHERE e.id_" + membre + " = '" + id_personne + "'");
            
            while (informations.next()) {
                nom = informations.getString(1);
                prenom = informations.getString(2);
                System.out.println(nom);
                return true;
            }
            
            bd.terminerRequete();
        }
        
        bd.fermerBase();
        return false;
    }
}
