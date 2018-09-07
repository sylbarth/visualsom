/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import java.util.Vector;
import java.util.Date;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class BDConnection {

    static java.sql.Connection c;
    static Statement s;
    static ResultSet rs = null;
    String dbName,tableName;
    int nbCol;
    int longueur;
    double min[],max[];

    public BDConnection(String dbName, String tableName) {
	this.dbName = dbName;
	this.tableName = tableName;
	try{
	    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	    c = DriverManager.getConnection("jdbc:odbc:"+dbName);
	    s = c.createStatement();
	    rs = s.executeQuery("SELECT * FROM "+tableName+";");
	    nbCol = rs.getMetaData().getColumnCount() -1;
	    min = new double[nbCol];
	    max = new double[nbCol];
	    for(int i = 0; i < nbCol ;i++){
		max[i] = -10000;
		min[i] = 10000;}

	    double tmp;
	    for(longueur = 0; rs.next()  ;longueur++){
		for(int i = 2; i <= nbCol+1 ;i++){
		    tmp = rs.getDouble(i);
		    if (tmp  > max[i-2]) max[i-2]  = tmp;
		    if (tmp  < min[i-2]) min[i-2]  = tmp;
		}
	    }
	    rs.close() ;
	}catch (Exception e) {System.out.println(e) ;}
    }


    public Vector getData() {
	Vector v = new Vector();

	String nom;
	try{
	    rs = s.executeQuery("SELECT * FROM "+tableName+";");
	    while (rs.next()){
		Vector t = new Vector();
		nom = rs.getString(1);
		for (int i = 2;i <= nbCol+1; i++ ) t.add(new Double(rs.getDouble(i)));
		v.add(new geoNeuron(nom,t, min, max));
		t.clear();
	    }
	    rs.close();

	}catch (Exception e) {System.out.println(e) ;}
	return v;
    }

    public String getSourceName() {
	return this.dbName;
    }
    public int getTableWidth(){return this.nbCol;}

    public int getTableLength(){return this.longueur;}
}
