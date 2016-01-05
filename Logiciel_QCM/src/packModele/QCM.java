/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packModele;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class QCM {
    private int id_qcm;
    private String nom;
    private String description;
    private String classe;
    private Date date_debut;
    private Date date_fin;
    private boolean conformite_relative;
    
    private ArrayList<Question> questions;
}
