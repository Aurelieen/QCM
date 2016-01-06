package packVue;

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
import javax.swing.table.DefaultTableModel;

public class VueEtudiant extends javax.swing.JPanel {

    public VueEtudiant() {
        initComponents();
        jLabel4.addMouseListener(new Ecouteur_AccesReponse());
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jSeparator1 = new JSeparator();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jScrollPane2 = new JScrollPane();
        jTable2 = new JTable();

        setBackground(new Color(255, 255, 255));

        jLabel1.setFont(new Font("Tahoma", 0, 18)); 
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Bienvenue, Nom de l'élève");

        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Votre moyenne générale est de : .../20");

        jLabel3.setIcon(new ImageIcon(getClass().getResource("/packVue/image/bandeau_bas.jpg"))); // NOI18N

        jLabel4.setFont(new Font("Tahoma", 1, 11)); 
        jLabel4.setForeground(new Color(204, 204, 204));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText("Actualiser la page  ");

        jTable1.setModel(new DefaultTableModel(
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
        jTable1.setSelectionBackground(new Color(255, 204, 0));
        jTable1.setSelectionForeground(new Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new Font("Tahoma", 0, 14)); 
        jLabel5.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel5.setText("Questionnaires en cours");

        jLabel6.setFont(new Font("Tahoma", 0, 14)); 
        jLabel6.setText("Questionnaires passés");

        jTable2.setModel(new DefaultTableModel(
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
        
        jTable2.setSelectionBackground(new Color(255, 204, 0));
        jTable2.setSelectionForeground(new Color(0, 0, 0));
        jScrollPane2.setViewportView(jTable2);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
   
    public class Ecouteur_AccesReponse implements ActionListener, MouseListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource()==jLabel4){
                System.out.println("Actualisation de la page");
        }
        }
        @Override public void mousePressed(MouseEvent e) { ; }
        @Override public void mouseReleased(MouseEvent e) { ; }
        @Override public void mouseEntered(MouseEvent e) { ; }
        @Override public void mouseExited(MouseEvent e) { ; }
        
    }
    
}
