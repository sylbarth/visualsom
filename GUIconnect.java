/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class GUIconnect extends Frame{

    URL codebas;
    Panel BdPanel = new Panel();
    Label jLabel1 = new Label();
    Button BBdvalid = new Button();
    TextField BconnectString = new TextField();
    net n;
    Label jLabel3 = new Label();
    public GUIconnect(net n) {
	this.n =n;
	this.setLocale(Locale.FRANCE);

	try {
	    jbInit();
	}
	catch(Exception e) {
	    e.printStackTrace();
	}


	this.show();
    }
    public GUIconnect(net n, URL Codebase) {
	codebas = Codebase;
	this.n =n;
	this.setLocale(Locale.FRANCE);

	try {
	    jbInit();
	}
	catch(Exception e) {
	    e.printStackTrace();
	}


	this.show();
    }

    private void jbInit() throws Exception {
	this.setBounds(200,200,420,60);
	this.setSize(420,60) ;
	jLabel1.setText("URL fichier");
	jLabel1.setBounds(new Rectangle(10, 4, 69, 17));
	BdPanel.setLayout(null);
	BBdvalid.setLabel("OK");
	BBdvalid.setBounds(new Rectangle(343, 1, 54, 24));
	BBdvalid.addActionListener(new java.awt.event.ActionListener() {

		public void actionPerformed(ActionEvent e) {
		    BBdvalid_actionPerformed(e);
		}
	    });
	BconnectString.setText("http://localhost/test.txt");
	BconnectString.setBounds(new Rectangle(79, 2, 256, 21));

	//jLabel3.setText(System.getProperty("java.version") + " " + System.getProperty("java.vm.specification.name")+ " " + System.getProperty("java.vm.version"));

	jLabel3.setBounds(new Rectangle(9, 22, 286, 27));

	this.add(BdPanel, BorderLayout.CENTER);

	BdPanel.add(jLabel1, null);
	BdPanel.add(BconnectString, null);
	BdPanel.add(BBdvalid, null);
	BdPanel.add(jLabel3, null);
    }


    void BBdvalid_actionPerformed(ActionEvent e) {
	n.kohonenInit(BconnectString.getText(), codebas);
	this.dispose() ;
    }

}
