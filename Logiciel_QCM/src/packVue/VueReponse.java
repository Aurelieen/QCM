/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packVue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import packControleur.ContrReponse;
import packControleur.ControleurAbstrait;
import packModele.Etudiant;
import packModele.QCM;
import packModele.Question;
import packModele.Reponse;

/**
 *
 * @author Admin
 */
public class VueReponse extends JPanel {
    private Etudiant etudiant;
    private QCM qcm;
    private long tempsRestant;
    private Timer timer;
    
    protected long diffSecondes;
    protected long diffMinutes;
    protected long diffHeures;
    protected long diffJours;
    
    private ArrayList<JLabel> enonces;
    private ArrayList<JCheckBox> reponses;
    private boolean deja_prevenu;
    
    private final ContrReponse contrReponse;
    
    public VueReponse(Etudiant etudiant, QCM qcm, ControleurAbstrait contrReponse) {
        this.contrReponse = (ContrReponse) contrReponse;
        this.etudiant = etudiant;
        this.qcm = qcm;
        this.deja_prevenu = false;
        
        Calendar cal = formaterDateFin();
        this.tempsRestant = cal.getTime().getTime() - new Date().getTime();
        
        this.timer = temporiser();
        this.timer.start();
        
        // Construction de la fenêtre
        initComponents();
        labelEleve.setText("  " + etudiant.prenommer() + " " + etudiant.nommer());
        labelNom.setText(qcm.getNom());
        labelConsignes.setText(qcm.getConsignes());
        labelTemps.setFont(new Font("Tahoma", Font.PLAIN, 11));
        
        // Construction des énoncés et des réponses possibles
        this.enonces = new ArrayList<>();
        this.reponses = new ArrayList<>();
        construireChoix();
        
        boutonEnvoi.addActionListener(new Ecouteur_Reponse());
        this.setPreferredSize(new Dimension(500, 600));
    }
    
    private void construireChoix() {
        GridBagConstraints c = new GridBagConstraints();
        int i = 1;
        
        c.gridx = c.gridy = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        
        panneauEnonces.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panneauEnonces.setAlignmentY(JPanel.TOP_ALIGNMENT);
        
        for (Question question : qcm.getQuestions()) {
            c.insets = new Insets(15, 7, 0, 0);
            
            JLabel enonce = new JLabel("<html><p style=\"width: 400px;\">QUESTION " + i + ". " + question.getDescription() + "</html></p>");
            enonce.setHorizontalAlignment(JLabel.LEFT);
            
            enonces.add(enonce);
            panneauEnonces.add(enonces.get(enonces.size() - 1), c);
            
            i++;
            
            c.insets = new Insets(0, 7, 0, 0);
            for (Reponse reponse : question.getReponses()) {
                JCheckBox choix = new JCheckBox("<html><p style=\"width: 380px;\">" + reponse.getDescription() + "</html></p>");
                
                choix.setBackground(Color.WHITE);
                choix.setVerticalTextPosition(SwingConstants.TOP);
                choix.setFont(new Font("Tahoma", Font.PLAIN, 11));
                
                reponses.add(choix);
                
                c.gridy++;
                panneauEnonces.add(reponses.get(reponses.size() - 1), c);
            }
            
            c.gridy++;
        }
        
        c.gridy++;
        c.insets = new Insets(10, 7, 0, 0);
        c.anchor = GridBagConstraints.CENTER;
        panneauEnonces.add(boutonEnvoi, c);
        
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.gridy++;
        panneauEnonces.add(javax.swing.Box.createVerticalGlue(), c);
    }
    
    private Timer temporiser (){
        ActionListener action = (ActionEvent event) -> {           
            if (tempsRestant > 0) {
                tempsRestant -= 1000;
                
                diffJours = TimeUnit.MILLISECONDS.toDays(tempsRestant);
                diffHeures = TimeUnit.MILLISECONDS.toHours(tempsRestant - TimeUnit.DAYS.toMillis(diffJours));
                diffMinutes = TimeUnit.MILLISECONDS.toMinutes(tempsRestant - TimeUnit.DAYS.toMillis(diffJours) - TimeUnit.HOURS.toMillis(diffHeures));
                diffSecondes = TimeUnit.MILLISECONDS.toSeconds(tempsRestant - TimeUnit.DAYS.toMillis(diffJours) - TimeUnit.HOURS.toMillis(diffHeures) - TimeUnit.MINUTES.toMillis(diffMinutes));
                
                String intervalle = "Temps restant : " + Long.toString(diffJours) + " j " + Long.toString(diffHeures) + " h " + Long.toString(diffMinutes) + " min " + Long.toString(diffSecondes) + " s  ";
                labelTemps.setText(intervalle);
                repaint();
            } else{
                labelTemps.setText("Date limite passée.  ");
                labelTemps.repaint();
                timer.stop();
            }
        };
        
        return new Timer (1000, action);
    }
    
    private Calendar formaterDateFin() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(qcm.getDateFin_D());
        
        return cal;
    }
    
    public void rehausser() {
        panneauEnonces.setPreferredSize(new Dimension(480, boutonEnvoi.getBounds().y + 100));
    }
    
    public class Ecouteur_Reponse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == boutonEnvoi) {
                if (qcm.getDateFin_D().after(new Date())) {
                    if (reponsesOubliees() && !deja_prevenu) {
                        deja_prevenu = true;
                        // Gérer le cas où on n'a pas tout coché
                    } else {
                        // Transmission et formatage des informations au contrôleur des réponses
                        ArrayList<String> controles = new ArrayList<>();
                        controles.add(Integer.toString(qcm.getId()));
                        
                        for (JCheckBox reponse : reponses) {
                            controles.add(Boolean.toString(reponse.isSelected()));
                        }
                        
                        contrReponse.donnerEtudiant(etudiant);
                        contrReponse.control(controles);
                        etudiant.notifyObservateurs("retourEtu");
                    }
                } else {
                    // Gérer le cas où c'est trop taaard bâtaaaard.
                }
            }
        }
        
        /* @Override public void mouseClicked(MouseEvent me) { ; }
        @Override public void mousePressed(MouseEvent me) { ; }
        @Override public void mouseReleased(MouseEvent me) { ; }
        @Override public void mouseEntered(MouseEvent me) { ; }
        @Override public void mouseExited(MouseEvent me) { ; } */
    }
    
    private boolean reponsesOubliees() {
        int i = 0;
        for (Question question : qcm.getQuestions()) {
            boolean zeroReponse = true;
            for (Reponse reponse : question.getReponses()) {
                if (reponses.get(i).isSelected())
                    zeroReponse = false;
                
                i++;
            }
            
            if (zeroReponse)
                return true;
        }
        
        return false;
    }
        
    // Déclaration des variables des différents éléments
    private javax.swing.JButton boutonEnvoi;
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
    private javax.swing.JPanel panneauEnonces;
    private javax.swing.JScrollPane panneauDefilant;
    private javax.swing.JSeparator separateur;
    // Fin de déclaration de variables des différents éléments
   
    private void initComponents() {

        // Initialisation des éléments
        jLabel1 = new JLabel();
        labelEleve = new JLabel();
        labelTemps = new JLabel();
        labelNom = new JLabel();
        labelConsignes = new JLabel();
        panneauDefilant = new JScrollPane();
        panneauEnonces = new JPanel();
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
        boutonEnvoi = new JButton();
        separateur = new JSeparator();

        setBackground(new Color(255, 255, 255));
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/packVue/image/bandeau_bas.jpg"))); 

        labelEleve.setText("  Élève : [prénom] [nom]");

        labelTemps.setHorizontalAlignment(SwingConstants.RIGHT);
        labelTemps.setText("Chargement…  ");
 
        labelNom.setFont(new java.awt.Font("Tahoma", 0, 18)); 
        labelNom.setHorizontalAlignment(SwingConstants.CENTER);
        labelNom.setText("[nom_qcm]");

        labelConsignes.setText("  Consignes : [description_qcm]");
        
        panneauDefilant.setBackground(Color.WHITE);
        panneauDefilant.setBorder(null);
        panneauDefilant.setMaximumSize(new Dimension(500, 500));

        panneauEnonces.setBackground(Color.WHITE);
        panneauEnonces.setPreferredSize(new Dimension(500, 452));
        
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("1. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla efficitur ?");

        jCheckBox1.setBackground(new Color(255, 255, 255));
        jCheckBox1.setText("Elementum");

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Dignissim");

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setText("Pellentesque");

        jLabel7.setFont(new Font("Tahoma", 1, 11)); 
        jLabel7.setText("2. Nullam in ante et d vestibulum in commodo");
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

        boutonEnvoi.setText("Valider les réponses");
        
        this.setBackground(Color.WHITE);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panneauDefilant, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(panneauDefilant, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
        
        // Ajouts d'organisation
        panneauEnonces.setLayout(new GridBagLayout());
        
        panneauDefilant.setLayout(new ScrollPaneLayout());
        panneauDefilant.getViewport().add(panneauEnonces);
        panneauDefilant.getViewport().setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        panneauDefilant.getViewport().setAlignmentY(JScrollPane.TOP_ALIGNMENT);
        
        panneauDefilant.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panneauDefilant.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panneauDefilant.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        panneauDefilant.setAlignmentY(JScrollPane.TOP_ALIGNMENT);
    }
}
