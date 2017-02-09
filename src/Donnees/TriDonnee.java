/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Donnees;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Yanon
 */
public class TriDonnee {
    

    public static File stof(String s){
     File f = new File(s);
     if (f.exists()) {
            String sf = f.toString();
            System.out.println("String representation of path is : " + s);
        } else {
            System.out.println("File cannot exists: ");
        }
     return f;
     }
    

    public static String filtostr(File f)
     { int x=1;
       double t;
    int u;
    double n;
         try {FileInputStream fstream = new FileInputStream(f);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine="";
            String ligne;
            while ((ligne =br.readLine()) != null) {
                
               String[] motsDeLaLigne = ligne.split(";");
                  
                 
                    
                     
                        /*if("mq".equals(motsDeLaLigne[7])){t=0;}else{
                          t=Double.parseDouble(motsDeLaLigne[7]);}
                         if("mq".equals(motsDeLaLigne[9])){u=0;}else{
                          u=Integer.parseInt(motsDeLaLigne[9]);}
                          if("mq".equals(motsDeLaLigne[14])){n=0;}else{
                          n=Double.parseDouble(motsDeLaLigne[14]);}*/
                         
                       strLine=strLine+motsDeLaLigne[1]+" "+motsDeLaLigne[7]+" "+motsDeLaLigne[9]+" "+motsDeLaLigne[14];
                        
                        
                     
                    
                  
            System.out.println("s= \n"+strLine+"\n");
            
            
               }
         
            
    in.close();
             return strLine;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        return "";
        }
         
     }
    public static void main(String[] args) {
        // TODO code application logic here
         String ff="C:\\Users\\Yanon\\Documents\\NetBeansProjects\\SmartClimate\file.CSV";
        File f=stof(ff);
        String s;
        
        filtostr(f);
       

}
}


