/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import javax.swing.JWindow;

import java.applet.*;
import java.util.*;
import java.awt.*;
import java.net.*;
import java.io.*;


public class graphes extends Frame implements Runnable{
    geoNeuron[] gn;
    String entete[];
    public Thread  animator = null;


    public graphes(geoNeuron[] gn, String header[]) {
	entete = header;
	this.gn = gn;
	this.setSize(400,200);
	this.setVisible(true);
    }

    public void paint(Graphics g){
	if (animator != null){
	    g.setColor(new Color(0,0,0));
	    g.fillRect(0, 0, this.getWidth() , this.getHeight() );
	    int larg = this.getWidth() / (gn[0].size()-1);
	    for(int x=0; x<9; x++){
		for(int y=0; y<(gn[0].size()-1); y++){
		    g.setColor(new Color((int)(x*20), (int)(250-x*20),(int)(gn[x].getSN(2)*250)));
		    g.drawLine(y*larg,(int)(gn[x].getSN(y)*this.getHeight()) ,(y+1)*larg,(int)(gn[x].getSN(y+1)*this.getHeight()));
		    g.setColor(new Color(250,250,250));
		    g.drawString(entete[y+1],(y*larg)-10,this.getHeight()-30 );
		}
	    }
	}
	try {Thread.sleep(400);} catch (InterruptedException e){};
    }

    public void run() {
	repaint();
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
}
