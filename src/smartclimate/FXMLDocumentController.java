/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclimate;

import Donnees.Data;
import Donnees.TraitementDonnees;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Yanon
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      //  try {
            //TraitementDonnees.Telechargement("201502");
//           TraitementDonnees.TelechargementAnnee("2015");
//            File file = new File("file.csv.gz");
//            TraitementDonnees.TelechargementAnnee("2016");
          //  String selectedCity = choiceBox.getValue();
            ArrayList<Data> liste = TraitementDonnees.getDataForDateByStation("201605","BREST-GUIPAVAS");
            if(liste!=null) {
                for(Data data:liste) {
                    System.out.println("vil:"+data.getStation().getNomStation()+
                            " t:"+data.getTemperature()+
                            "date:"
                            +data.getDate().getAnnee()
                            +data.getDate().getMois()
                            +data.getDate().getJours()
                            +data.getDate().getHeure());
                }
            }
            else {
                System.out.println("null");
            }
//            TraitementDonnees.Decompresser(new File("file.csv.gz"),true);
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
    }    
    
}
