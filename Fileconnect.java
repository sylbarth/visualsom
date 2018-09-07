/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;


/**
 * Classe de connection a un fichier texte
 */

 public class Fileconnect{

     BufferedReader in;
     String nomFichier;
     URL stockURL;

     int nbCol;
     int longueur =0;
     double min[],max[];
     String entete[];

     /**
      *  Constructeur,
      *   fileName est le nom et le chemin d'accés complet du fichier d'entrée
      *   il initialise le nom des colonnes et ouvre l'accés au fichier
      */

     public Fileconnect(String fileName, URL codebas){
	 System.out.println(codebas) ;
	 try{

	     stockURL = new URL(codebas, fileName);
	     System.out.println(stockURL) ;
	     StringTokenizer str;

	     in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
	     str = new StringTokenizer(in.readLine());
	     nbCol = str.countTokens()-1;
	     entete = new String[nbCol+1];
	     int e = 0;
	     while(str.hasMoreTokens()) entete[e++] = str.nextToken();
	     min = new double[nbCol];
	     max = new double[nbCol];
	     for(int i = 0; i < nbCol ;i++){
		 max[i] = -1000000;
		 min[i] = 1000000;}
	     double tmp;
	     while(longueur < 120) {
		 str = new StringTokenizer(in.readLine());
		 str.nextToken();

		 for(int i = 0; i < nbCol ;i++){
		     tmp = new Double(str.nextToken()).doubleValue() ;
		     if (tmp  > max[i]) max[i]  = tmp;
		     if (tmp  < min[i]) min[i]  = tmp;
		 }
		 longueur++;
	     }
	     System.out.println(nbCol + "         "+ longueur + "   " + min[3] + "    "+ max[5]) ;
	     for(int i = 0; i < nbCol-1;i++)  System.out.print(min[i] + ";" + max [i]+ "<<");
	     System.out.println();
	     in.close();
	 }
	 catch(FileNotFoundException e){System.err.println("erreur a l'ouverture du fichier" + e.toString());}
	 catch(MalformedURLException ee){System.err.println("erreur a l'ouverture du url" + ee.toString());}
	 catch(IOException eee){System.err.println("erreur a l'initialisation du fichier" + eee.toString());}
     }

     public Vector getData(){
	 Vector v = new Vector();

	 StringTokenizer str;
	 try{  in = new BufferedReader(new InputStreamReader(stockURL.openStream()));}
	 catch(FileNotFoundException e){System.err.println("erreur a l'ouverture du fichier" + e.toString());}
	 catch(MalformedURLException ee){System.err.println("erreur a l'ouverture du url" + ee.toString());}
	 catch(IOException eee){System.err.println("erreur a l'initialisation du fichier" + eee.toString());}
	 try{
	     str = new StringTokenizer(in.readLine());
	     for(int i = 0; i < longueur-1; i++){

		 String nom = new String();
		 str = new StringTokenizer(in.readLine());
		 nom = str.nextToken();

		 v.add(new geoNeuron(nom,makeGeo(str),min,max));
	     }
	     in.close() ;
	 }catch(IOException ee){System.err.println("erreur de lecture du fichier" + ee.toString());}
	 return v;
     }
     public String[] getHeader(){return entete;}
     public int getTableWidth(){return this.nbCol;}
     public int getTableLength(){return this.longueur;}
     public Vector makeGeo(StringTokenizer str){
	 Vector t = new Vector();
	 while(str.hasMoreTokens()) t.add(new Double(str.nextToken()));
	 return t;
     }
 }
