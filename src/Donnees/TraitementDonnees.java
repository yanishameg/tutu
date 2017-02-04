/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Donnees;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Yanon
 *  
 */
public class TraitementDonnees {
    static String Nomfichierdonnee="donneeglob.csv"; 
    static String Nomfichierdonneetemp="donneetemp.csv";
    static String Nomfichierdonneecomp="donneecomp.csv.gz";
    static String Nomfichierdonneecomptemp="donneecomptemp.csv.gz";
    public static void Telechargement(String date) {

        try {
            URL website = new URL("https://donneespubliques.meteofrance.fr/donnees_libres/Txt/Synop/Archive/synop."+date+".csv.gz");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(Nomfichierdonneecomptemp);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        } catch (MalformedURLException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    public static void TelechargementAnnee (String annee){
    
        String mois ;
    
        for (int i = 1; i <= 12; i++) {
            try {
                //Telechargement("2015"+());
                mois = 2015+("00"+i).substring("i".length());
                //System.out.println("date:"+2015+("00"+i).substring("i".length()));
                Telechargement(mois);
                Decompresser(new File(Nomfichierdonneecomptemp), true);
                
            } catch (IOException ex) {
                Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }
    
    /**
     * 
     * @param file1 le petit fichier
     * @param file2 le grand fichier
     * @param date
     */
    public static void mergefiles (String file1,String file2,String date){
        String line,dateTemp;
        String tabDonnee[];
        try {
            File f1;           
            FileReader fr1;
            BufferedReader br1;
            
            File f2;
            FileReader fr2;
            BufferedReader br2;
            
            File f3;
            FileWriter fw3;
            BufferedWriter bw3;
            
            f1 = new File(file1);
            fr1 = new FileReader (f1);
            br1 = new BufferedReader(fr1);
            
            f2  = new File(file2);
            fr2 = new FileReader (f2);
            br2 = new BufferedReader(fr2);
            
            
            f3 = new File("temp_"+file2);
            fw3= new FileWriter (f3);
            bw3 = new BufferedWriter(fw3);
            
            line = br2.readLine();
            
            line = br2.readLine();
            tabDonnee = line.split(";");
            dateTemp = tabDonnee[1].substring(0, 6);
            
            while (line != null) {
                if (Integer.parseInt(date)>Integer.parseInt(dateTemp)) {
                    bw3.write(line+"\n");
                }
                line = br2.readLine();
                tabDonnee = line.split(";");
                dateTemp = tabDonnee[1].substring(0, 6);

            }
            
        } catch (IOException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    public static File Decompresser(File infile, boolean deleteGzipfileOnSuccess) throws IOException {
        GZIPInputStream gin = new GZIPInputStream(new FileInputStream(infile));
        FileOutputStream fos = null;
        try {
            File outFile = new File(infile.getParent(), infile.getName().replaceAll("\\.gz$", ""));
            fos = new FileOutputStream(outFile);
            byte[] buf = new byte[100000];
            int len;
            while ((len = gin.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }

            fos.close();
            if (deleteGzipfileOnSuccess) {
                infile.delete();
            }
            return outFile;
        } finally {
            if (gin != null) {
                gin.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
