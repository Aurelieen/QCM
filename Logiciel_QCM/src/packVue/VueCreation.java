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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import packModele.Enseignant;

/**
 *
 * @author Admin
 */
public class VueCreation extends JPanel {
    private Enseignant enseignant;
    // 500 de largeur
    
    // Ajout des labels 
    JLabel titre;
    JLabel nomauteur;
    JLabel nomqcm;
    JLabel consigne;
    JLabel classe;
    JLabel date_debut;
    JLabel date_fin;    
    JLabel conformite;
    JLabel nomenseignant;
    
    // Ajout des RadioButton : selection de la conformite
    
    JRadioButton stricte;
    JRadioButton relative;
    
    // Ajout de la ComboBox : choix de la classe concernee
    
    JComboBox choix_classe;
    
    // Ajout des JTextField : remplissage du formulaire
    
    JTextField in_nomqcm;
    JTextField in_consigne;
    JTextField in_datedebut;
    JTextField in_datefin;
    
    public VueCreation(){                  
        
        // Initialisation des JLabel
        
        titre = new JLabel("Interface de création d'un QCM");
        titre.setFont(new Font("Arial", Font.BOLD, 20));        
        nomauteur = new JLabel("Auteur : ");         
        nomqcm = new JLabel("Nom du QCM : ", SwingConstants.LEFT);
        // nomqcm.setHorizontalAlignment(SwingConstants.WEST);
        consigne = new JLabel("Consigne du QCM : ");        
        classe = new JLabel("Classe affectée : ");        
        date_debut = new JLabel("Date de début : ");        
        date_fin = new JLabel("Date de fin : ");        
        conformite = new JLabel("Conformité : ");        
        nomenseignant = new JLabel("[nom_enseignant]");
        
        // Initialisation des RadioButton
        
        stricte = new JRadioButton("stricte");
        relative = new JRadioButton("relative");
        ButtonGroup group = new ButtonGroup();
        group.add(stricte);
        group.add(relative);
        
        // Initialisation de la JComboBox
        
        ArrayList<String> mes_classes = null; // enseignant.getClasses();
        choix_classe = new JComboBox();
/*        for (int i = 0; i <= mes_classes.size(); i++) {
            choix_classe.addItem(mes_classes.get(i));
        }*/
        choix_classe.setBackground(Color.LIGHT_GRAY);
        
        // Initialisation des JTextField
        
        in_nomqcm = new JTextField();
        in_consigne = new JTextField();
        in_datedebut = new JTextField();
        in_datefin = new JTextField();
        
        in_nomqcm.setPreferredSize(new Dimension(350, 20));
        in_consigne.setPreferredSize(new Dimension(350, 20));
        in_datedebut.setPreferredSize(new Dimension(350, 20));
        in_datefin.setPreferredSize(new Dimension(350, 20));
        
        in_datedebut.setText("JJ/MM/AAAA HH:MM:SS");
        in_datefin.setText("JJ/MM/AAAA HH:MM:SS");
        
      
        in_datedebut.setFont(new Font("Arial", Font.ITALIC, 12));
        in_datefin.setFont(new Font("Arial", Font.ITALIC, 12));
        
        in_datedebut.setForeground(Color.gray);
        in_datefin.setForeground(Color.gray);
        
        in_datedebut.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
            in_datedebut = ((JTextField)e.getSource());
            in_datedebut.setText("");
            in_datedebut.getFont().deriveFont(Font.PLAIN);
            in_datedebut.setForeground(Color.black);
            in_datedebut.removeMouseListener(this);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}            
        });
        
         in_datefin.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
            in_datefin = ((JTextField)e.getSource());
            in_datefin.setText("");
            in_datefin.getFont().deriveFont(Font.PLAIN);
            in_datefin.setForeground(Color.black);
            in_datefin.removeMouseListener(this);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}            
        });
        
        // ajout des elements dans la fenetre
         
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridheight=1;
        gbc.gridwidth=2;        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;        
        this.add(titre,gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(nomauteur,gbc);
        gbc.gridx=2;
        gbc.gridy=2;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(nomenseignant,gbc);
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        this.add(nomqcm,gbc);
        gbc.gridx=1;
        gbc.gridy=4;
        gbc.gridheight=1;
        gbc.gridwidth=3;
        this.add(in_nomqcm,gbc);
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        this.add(consigne,gbc);
        gbc.gridx=1;
        gbc.gridy=5;
        gbc.gridheight=1;
        gbc.gridwidth=3;
        this.add(in_consigne,gbc);
        gbc.gridx=0;
        gbc.gridy=6;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        this.add(classe,gbc);
        gbc.gridx=1;
        gbc.gridy=6;
        gbc.gridheight=1;
        gbc.gridwidth=3;
        this.add(choix_classe,gbc);
        gbc.gridx=0;
        gbc.gridy=7;
        gbc.gridheight=1;
        gbc.gridwidth=1;        
        this.add(date_debut,gbc);
        gbc.gridx=1;
        gbc.gridy=7;
        gbc.gridheight=1;
        gbc.gridwidth=3;
        this.add(in_datedebut,gbc);
        gbc.gridx=0;
        gbc.gridy=8;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        this.add(date_fin,gbc);
        gbc.gridx=1;
        gbc.gridy=8;
        gbc.gridheight=1;
        gbc.gridwidth=3;
        this.add(in_datefin,gbc);
        gbc.gridx=0;
        gbc.gridy=9;
        gbc.gridheight=1;
        gbc.gridwidth=1;       
        this.add(conformite,gbc);
        gbc.gridx=1;
        gbc.gridy=9;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        this.add(stricte,gbc);
        gbc.gridx=2;
        gbc.gridy=9;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        this.add(relative,gbc);
      // ajout d'un element invisible pour centrer en haut
        gbc.gridx=0;
        gbc.gridy=10;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        gbc.weighty=1;
        gbc.weightx=1;
        JLabel fe= new JLabel("");
        this.add(fe,gbc);
        
        
        this.setVisible(true);
       
    }
    
    // jscrollpane
}
