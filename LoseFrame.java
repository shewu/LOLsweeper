import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class LoseFrame extends JFrame {
	public LoseFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0,0,172));
		setFocusable(true);
		setResizable(false);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if(gd.isFullScreenSupported()) {
			gd.setFullScreenWindow(this);
		}
		Toolkit tk = Toolkit.getDefaultToolkit();
		setSize(tk.getScreenSize().width, tk.getScreenSize().height);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F8) {
					setVisible(false);
					GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
					gd.setFullScreenWindow(null);
				}
			}
		});
		JPanel bsod = new JPanel() {
			public void paintComponent(Graphics g) {
				Image img = new ImageIcon("stuff/bsod.jpg").getImage();
				g.drawImage(img, 0, 0, null);
			}
		};
		Container cp = getContentPane();
		cp.add(bsod);
		setVisible(true);
	}
}
