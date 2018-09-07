/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import java.applet.*;
import java.util.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class net extends JComponent implements Runnable {

    public int W ;
    public int H ;
    public int NGEONEURON;
    public geoNeuron gn[];
    public static final double COUNTRY = 1.00;
    public String entete[];
    URL codebas;
    public static final Color bkC = new Color(255,255,255);

    public int imagewidth ,imageheight;
    public Thread  animator    = null;
    String db,tb;
    public boolean please_stop = false;
    Font mF = new Font("Courier", Font.BOLD, 12);
    Font sF = new Font("Courier", Font.BOLD, 8);
    public int counter;



    Fileconnect data;
    public double r[][];

    public double theta, phi, momentum;

    public  net() {

    }

    ///////////////////////////////////////////////////////////////////
    //
    //  Init section
    //
    ///////////////////////////////////////////////////////////////////

    public void kohonenInit(String db, URL codebas){

	theta = 0.5;
	phi   = 0.5;
	momentum = 0.999;

	this.codebas = codebas;
        this.db = db;


	data = new Fileconnect(db,codebas) ;

	H = (int)Math.sqrt(data.getTableLength());
	W = H;

	NGEONEURON = H * W;

	Vector bank = data.getData();
	gn = new geoNeuron[NGEONEURON];
	System.out.println(H + " " + W);
	for(int x = 0; x<W; x++)
	    for(int y = 0; y<H; y++){
		gn[x*W+y] = (geoNeuron)bank.elementAt(x*W+y);
	    }
	entete = data.getHeader() ;

	r = new double[NGEONEURON][NGEONEURON];
	makeR(theta);
	counter = 0;
	start();
    }

    ///////////////////////////////////////////////////////////////////
    //
    //  Problem section
    //
    ///////////////////////////////////////////////////////////////////

    public void makeR(double th){

	for(int i=0; i<NGEONEURON; i++){
	    r[i][i]= 1.0;
	    for(int j=i+1; j<NGEONEURON; j++){
      		r[i][j] = Math.exp( -1.0 * ( gn[i].dist(gn[j])*gn[i].dist(gn[j]) )/(2.0*th*th));
      		r[j][i] = r[i][j];
	    }
	}
    }

    // The body of the animator thread.
    public void run() {

    	int j;
    	double x1,x2,mindist;
    	int count = 0,clustcount = 0;

        while(counter < 100000) {

            counter++;

            // CHOSE A RANDOM PATTERN
            x1 = Math.random()* COUNTRY;
            x2 = Math.random()* COUNTRY;

            // SEARCH FOR MINIMAL
	    mindist = 100000.0;
            j = -1;

	    for(int i=0; i<NGEONEURON;i++){
            	double d = (x1 - gn[i].wx)*(x1 - gn[i].wx) + (x2 - gn[i].wy)*(x2 - gn[i].wy);
            	if(d < mindist){
		    mindist = d;
		    j = i;
            	}
            }


            gn[j].update++;
            gn[j].choose++;

            // UPDATE WEIGHTS
            for(int i=0; i<NGEONEURON;i++){
            	gn[i].wx += (phi * r[i][j] * (x1 - gn[i].wx));
            	gn[i].wy += (phi * r[i][j] * (x2 - gn[i].wy));
            }

            // DECREASE LEARNING PARAMETERS
	    phi *= momentum;
	    theta *= momentum;

            // RE-COMPUTE r MATRIX
	    makeR(theta);

       	    // PLOT RESULT EVERY 40 SESSIONS
	    count = (count++)%200;
	    if(count==0){
		repaint();
            	try {Thread.sleep(4);} catch (InterruptedException e){};
	    }

            // classe tout les 400
	    count = (count++)%400;
	    if(count==0){
                java.util.Arrays.sort(this.gn);

            	try {Thread.sleep(4);} catch (InterruptedException e){};
	    }

	}
	animator = null;
    }

    ///////////////////////////////////////////////////////////////////
    //
    //  Functional section
    //
    ///////////////////////////////////////////////////////////////////


    private int toXReal(double val){
	int w = this.getSize().width;
	return (int)(val *((double)w-50.0) / COUNTRY +25.0);
    }

    private int toYReal(double val){
	int h = this.getSize().height;
	return (int)(val *((double)h-50.0) / COUNTRY +25.0);
    }

    public void paint(Graphics g) {

        Dimension size = this.getSize();
	int w = size.width, h = size.height;
	g.setFont(mF);

	// CLEAR ALL
	g.setColor(bkC);
	g.fillRect(0, 0, w, h);
        // draw neurons
        if (animator != null){
            for(int x=0; x<NGEONEURON; x++){
		g.setColor(new Color((int)(gn[x].getSN(0)*250), (int)(gn[x].getSN(1)*250),(int)(gn[x].getSN(2)*250)));
		g.fillOval( toXReal(gn[x].wx)-2, toYReal(gn[x].wy)-2,20,20);
            }
            for(int x=0; x<8; x++){
		g.setColor(new Color(0,0,0));
		g.fillOval( toXReal(gn[x].wx)-4, toYReal(gn[x].wy)-4,24,24);
		g.setColor(new Color((int)(x*20), (int)(250-x*20),(int)(gn[x].getSN(2)*250)));
		g.fillOval( toXReal(gn[x].wx)-2, toYReal(gn[x].wy)-2,20,20);
            }
	}
    }

    // Start the animation
    public void start() {
        animator = new Thread(this);
        animator.start();
    }
    // Stop it.
    public void stop() {
        if (animator != null) animator.interrupt() ;
        animator = null;
    }


    // Stop and start animating on mouse clicks.
    public boolean mouseDown(Event e, int x, int y) {
	// if running, stop it.  Otherwise, start it.
	if (animator != null){
	    please_stop = true;
	}else{
	    please_stop = false;
	    animator = new Thread(this);
	    kohonenInit(db,codebas);
	    animator.start();
	}
	return true;
    }
}
