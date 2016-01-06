/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiciel_qcm;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class BD {
    private java.sql.Connection cnx;
    private java.sql.Statement lien;
    private String nom = "base_donnees.sqlite";
    
    public BD() {
        // Chargement du pilote en mémoire
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            cnx = DriverManager.getConnection("jdbc:sqlite:" + nom);
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public java.sql.ResultSet SELECT(String commande) throws SQLException {
        try {
            lien = cnx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        java.sql.ResultSet resultatRequete;
        resultatRequete = lien.executeQuery(commande);        
        return resultatRequete;
    }
    
    public int ecrire(String commande) throws SQLException {
        try {
            lien = cnx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lien.executeUpdate(commande);
    }
    
    public void terminerRequete() throws SQLException {
        lien.close();
    }
    
    public void fermerBase() throws SQLException {
        cnx.close();
    }
}
