/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packVue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import packModele.Etudiant;
import packModele.QCM;

/**
 *
 * @author Admin
 */
public class VueReponse extends JPanel {
    private Etudiant etudiant;
    private QCM qcm;
   
    public VueReponse(Etudiant etudiant, QCM qcm) {
        this.etudiant = etudiant;
        this.qcm = qcm;
        
        // Construction de la fenêtre
        initComponents();
        labelEleve.setText(etudiant.prenommer() + " " + etudiant.nommer());
    }
    
    // Déclaration des variables des différents éléments
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelEleve;
    private javax.swing.JLabel labelTemps;
    private javax.swing.JLabel labelNom;
    private javax.swing.JLabel labelConsignes;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator separateur;
    // Fin de déclaration de variables des différents éléments
   
    private void initComponents() {

        jLabel1 = new JLabel();
        labelEleve = new JLabel();
        labelTemps = new JLabel();
        labelNom = new JLabel();
        labelConsignes = new JLabel();
        jScrollPane1 = new JScrollPane();
        jPanel1 = new JPanel();
        jLabel6 = new JLabel();
        jCheckBox1 = new JCheckBox();
        jCheckBox2 = new JCheckBox();
        jCheckBox3 = new JCheckBox();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jCheckBox4 = new JCheckBox();
        jCheckBox5 = new JCheckBox();
        jCheckBox6 = new JCheckBox();
        jCheckBox7 = new JCheckBox();
        jCheckBox8 = new JCheckBox();
        jButton1 = new JButton();
        separateur = new JSeparator();

        setBackground(new Color(255, 255, 255));

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/packVue/image/bandeau_bas.jpg"))); 

        labelEleve.setText("  Élève : [prénom] [nom]");

        labelTemps.setHorizontalAlignment(SwingConstants.RIGHT);
        labelTemps.setText("Temps restant : 1 h 17 min 35 s  ");

        labelNom.setFont(new java.awt.Font("Tahoma", 0, 18)); 
        labelNom.setHorizontalAlignment(SwingConstants.CENTER);
        labelNom.setText("[nom_qcm]");

        labelConsignes.setText("  Consignes : [description_qcm]");

        jScrollPane1.setBackground(new Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setMaximumSize(new Dimension(500, 32767));

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(500, 452));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("1. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla efficitur ?");

        jCheckBox1.setBackground(new Color(255, 255, 255));
        jCheckBox1.setText("Elementum");

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Dignissim");

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setText("Pellentesque");

        jLabel7.setFont(new Font("Tahoma", 1, 11)); 
        jLabel7.setText("2. Nullam in ante et diam faucibus varius quis nec eros. Fusce arcu felis, vestibulum in commodo");
        jLabel7.setToolTipText("");

        jLabel8.setFont(new Font("Tahoma", 1, 11)); 
        jLabel8.setText("non, euismod vel nisl. Vestibulum id porta magna ?");

        jCheckBox4.setBackground(new Color(255, 255, 255));
        jCheckBox4.setText("Réponse A");

        jCheckBox5.setBackground(new Color(255, 255, 255));
        jCheckBox5.setText("Réponse B");

        jCheckBox6.setBackground(new Color(255, 255, 255));
        jCheckBox6.setText("Réponse C");

        jCheckBox7.setBackground(new Color(255, 255, 255));
        jCheckBox7.setText("Réponse D");

        jCheckBox8.setBackground(new Color(255, 255, 255));
        jCheckBox8.setText("Réponse E");

        jButton1.setText("Valider les réponses");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox3)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 489, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox8)
                            .addComponent(jCheckBox7)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox7)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox8)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );

        jScrollPane1.setViewportView(jPanel1);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelConsignes, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelNom, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(labelEleve, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelTemps, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(separateur, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTemps, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEleve, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelNom, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separateur, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(labelConsignes)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }

    


    

}
