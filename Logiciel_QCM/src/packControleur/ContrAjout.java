/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packControleur;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import packModele.Enseignant;
import packModele.Etudiant;
import packModele.Personne;
import packModele.QCM;
import packModele.Question;
import packModele.Reponse;

/**
 *
 * @author Admin
 */
public class ContrAjout extends ControleurAbstrait {
    private Enseignant enseignant;
    
    public ContrAjout(Personne personne) {
        super(personne);
    }
    
    @Override
    public Object control(ArrayList<String> donnees) {
        // Création du nouveau QCM à travers l'étudiant
        String nomQCM = (donnees.get(0) == null) ? "{ À supprimer }" : donnees.get(0);
        String descriptionQCM = donnees.get(1);
        String classeQCM = donnees.get(2);
        Date date_debut = new Date(Long.parseLong(donnees.get(3)));
        Date date_fin = new Date(Long.parseLong(donnees.get(4)));
        boolean conformite_relative = Boolean.parseBoolean(donnees.get(5));
        
        QCM qcm = enseignant.creerQCM(nomQCM, descriptionQCM, classeQCM, date_debut, date_fin, conformite_relative);
        System.out.println(nomQCM + " " + descriptionQCM + " " + classeQCM + " " + date_debut + " " + date_fin + " " + conformite_relative);
        
        // Création des questions et des réponses
        int i = 6;
        while (i < donnees.size()) {            
            int nb_reponses = Integer.parseInt(donnees.get(i).substring(1, (int) (1 + Integer.parseInt(donnees.get(i).substring(0, 1)))));
            qcm.ajoutQuestion(donnees.get(i).substring((int) (Math.log(nb_reponses)/Math.log(10)) + 1));
            
            for (int j = 0; j < nb_reponses; j++) {                
                i++;
                
                if (donnees.get(i).startsWith("true"))
                    qcm.getQuestions().get(qcm.getQuestions().size() - 1).ajoutReponse(donnees.get(i).substring(4), true);
                else
                    qcm.getQuestions().get(qcm.getQuestions().size() - 1).ajoutReponse(donnees.get(i).substring(5), false);
            }
            
            i++;
        }
        
        for (Question question : qcm.getQuestions()) {
            System.out.println(question.getDescription());
            for (Reponse reponse : question.getReponses()) {
                System.out.println("  " + reponse.getDescription() + " " + reponse.est_juste());
            }
            System.out.println("");
        }
        
        enseignant.validerQCM(qcm);
        try {
            enseignant.setQCM(enseignant.recupererQCM());
        } catch (SQLException ex) {
            Logger.getLogger(ContrAjout.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void donnerEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
}
