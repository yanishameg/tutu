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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Yanon
 *
 */
public class TraitementDonnees {

    /*
    static String Nomfichierdonnee = "donneeglob.csv";
    static String Nomfichierdonneetemp = "donneetemp.csv";
    static String Nomfichierdonneecomp = "donneecomp.csv.gz";
    static String Nomfichierdonneecomptemp = "donneecomptemp.csv.gz";
     */

    public static void Telechargement(String date) {

        try {
            URL website = new URL("https://donneespubliques.meteofrance.fr/donnees_libres/Txt/Synop/Archive/synop." + date + ".csv.gz");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("data/" + date + ".csv.gz");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        } catch (MalformedURLException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getApplicationPath() throws IOException {
        return (new File(".").getCanonicalPath());
    }

    public static void TelechargementAnnee(String annee) {

        String mois;

        for (int i = 1; i <= 12; i++) {
            try {
                //Telechargement("2015"+());
                if (i < 10) {
                    mois = annee + ("00" + i).substring("i".length());
                } else {
                    mois = annee + String.valueOf(i);
                }

                System.out.println("mois:" + mois);
                Telechargement(mois);

                Decompresser(new File("data/" + mois + ".csv.gz"), true);

            } catch (IOException ex) {
                Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // deleteGzipFiles();
    }

    
    public static ArrayList<Data> getDataForDateByStation(String date, String nomStation) {
        ArrayList<Data> liste;
        FileReader fr = null;
        try {

            String idStation = getStationIdByName(nomStation);
            
            String path = "data/" + date.substring(0, 6) + ".csv";
            File file = new File(path);
            if (!file.exists()||idStation==null) {
                //file n'existe pas ou numero station ne peu pas etre trouvé
                return null;
            }

            liste = new ArrayList<Data>();

            Pattern pattern = Pattern.compile(date + ".*");

            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line, dateL;
            String[] splited;

            line = br.readLine();
            line = br.readLine();
            double temperature;
            int humidite;
            float nebulosite;
            Date maDate;
            String idStation2;
            Station station;
            while (line != null) {
                splited = line.split(";");
                dateL = splited[1];
                idStation2 = splited[0];
                Matcher matcher = pattern.matcher(dateL);
                if(idStation.equals(idStation2)){
                    if (matcher.find()) {
                        temperature = splited[7].equals("mq") ? 999 : Double.parseDouble(splited[7]);
                        humidite = splited[9].equals("mq") ? 999 : Integer.parseInt(splited[9]);
                        nebulosite = splited[14].equals("mq") ? 999 : Float.parseFloat(splited[14]);

                        maDate = new Date(dateL.substring(0,4),
                                            dateL.substring(4,6),
                                            dateL.substring(6,8),
                                            dateL.substring(8,10));

                        station = new Station(idStation,nomStation);
                        liste.add(new Data(station, temperature, humidite, nebulosite, maDate));
                    }
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
            return liste;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    public static String getStationIdByName(String nomStation) {
        File file = new File("configuration.csv");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            line = br.readLine();
            String nomStat;
            while(line!=null) {
                nomStat = line.split(";")[1];
                System.out.println("nomstation"+nomStat);
                if(nomStat.equals(nomStation)) {
                    return (line.split(";")[0]);
                }
                line = br.readLine();
            }
            return null;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TraitementDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "haja";
    }

    public static void deleteGzipFiles() {
        File file1 = new File("data");
        Pattern pattern = Pattern.compile(".*.csv.gz");

        if (file1.isDirectory()) {
            for (File file : file1.listFiles()) {
                Matcher match = pattern.matcher(file.getName());
                if (match.matches()) {
                    if (file.delete()) {
                        System.out.println("file deleted");
                    } else {
                        System.out.println("file could not be deleted");
                    }
                }
            }
        }
    }

    /**
     *
     * @param file1 le petit fichier
     * @param file2 le grand fichier
     * @param date
     */
    public static void mergefiles(String file1, String file2, String date) {
        String line, line2, dateTemp;
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
            fr1 = new FileReader(f1);
            br1 = new BufferedReader(fr1);

            f2 = new File(file2);
            fr2 = new FileReader(f2);
            br2 = new BufferedReader(fr2);

            f3 = new File("temp_" + file2);
            fw3 = new FileWriter(f3);
            bw3 = new BufferedWriter(fw3);

            line = br2.readLine();

            line = br2.readLine();
            tabDonnee = line.split(";");
            dateTemp = tabDonnee[1].substring(0, 6);

            while (line != null) {
                if (Integer.parseInt(date) > Integer.parseInt(dateTemp)) {
                    bw3.write(line + "\n");
                }

                line = br2.readLine();
                tabDonnee = line.split(";");
                dateTemp = tabDonnee[1].substring(0, 6);

            }

            line = br1.readLine();

            line = br1.readLine();

            while (line != null) {
                bw3.write(line + "\n");

                line = br1.readLine();
            }

            line2 = br2.readLine();

            while (line2 != null) {
                bw3.write(line + "\n");

                line2 = br2.readLine();
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
            gin.close();
            if (deleteGzipfileOnSuccess) {
                System.out.println("deleting path:" + infile.getAbsolutePath());
                if (!infile.delete()) {
                    System.out.println("can't delete");
                }

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
