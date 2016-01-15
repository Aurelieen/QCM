
package packVue;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import packModele.QCM;

public class VueDetails extends javax.swing.JDialog {
    private final QCM questionnaire;
    private final ArrayList<ArrayList<String>> notes;
    
    public VueDetails(JFrame parent, QCM questionnaire) {
        super(parent, "Voir les détails du QCM : " + questionnaire, true);
        initComponents();
        
        this.questionnaire = questionnaire;
        notes = this.questionnaire.recupererNotes();
        this.labelNom.setText(this.questionnaire.toString());
        
        remplirTableau();
        this.setVisible(true);
    }
    
    private void remplirTableau() {        
        int i = 0;
        tableauQCM.getTableHeader().setReorderingAllowed(false);
        tableauQCM.getTableHeader().setResizingAllowed(false);
        
        tableauQCM.getColumnModel().getColumn(0).setPreferredWidth(300);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableauQCM.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        
        for (ArrayList<String> releve : notes) {
            tableauQCM.setValueAt(releve.get(0) + " " + releve.get(1), i, 0);
            tableauQCM.setValueAt(releve.get(2), i, 1);
            
            i++;
        }
    }
   
    @SuppressWarnings("unchecked")
    private void initComponents() {

        panneauPrincipal = new JPanel();
        labelTitre = new JLabel();
        labelNom = new JLabel();
        jSeparator1 = new JSeparator();
        jScrollPane1 = new JScrollPane();
        tableauQCM = new JTable();
        jLabel3 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new Color(255, 255, 255));

        panneauPrincipal.setBackground(new Color(255, 255, 255));

        labelTitre.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setText("Résultats du QCM");

        labelNom.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelNom.setHorizontalAlignment(SwingConstants.CENTER);
        labelNom.setText("[nom_qcm]");

        jScrollPane1.setBackground(new Color(255, 255, 255));

        tableauQCM.setModel(new DefaultTableModel(
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
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Etudiant", "Note (sur 20)"
            }
        ));
        tableauQCM.setSelectionBackground(new Color(255, 204, 0));
        tableauQCM.setSelectionForeground(new Color(0, 0, 0));
        jScrollPane1.setViewportView(tableauQCM);

        jLabel3.setIcon(new ImageIcon(getClass().getResource("/packVue/image/bandeau_bas.jpg"))); // NOI18N

        GroupLayout jPanel1Layout = new GroupLayout(panneauPrincipal);
        panneauPrincipal.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(labelTitre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelNom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitre)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNom)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panneauPrincipal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panneauPrincipal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    

    private javax.swing.JLabel labelTitre;
    private javax.swing.JLabel labelNom;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panneauPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableauQCM;

}
