/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packVue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import packControleur.ContrAjout;
import packControleur.ContrAuth;
import packControleur.ContrReponse;
import packControleur.ContrSuppr;
import packControleur.ControleurAbstrait;
import packModele.Enseignant;
import packModele.Etudiant;
import packModele.Personne;
import packModele.QCM;

/**
 *
 * @author Admin
 */
public class Fenetre extends JFrame implements Observateur {
    /*  ATTRIBUTS DE LA FENÊTRE. On distingue ses panneaux internes et les
        contrôleurs qu'elle reçoit (lien d'association) pour les appeler en
        cas d'appel à un événement (via un écouteur interne). */
    
    // Référence à l'utilisateur
    private Personne            personne;
    
    // Panneaux de la fenêtre (avec les contrôleurs adéquats)
    private VueAuth             vueAuth;
    private VueCreation         vueCreation;
    private VueEnseignant       vueEnseignant;
    private VueEtudiant         vueEtudiant;
    private VueReponse          vueReponse;
    
    // Contrôleurs distribués par la fenêtre aux panneaux (références)
    private ControleurAbstrait  contrAuth;
    private ControleurAbstrait  contrAjout;
    private ControleurAbstrait  contrReponse;
    private ControleurAbstrait  contrSuppr;
    
    // Constructeur d'une fenêtre
    public Fenetre(Personne p, ControleurAbstrait contrAuth, ControleurAbstrait contrAjout, ControleurAbstrait contrReponse, ControleurAbstrait contrSuppr) {
        connecterControleurs(contrAuth, contrAjout, contrReponse, contrSuppr);
        this.setResizable(false);
        
        vueAuth = new VueAuth(personne, contrAuth);
        this.add(vueAuth);
        this.setTitle("Se connecter — QCM");
        
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public final void connecterControleurs(ControleurAbstrait contrAuth, ControleurAbstrait contrAjout, ControleurAbstrait contrReponse, ControleurAbstrait contrSuppr) {
        this.contrAuth = contrAuth;
        this.contrAjout = contrAjout;
        this.contrReponse = contrReponse;
        this.contrSuppr = contrSuppr;
    }

    @Override
    public void update(String code) {
        switch (code) {
            case "connexionEchec":
                JOptionPane jop1;
                jop1 = new JOptionPane();
                jop1.showMessageDialog(null, "Le nom d'utilisateur ou le mot de passe est incorrect.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
                personne = vueAuth.getPersonne();
                break;
            case "connexionEns":
                personne = vueAuth.getPersonne();
                this.afficherEnseignant();
                break;
            case "connexionEtu":
                personne = vueAuth.getPersonne();
                this.afficherEtudiant();
                break;
            case "actualisation":
                this.remove(vueEtudiant);
                this.afficherEtudiant();
                break;
            case "reponse":
                QCM qcm_transmis = vueEtudiant.transmettreQCM();
                this.remove(vueEtudiant);
                this.afficherReponse((Etudiant) personne, qcm_transmis);
                break;
            case "retourEtu":
                this.remove(vueReponse);
                this.afficherEtudiant();
                break;
            case "retourEns":
                this.remove(vueCreation);
                this.afficherEnseignant();
                break;
            case "actualisationEns":
                this.remove(vueEnseignant);
                this.afficherEnseignant();
                break;
            case "creerQCM":
                this.remove(vueEnseignant);
                this.afficherCreation((Enseignant) personne);
                break;
        }
    }
    
    public void afficherEnseignant() {
        vueEnseignant = new VueEnseignant((Enseignant) personne, contrSuppr);
        this.setContentPane(vueEnseignant);
        this.setTitle("Interface Enseignant — QCM");
        this.pack();
        
        // Réafficher le tableau après un QCM
        Enseignant e = (Enseignant) personne;
        try {
            e.setQCM(e.recupererQCM());
        } catch (SQLException ex) {
            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        vueEnseignant.remplirTableau();
        this.pack();
    }
    
    public void afficherEtudiant() {        
        vueEtudiant = new VueEtudiant((Etudiant) personne);
        this.setContentPane(vueEtudiant);
        this.setTitle("Interface Etudiant — QCM");
        this.pack();
    }
    
    public void afficherReponse(Etudiant e, QCM q) {
        vueReponse = new VueReponse(e, q, contrReponse);
        this.setContentPane(vueReponse);
        this.setTitle("Répondre — " + q.getNom());
        this.pack();
        
        // Adapter la hauteur du panneau défilant après le pack()
        vueReponse.rehausser();
    }
    
    public void afficherCreation(Enseignant e) {
        vueCreation = new VueCreation(e, contrAjout);
        this.setContentPane(vueCreation);
        this.setTitle("Créer un QCM — " + e.prenommer() + " " + e.nommer());
        this.pack();
        
        // Adapter la hauteur du panneau défilant après le pack()
        vueCreation.rehausser();
    }
}
