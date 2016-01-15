package packVue;

import java.awt.Cursor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import packModele.Etudiant;
import packModele.QCM;

public class VueEtudiant extends javax.swing.JPanel {
    private final Etudiant etudiant;
    
    public VueEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        
        initComponents();
        labelBienvenue.setText("Bienvenue, " + this.etudiant.prenommer() + " " + this.etudiant.nommer());
        labelActu.addMouseListener(new Ecouteur_AccesReponse());
        tableauCours.addMouseListener(new Ecouteur_AccesReponse());
        
        labelMoyenne.setText("Votre moyenne générale est de : " + etudiant.getMoyenne() + "/20");
        remplirTableau();
    }
    
    private void remplirTableau() {
        int i = 0, j = 0;
        tableauCours.getTableHeader().setReorderingAllowed(false);
        tableauCours.getTableHeader().setResizingAllowed(false);
        
        tableauPasses.getTableHeader().setReorderingAllowed(false);
        tableauPasses.getTableHeader().setResizingAllowed(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableauPasses.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        // Remplissage des tableaux avec les données des QCM
        for (QCM qcm : etudiant.getQCM()) {
            switch (qcm.estActuel()) {
                case -1:
                    String noteM1 = etudiant.getNote(qcm);
                    
                    tableauPasses.setValueAt(qcm, i, 0);
                    tableauPasses.setValueAt(qcm.getDateFin(), i, 1);
                    tableauPasses.setValueAt(noteM1, i, 2);
                    i++;
                    break;
                case 0:
                    String note0 = etudiant.getNote(qcm);
                    
                    if ("Non noté.".equals(note0)) {
                        tableauCours.setValueAt(qcm, j, 0);
                        tableauCours.setValueAt(qcm.getDateFin(), j, 1);
                        j++;
                    } else {
                        tableauPasses.setValueAt(qcm, i, 0);
                        tableauPasses.setValueAt(qcm.getDateFin(), i, 1);
                        tableauPasses.setValueAt(note0, i, 2);
                        i++;
                    }
                    
                    break;
            }
        }
    }
    
    public class Ecouteur_AccesReponse implements ActionListener, MouseListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == labelActu) {
                etudiant.notifyObservateurs("actualisation");
            } else if ((e.getSource() == tableauCours) && (e.getClickCount() == 2)) {
                int ligne = tableauCours.rowAtPoint(e.getPoint());
                int colonne = tableauCours.columnAtPoint(e.getPoint());
                
                if ((colonne == 0) && (tableauCours.getValueAt(ligne, 0) != null)) {
                    
                }
            }
        }
        
        @Override public void mousePressed(MouseEvent e) { ; }
        @Override public void mouseReleased(MouseEvent e) { ; }
        @Override public void mouseEntered(MouseEvent e) { ; }
        @Override public void mouseExited(MouseEvent e) { ; } 
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        labelBienvenue = new JLabel();
        labelMoyenne = new JLabel();
        labelImage = new JLabel();
        labelActu = new JLabel();
        jScrollPane1 = new JScrollPane();
        tableauCours = new JTable();
        jSeparator1 = new JSeparator();
        labelQCM_Cours = new JLabel();
        labelQCM_Passes = new JLabel();
        jScrollPane2 = new JScrollPane();
        tableauPasses = new JTable();

        setBackground(new Color(255, 255, 255));

        labelBienvenue.setFont(new Font("Tahoma", 0, 18)); 
        labelBienvenue.setHorizontalAlignment(SwingConstants.CENTER);
        labelBienvenue.setText("Bienvenue, Nom de l'élève");

        labelMoyenne.setHorizontalAlignment(SwingConstants.CENTER);
        labelMoyenne.setText("Votre moyenne générale est de : .../20");

        labelImage.setIcon(new ImageIcon(getClass().getResource("/packVue/image/bandeau_bas.jpg"))); // NOI18N

        labelActu.setFont(new Font("Tahoma", 1, 11)); 
        labelActu.setForeground(new Color(204, 204, 204));
        labelActu.setHorizontalAlignment(SwingConstants.RIGHT);
        labelActu.setText("Actualiser la page  ");
        labelActu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        tableauCours.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "QCM", "Date de fin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
           
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tableauCours.setSelectionBackground(new Color(255, 204, 0));
        tableauCours.setSelectionForeground(new Color(0, 0, 0));
        jScrollPane1.setViewportView(tableauCours);

        labelQCM_Cours.setFont(new Font("Tahoma", 0, 14)); 
        labelQCM_Cours.setHorizontalAlignment(SwingConstants.LEFT);
        labelQCM_Cours.setText("Questionnaires en cours");

        labelQCM_Passes.setFont(new Font("Tahoma", 0, 14)); 
        labelQCM_Passes.setText("Questionnaires passés");

        tableauPasses.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "QCM", "Date de fin", "Note"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        tableauPasses.setSelectionBackground(new Color(255, 204, 0));
        tableauPasses.setSelectionForeground(new Color(0, 0, 0));
        jScrollPane2.setViewportView(tableauPasses);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(labelBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelMoyenne, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelActu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelQCM_Cours)
                    .addComponent(labelQCM_Passes, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelActu, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelBienvenue)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMoyenne)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(labelQCM_Cours)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(labelQCM_Passes)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(labelImage, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelBienvenue;
    private javax.swing.JLabel labelMoyenne;
    private javax.swing.JLabel labelImage;
    private javax.swing.JLabel labelActu;
    private javax.swing.JLabel labelQCM_Cours;
    private javax.swing.JLabel labelQCM_Passes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableauCours;
    private javax.swing.JTable tableauPasses;    
}
