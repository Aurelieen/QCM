/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packVue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import packControleur.ControleurAbstrait;
import packModele.Personne;


public class VueAuth extends JPanel {
    private final ControleurAbstrait contrAuth;
    private Personne personne;
    
    public VueAuth(Personne p, ControleurAbstrait contrAuth) {
        this.contrAuth = contrAuth;
        this.personne = p;
        
        initComponents();
        boutonConnexion.addActionListener(new Ecouteur_Connexion());
    }

    // Classe interne, interception de la connexion
    public class Ecouteur_Connexion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == boutonConnexion){
                VueAuth.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                ArrayList<String> identifiants = new ArrayList();
                
                identifiants.add(champPseudo.getText().replaceAll("'", ""));
                identifiants.add(String.valueOf(champPassword.getPassword()).replaceAll("'", ""));
                
                personne = (Personne) contrAuth.control(identifiants);
                personne.addObservateur((Observateur) SwingUtilities.getWindowAncestor(VueAuth.this));
                
                // Notifions la fenêtre de l'état de l'utilisateur
                if ("Enseignant".equals(personne.toString()) && personne.nommer() != null) {
                    personne.notifyObservateurs("connexionEns");
                } else {
                    if ("Etudiant".equals(personne.toString()) && personne.nommer() != null) {
                        personne.notifyObservateurs("connexionEtu");
                    } else {
                        personne.notifyObservateurs("connexionEchec");
                    }
                }
                
                VueAuth.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    public Personne getPersonne() {
        return personne;
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel7 = new JLabel();
        jLabel2 = new JLabel();
        champPseudo = new JTextField();
        labelUtilisateur = new JLabel();
        labelPassword = new JLabel();
        labelTitre = new JLabel();
        labelSousTitre = new JLabel();
        boutonConnexion = new JButton();
        champPassword = new JPasswordField();
        jSeparator1 = new JSeparator();
        labelAvertissement = new JLabel();

        jLabel7.setText("jLabel7");

        setBackground(new Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/packVue/image/bandeau_bas.jpg"))); // NOI18N

        champPseudo.setToolTipText("");
        champPseudo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        champPseudo.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        labelUtilisateur.setText("Utilisateur :");

        labelPassword.setText("Mot de passe :");

        labelTitre.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setText("Interface d'évaluation");

        labelSousTitre.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        labelSousTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelSousTitre.setText("Questionnaires à choix multiples");

        boutonConnexion.setBackground(new java.awt.Color(255, 204, 0));
        boutonConnexion.setText("Se connecter");

        champPseudo.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        champPassword.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        labelAvertissement.setHorizontalAlignment(SwingConstants.CENTER);
        labelAvertissement.setText("Fermez le logiciel après l'avoir utilisé.");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(labelTitre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(labelSousTitre, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator1)
                                .addComponent(boutonConnexion, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(labelPassword, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelUtilisateur, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(champPseudo)
                                                .addComponent(champPassword, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))))
                        .addGap(90, 90, 90))
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelAvertissement, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(labelTitre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSousTitre)
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(champPseudo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelUtilisateur, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(champPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(boutonConnexion)
                        .addGap(63, 63, 63)
                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAvertissement)
                        .addGap(46, 46, 46)
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
        
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
   
    }

    private javax.swing.JButton boutonConnexion;
    private javax.swing.JLabel labelUtilisateur;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelTitre;
    private javax.swing.JLabel labelSousTitre;
    private javax.swing.JLabel labelAvertissement;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPasswordField champPassword;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField champPseudo;
}
