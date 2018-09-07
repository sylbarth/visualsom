/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import java.util.Vector;

public class geoNeuron extends Vector implements java.lang.Comparable {

    public double[] min,max;
    public double wx,wy;
    public int update,choose,clust;
    public String nom;

    public geoNeuron(String name, Vector v, double[] min, double[] max){
	this.addAll(v);
	this.max = max;
	this.min = min;
	this.nom = name;
	this.wx = Math.random() ;
	this.wy = Math.random() ;
	update = 0;
	choose = 0;
    }

    public double dist(geoNeuron c){
	double diff = 0;
	for (int i = 0; i < this.size() ; i++)
	    diff += Math.pow(getSN(i)-c.getSN(i),2);
	return Math.sqrt(diff);
    }


    public double wdist(geoNeuron c){
	double dx = this.wx - c.wx;
	double dy = this.wy - c.wy;
	return Math.sqrt(dx*dx + dy*dy);
    }

    public double getN(int pos){
	return ((Double)this.elementAt(pos)).doubleValue();
    }

    //renvoie l'element normé entre 0 et 1
    public double getSN(int pos){
	if ((getN(pos)/(double)max[pos])>1.0){
	    System.out.println(nom + "pos " + max[pos] + " position " +pos ) ;
	    return 1.0;
	}
	if (max[pos] > 0.0 )return getN(pos)/(double)max[pos];
	else return 0.0;
    }

    public String getName(){

	if (choose == 100) System.out.println(nom + " " + this );
	if (choose > 100)	return choose +" "+ nom;
	return "";
    }

    public String toString(){
	return nom +super.toString();
    }

    public int compareTo(Object o){
        return (((geoNeuron)o).choose  - this.choose );
    }
}
