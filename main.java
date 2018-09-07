/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class main extends JFrame {

    JPanel contentPane;
    JToolBar toolBar = new JToolBar();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    ImageIcon image1;
    ImageIcon image2;
    ImageIcon image3;
    BorderLayout borderLayout1 = new BorderLayout();
    net jPanel1 = new net();
    JButton clustView = new JButton();

    //Construct the frame
    public main() {
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
	image1 = new ImageIcon(main.class.getResource("./img/openFile.gif"));
	image2 = new ImageIcon(main.class.getResource("./img/closeFile.gif"));
	image3 = new ImageIcon(main.class.getResource("./img/help.gif"));
	contentPane = (JPanel) this.getContentPane();
	contentPane.setLayout(borderLayout1);
	this.setSize(new Dimension(400, 300));
	this.setTitle("Data classifier");
	jButton1.setIcon(image1);
	jButton1.addActionListener(new java.awt.event.ActionListener() {

		public void actionPerformed(ActionEvent e) {
		    jButton1_actionPerformed(e);
		}
	    });
	jButton1.setToolTipText("Open File");
	jButton2.setIcon(image2);
	jButton2.setToolTipText("Close File");
	jButton3.setIcon(image3);
	jButton3.setToolTipText("Help");
	clustView.setText("clusters");
	clustView.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    clustView_actionPerformed(e);
		}
	    });
	toolBar.add(jButton1);
	toolBar.add(clustView, null);
	//  toolBar.add(jButton2);
	//  toolBar.add(jButton3);
	contentPane.add(jPanel1, BorderLayout.CENTER);
	contentPane.add(toolBar, BorderLayout.NORTH);
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
	super.processWindowEvent(e);
	if (e.getID() == WindowEvent.WINDOW_CLOSING) {
	    System.exit(0);
	}
    }

    void jButton1_actionPerformed(ActionEvent e) {
	new GUIconnect(jPanel1);
    }

    void clustView_actionPerformed(ActionEvent e) {
	new graphes(jPanel1.gn ,jPanel1.entete ).start() ;
    }
}
