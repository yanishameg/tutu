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
public class Station {
    String idStation;
    String nomStation;

    public Station(String idStation, String nomStation) {
        this.idStation = idStation;
        this.nomStation = nomStation;
    }

    
    
    public String getIdStation() {
        return idStation;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }
    
}
