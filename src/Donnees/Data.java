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
public class Data {
    Station station;
    double temperature;
    int humidite;
    float nebulosite;
    Date date;

    public Data(Station station, double temperature, int humidite, float nebulosite, Date date) {
        this.station = station;
        this.temperature = temperature;
        this.humidite = humidite;
        this.nebulosite = nebulosite;
        this.date = date;
    }

    
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidite() {
        return humidite;
    }

    public void setHumidite(int humidite) {
        this.humidite = humidite;
    }

    public float getNebulosite() {
        return nebulosite;
    }

    public void setNebulosite(float nebulosite) {
        this.nebulosite = nebulosite;
    }
    
}
