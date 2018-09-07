/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;

public class SomApplet extends JApplet {




    Panel toolBar = new Panel();
    Button jButton1 = new Button();
    Button jButton2 = new Button();
    Button jButton3 = new Button();

    BorderLayout borderLayout1 = new BorderLayout();
    net jPanel1 = new net();
    Button clustView = new Button();

    //Construct the frame
    public SomApplet() {
	enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	try {
	    jbInit();
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
    }

    //Component initialization
    private void jbInit() throws Exception  {


	this.setSize(new Dimension(400, 300));

	jButton1.addActionListener(new java.awt.event.ActionListener() {

		public void actionPerformed(ActionEvent e) {
		    jButton1_actionPerformed(e);
		}
	    });

	jButton2.setLabel("ouvrir") ;
	clustView.setLabel("clusters");
	clustView.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    clustView_actionPerformed(e);
		}
	    });
	//this.setLayout();
	jButton1.setLabel("ouvrir");
	getContentPane().add(jPanel1, BorderLayout.CENTER);
	getContentPane().add(toolBar, BorderLayout.NORTH);
	toolBar.add(jButton1);
	toolBar.add(clustView, null);
    }

    //File | Exit action performed
    public void fileExit_actionPerformed(ActionEvent e) {
	System.exit(0);
    }

    //Help | About action performed
    public void helpAbout_actionPerformed(ActionEvent e) {
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
	// super.processWindowEvent(e);
	if (e.getID() == WindowEvent.WINDOW_CLOSING) {
	    System.exit(0);
	}
    }

    void jButton1_actionPerformed(ActionEvent e) {
	new GUIconnect(jPanel1, this.getCodeBase() );
    }


    void clustView_actionPerformed(ActionEvent e) {
	new graphes(jPanel1.gn, jPanel1.entete  ).start() ;
    }
}
