/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packVue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import packControleur.ContrAjout;
import packControleur.ControleurAbstrait;
import packModele.Enseignant;

/**
 *
 * @author Admin
 */
public class VueCreation extends JPanel {
    private Enseignant enseignant;
    private ContrAjout contrAjout;
    
    private ArrayList<JTextField> formQuestions;
    private ArrayList<ArrayList<JTextField>> formReponses;
    private ArrayList<ArrayList<JCheckBox>> formJustes;
    private ArrayList<JLabel> formAjoutReponses;
    private JLabel ajoutQuestion;
    private JButton boutonValidation;  
    
    private Date dateDebut;
    private Date dateFin;
    
    private ArrayList<String> stockQuestion;
    
    public VueCreation(Enseignant enseignant, ControleurAbstrait contrAjout) {                  
        this.enseignant = enseignant;
        this.contrAjout = (ContrAjout) contrAjout;
        
        // Création des éléments de la vue
        initComponents();
        this.setPreferredSize(new Dimension(500, 600));
        
        // Création de la fenêtre
        initialisation();
        construction();
    }
    
    // Initialisation des composants (hors panneaux)
    private void initialisation() {
        // Liste déroulante des classes
        this.comboBoxClasse.setModel(new DefaultComboBoxModel(this.enseignant.getClasses().toArray()));
        this.comboBoxClasse.setBackground(Color.WHITE);
        
        // Nom de l'auteur
        this.labelAuteur.setFont(new Font("Tahoma", Font.PLAIN, 11));
        this.labelAuteur.setText("Auteur : " + enseignant.prenommer() + " " + enseignant.nommer());
        
        // Boutons de conformité
        ButtonGroup exclusion = new ButtonGroup();
        this.radioButtonRelative.setBackground(Color.WHITE);
        this.radioButtonStricte.setBackground(Color.WHITE);
        exclusion.add(radioButtonStricte);
        exclusion.add(radioButtonRelative);
        
        // Minimum requis pour le questionnaire
        formQuestions = new ArrayList<>();
        formReponses = new ArrayList<>();
        formJustes = new ArrayList<>();
        formAjoutReponses = new ArrayList<>();
        
        ajouterQuestion();
        ajoutQuestion = new JLabel("+ AJOUTER UNE QUESTION");
        ajoutQuestion.setForeground(Color.DARK_GRAY);
        ajoutQuestion.addMouseListener(new Ecouteur_Creation());
        ajoutQuestion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Modification du séparateur
        separateur.setForeground(new java.awt.Color(255, 204, 0));
        
        // Bouton de validation
        boutonValidation = new JButton("Valider la création du QCM");
        boutonValidation.setBackground(new java.awt.Color(255, 204, 0));
        boutonValidation.addActionListener(new Ecouteur_Creation());
        
        // Stock temporaire des questions
        stockQuestion = new ArrayList<>();
    }
    
    // Initialisation des composants (panneaux)
    private void initialisationPanneaux() {
        panneauEnonces = new JPanel();
        panneauEnonces.setLayout(new GridBagLayout());
        
        panneauDefilant.setLayout(new ScrollPaneLayout());
        panneauDefilant.getViewport().add(panneauEnonces);
        panneauDefilant.getViewport().setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        panneauDefilant.getViewport().setAlignmentY(JScrollPane.TOP_ALIGNMENT);
        
        panneauDefilant.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panneauDefilant.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panneauDefilant.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        panneauDefilant.setAlignmentY(JScrollPane.TOP_ALIGNMENT); 
        
        panneauDefilant.setBackground(Color.WHITE);
        panneauDefilant.setBorder(null);
        panneauDefilant.setMaximumSize(new Dimension(500, 500));

        panneauEnonces.setBackground(Color.WHITE);
        panneauEnonces.setPreferredSize(new Dimension(460, 2000));
    }
    
    // Construction des formulaires
    private void construction() {
        initialisationPanneaux();
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        
        panneauEnonces.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panneauEnonces.setAlignmentY(JPanel.TOP_ALIGNMENT);
        
        for (int i = 0; i < formQuestions.size(); i++) {
            // Ajout de la numérotation de question
            c.insets = new Insets(12, 0, 0, 0);
            panneauEnonces.add(new JLabel("QUESTION " + (i + 1) + "."), c);
            c.gridx++;
            
            // Ajout du formulaire de la question
            c.gridwidth = 2;
            c.insets = new Insets(12, 0, 3, 0);
            
            formQuestions.get(i).setColumns(30);
            formQuestions.get(i).setHorizontalAlignment(SwingConstants.LEFT);
            panneauEnonces.add(formQuestions.get(i), c);
            
            c.insets = new Insets(0, 0, 0, 0);
            c.gridwidth = 1;
            c.gridy++;
            c.gridx = 0;
            
            // Ajout de la numération de réponse
            for (int j = 0; j < formReponses.get(i).size(); j++) {
                JLabel reponse = new JLabel("Réponse " + (j + 1));
                reponse.setFont(new Font("Tahoma", Font.PLAIN, 11));
                
                panneauEnonces.add(reponse, c);
                c.gridx++;
                
                formReponses.get(i).get(j).setColumns(18);
                panneauEnonces.add(formReponses.get(i).get(j), c);
                c.gridx++;
                
                formJustes.get(i).get(j).setBackground(Color.WHITE);
                formJustes.get(i).get(j).setFont(new Font("Tahoma", Font.PLAIN, 11));
                formJustes.get(i).get(j).setHorizontalAlignment(SwingConstants.LEFT);
                panneauEnonces.add(formJustes.get(i).get(j), c);
                
                c.gridx = 0;
                c.gridy++;
            }
            
            c.gridx = 1;
            formAjoutReponses.get(i).setForeground(Color.DARK_GRAY);
            panneauEnonces.add(formAjoutReponses.get(i), c);
            
            c.gridy++;
            c.gridx = 0;
        }
        
        // Permet d'ajouter une question
        c.insets = new Insets(10, 0, 0, 0);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy++;
        panneauEnonces.add(ajoutQuestion, c);
        
        // Bouton de validation
        c.insets = new Insets(15, 0, 0, 0);
        c.gridy++;
        c.anchor = GridBagConstraints.CENTER;
        panneauEnonces.add(boutonValidation, c);
                
        // Remplissage de fin
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.gridy++;
        panneauEnonces.add(javax.swing.Box.createVerticalGlue(), c);
    }
    
    public class Ecouteur_Creation implements ActionListener, MouseListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == boutonValidation) {
                /*
                    L'utilisateur a essayé de valider le QCM. Conditions :
                        - Tous les champs fixes (sauf consignes) doivent être remplis (sans espaces)
                        - Les dates doivent être correctement formatées
                        - Les dates doivent être cohérentes
                        
                        - Il doit y avoir au moins une question valable.
                            - Une question est valable si :
                                > Son champ de texte n'est pas vide.
                                > Elle a deux réponses valables au moins.
                                > Il y a au moins une réponse juste de cochée.
                        
                        - Laisser un champ vide permet de l'ignorer.
                */
                
                if (tousChampsFixesRemplis()) {
                    if (dateCorrecte(textFieldDateDebut.getText()) && dateCorrecte(textFieldDateFin.getText())) {
                        try {
                            DateFormat formateur = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            
                            dateDebut = formateur.parse(textFieldDateDebut.getText());
                            dateFin = formateur.parse(textFieldDateFin.getText());
                            
                            if (dateFin.after(dateDebut))
                                analyserQuestionnaire();
                            else
                                System.out.println("Gérer le cas où les dates sont inversées");
                        } catch (ParseException ex) {
                            System.out.println("Les dates ne sont pas correctement renseignées");
                        }
                    } else
                        System.out.println("Gérer le cas où le formatage n'est pas très bon quand même");
                } else
                    System.out.println("Gérer le cas où tous les champs fixes ne sont pas remplis, pardi ! ");
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent me) {
            int defilement = panneauDefilant.getVerticalScrollBar().getValue();
            
            if (me.getSource() == ajoutQuestion) {               
                ajouterQuestion();
                construction();
                rehausser();
            } else if (formAjoutReponses.contains((JLabel) me.getSource())) {
                ajouterReponse(formAjoutReponses.indexOf((JLabel) me.getSource()));
                construction();
                rehausser();
            }
            
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    panneauDefilant.getVerticalScrollBar().setValue(defilement);
                }
            });
        }

        @Override public void mouseEntered(MouseEvent me) { ; }
        @Override public void mouseExited(MouseEvent me) { ; }
        @Override public void mouseReleased(MouseEvent me) { ; }
        @Override public void mousePressed(MouseEvent me) { ; }
    }
    
    private void analyserQuestionnaire() {
        int nombreQuestionsValables = 0;
        boolean questionnaireValable = true;
        
        for (int i = 0; i < formQuestions.size(); i++) {
            boolean estValable = true;
            
            // Le champ de la question n'est pas vide
            if (formQuestions.get(i).getText().replaceAll(" ", "").length() < 1) {
                estValable = false;
                continue;
            }
            
            // Vérifications de l'intégrité des réponses
            int nombreEstJuste = 0;
            int nombreReponsesValables = 0;
            
            for (int j = 0; j < formReponses.get(i).size(); j++) {
                boolean estValable_r = false;                
                
                // Si le champ Réponse n'est pas vide
                if (formReponses.get(i).get(j).getText().replaceAll(" ", "").length() > 0) {
                    nombreReponsesValables++;
                    estValable_r = true;
                }
                
                // Si "Réponse juste" est cochée
                if (formJustes.get(i).get(j).isSelected() && estValable_r)
                    nombreEstJuste++;
            }
            
            // Si la question est valable, on incrémente la variable de départ
            if (estValable && nombreEstJuste > 0 && nombreReponsesValables > 1) {
                nombreQuestionsValables++;
                stockQuestion.add(Integer.toString(i));
            } else
                questionnaireValable = false;
        }
        
        if (nombreQuestionsValables > 0 && questionnaireValable) {
            ArrayList<String> donnees = preparerEnvoi();
            for (String str : donnees) {
                System.out.println(str);
            }
            
            // Envoi des données au contrôleur pour le traitement
            contrAjout.control(donnees);
            enseignant.notifyObservateurs("retourEns");
        } else
            System.out.println("Gérer / Demander à vérifier le contenu du QCM");
    }
    
    private ArrayList<String> preparerEnvoi() {
        ArrayList<String> donnees = new ArrayList<>();
        
        // Ajout des informations fixes
        donnees.add(textFieldNom.getText());                                                                // Nom du QCM
        donnees.add((textFieldConsigne.getText().length() > 0) ? textFieldConsigne.getText() : "Aucune.");  // Consignes du QCM
        donnees.add(comboBoxClasse.getSelectedItem().toString());                                           // Classe affectée
        donnees.add(Long.toString(dateDebut.getTime()));                                                    // Date de début
        donnees.add(Long.toString(dateFin.getTime()));                                                      // Date de fin
        donnees.add(Boolean.toString(radioButtonRelative.isSelected()));                                    // Conformité relative
        
        // Ajout des informations variables
        for (int i = 0; i < formQuestions.size(); i++) {
            if (stockQuestion.contains(Integer.toString(i))) {
                donnees.add(formQuestions.get(i).getText());
                int indice = donnees.size() - 1;
                int nombreReponses = 0;
                
                for (int j = 0; j < formReponses.get(i).size(); j++) {
                    if (formReponses.get(i).get(j).getText().length() > 0) {
                        donnees.add(formJustes.get(i).get(j).isSelected() + formReponses.get(i).get(j).getText());
                        nombreReponses++;
                    }
                }
                
                donnees.set(indice, nombreReponses + formQuestions.get(i).getText());
            }
        }
        
        return donnees;
    }
    
    private boolean tousChampsFixesRemplis() {
        return (textFieldNom.getText().replaceAll(" ", "").length() > 0)
            && (textFieldDateDebut.getText().replaceAll(" ", "").length() > 0)
            && (textFieldDateFin.getText().replaceAll(" ", "").length() > 0);
    }
    
    private boolean dateCorrecte(String str) {
        return str.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}$$");
    }
    
    private void ajouterQuestion() {
        formQuestions.add(new JTextField());
        formReponses.add(new ArrayList<>());
        formReponses.get(formQuestions.size() - 1).add(new JTextField());
        formReponses.get(formQuestions.size() - 1).add(new JTextField());
        
        formJustes.add(new ArrayList<>());
        formJustes.get(formQuestions.size() - 1).add(new JCheckBox("Réponse juste"));
        formJustes.get(formQuestions.size() - 1).add(new JCheckBox("Réponse juste"));
        
        formAjoutReponses.add(new JLabel("+ AJOUTER UNE RÉPONSE"));
        formAjoutReponses.get(formAjoutReponses.size() - 1).addMouseListener(new Ecouteur_Creation());
        formAjoutReponses.get(formAjoutReponses.size() - 1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    private void ajouterReponse(int i) {
        if (i >= 0 && i < formReponses.size()) {
            formReponses.get(i).add(new JTextField());
            formJustes.get(i).add(new JCheckBox("Réponse juste"));
        }
    }
    
    public void rehausser() {
        panneauEnonces.setPreferredSize(new Dimension(500, boutonValidation.getBounds().y + 200));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        labelTitre = new javax.swing.JLabel();
        labelAuteur = new javax.swing.JLabel();
        labelNomQCM = new javax.swing.JLabel();
        labelConsigne = new javax.swing.JLabel();
        labelClasse = new javax.swing.JLabel();
        labelDateDebut = new javax.swing.JLabel();
        labelDateFin = new javax.swing.JLabel();
        labelConformite = new javax.swing.JLabel();
        textFieldNom = new javax.swing.JTextField();
        textFieldConsigne = new javax.swing.JTextField();
        textFieldDateDebut = new javax.swing.JTextField();
        comboBoxClasse = new javax.swing.JComboBox<String>();
        textFieldDateFin = new javax.swing.JTextField();
        radioButtonStricte = new javax.swing.JRadioButton();
        radioButtonRelative = new javax.swing.JRadioButton();
        separateur = new javax.swing.JSeparator();
        panneauDefilant = new javax.swing.JScrollPane();
        panneauEnonces = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        labelTitre.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 18)); // NOI18N
        labelTitre.setText("Interface de création d'un QCM");

        labelAuteur.setText("Auteur : [nom_enseignant]");
        labelNomQCM.setText("Nom du QCM :");
        labelConsigne.setText("Consignes :");
        labelClasse.setText("Classe :");

        labelDateDebut.setText("Date de début :");
        labelDateFin.setText("Date de fin :");

        labelConformite.setText("Conformité :");

        textFieldNom.setText("DS Méthodologie de production d'applications");
        textFieldConsigne.setText("Pas de points négatifs sur ce QCM !");
        textFieldDateDebut.setText("27/12/2015 15:00:00");
        textFieldDateFin.setText("27/12/2015 16:00:00");

        radioButtonStricte.setSelected(true);
        radioButtonStricte.setText("stricte");

        radioButtonRelative.setText("relative");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panneauEnonces);
        panneauEnonces.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );

        panneauDefilant.setViewportView(panneauEnonces);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/packVue/image/bandeau_bas.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelAuteur)
                .addGap(183, 183, 183))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panneauDefilant)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelDateFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(293, 293, 293))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelNomQCM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelConsigne, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelClasse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelDateDebut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldNom)
                            .addComponent(textFieldConsigne)
                            .addComponent(comboBoxClasse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textFieldDateDebut)
                            .addComponent(textFieldDateFin)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelTitre)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(radioButtonStricte)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioButtonRelative)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelConformite)
                        .addGap(19, 19, 19)
                        .addComponent(separateur, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(labelTitre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAuteur)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomQCM)
                    .addComponent(textFieldNom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldConsigne, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelConsigne))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelClasse)
                    .addComponent(comboBoxClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDateDebut)
                    .addComponent(textFieldDateDebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDateFin)
                    .addComponent(textFieldDateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelConformite)
                    .addComponent(radioButtonRelative)
                    .addComponent(radioButtonStricte))
                .addGap(27, 27, 27)
                .addComponent(separateur, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panneauDefilant, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1))
        );
    }// </editor-fold>                        
                                       
   // Variables declaration - do not modify                     
    private javax.swing.JComboBox<String> comboBoxClasse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panneauEnonces;
    private javax.swing.JScrollPane panneauDefilant;
    private javax.swing.JSeparator separateur;
    private javax.swing.JLabel labelAuteur;
    private javax.swing.JLabel labelClasse;
    private javax.swing.JLabel labelConformite;
    private javax.swing.JLabel labelConsigne;
    private javax.swing.JLabel labelDateDebut;
    private javax.swing.JLabel labelDateFin;
    private javax.swing.JLabel labelNomQCM;
    private javax.swing.JLabel labelTitre;
    private javax.swing.JRadioButton radioButtonRelative;
    private javax.swing.JRadioButton radioButtonStricte;
    private javax.swing.JTextField textFieldConsigne;
    private javax.swing.JTextField textFieldDateDebut;
    private javax.swing.JTextField textFieldDateFin;
    private javax.swing.JTextField textFieldNom;
    // End of variables declaration  
}

