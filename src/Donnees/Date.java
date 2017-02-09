package Donnees;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Yanon
 */
public class Date {
    String annee;//yyyy
    String mois;//mm
    String jours;//jj
    String heure;//hh

    public Date(String annee, String mois, String jours, String heure) {
        this.annee = annee;
        this.mois = mois;
        this.jours = jours;
        this.heure = heure;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getJours() {
        return jours;
    }

    public void setJours(String jours) {
        this.jours = jours;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
    
    
}
