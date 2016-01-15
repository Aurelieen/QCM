/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packVue;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import packControleur.ContrAjout;
import packControleur.ContrAuth;
import packControleur.ContrReponse;
import packControleur.ContrSuppr;
import packControleur.ControleurAbstrait;
import packModele.Enseignant;
import packModele.Etudiant;
import packModele.Personne;

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
        }
    }
    
    public void afficherEnseignant() {
        vueEnseignant = new VueEnseignant((Enseignant) personne);
        this.setContentPane(vueEnseignant);
        this.setTitle("Interface Enseignant — QCM");
        this.pack();
    }
    
    public void afficherEtudiant() {        
        vueEtudiant = new VueEtudiant((Etudiant) personne);
        this.setContentPane(vueEtudiant);
        this.setTitle("Interface Etudiant — QCM");
        this.pack();
    }
}
