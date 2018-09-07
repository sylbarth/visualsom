/*********************************************************************
 * Project: Visual Self Organizing Maps
 * Author : Sylvain BARTHELEMY (sylbarth@gmail.com)
            and Jean-Baptiste FILIPPI (filippi@univ-corse.fr)
 *********************************************************************/

package visualsom;

import javax.swing.UIManager;

public class mapClassifier {
    boolean packFrame = false;

    //Construct the application
    public mapClassifier() {
	main frame = new main();
	//Validate frames that have preset sizes
	//Pack frames that have useful preferred size info, e.g. from their layout
	if (packFrame) {
	    frame.pack();
	}
	else {
	    frame.validate();
	}
	frame.setVisible(true);
    }

    //Main method
    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
	new mapClassifier();
    }
}
