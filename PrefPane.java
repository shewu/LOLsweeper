import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class PrefPane extends JFrame {
	JSlider mineControl = new JSlider();
	public PrefPane() {
		try {
			BufferedReader prefs = new BufferedReader(new FileReader("prefs.dat"));
			BufferedWriter out = new BufferedWriter(new FileWriter("prefs.dat"));			
		}
		catch(IOException e) {}
		setTitle("Preferences");
		setSize(400, 300);
/*		mineControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
			}
		});
*/		add(mineControl);
		setVisible(false);
	}
}
