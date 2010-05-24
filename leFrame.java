import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.apple.eawt.*;

public class leFrame extends JFrame {
	MinePanel p = new MinePanel(800, 800);
	JPanel controls = new JPanel();
	JPanel mainMenu = new JPanel();
	static final JMenuBar bar = new JMenuBar();
	JMenuItem newItem, openItem, saveItem, showScoresItem, helpItem;
	JMenu file = new JMenu("File");
	JMenu help = new JMenu("Help");
	JTextField tf = new JTextField(5);
	JTextField moneytf = new JTextField(5);
	JTextField timetf = new JTextField(5);
	PrefPane prefs = new PrefPane();
	public leFrame() {
		setSize(800, 850);
		setTitle("Macs Against PCs");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setPreferredSize(new Dimension(800, 800));
		controls.setPreferredSize(new Dimension(800, 50));

//		System.setProperty("com.apple.macos.useScreenMenuBar", "true");
//		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Macs Against PCs");

		addMenus();
		setJMenuBar(bar);

/*		if(System.getProperty("os.name").contains("Mac")) {
			System.out.println("mac os x");
			Application fApplication = Application.getApplication();
			fApplication.setEnabledPreferencesMenu(true);
			fApplication.addApplicationListener(new ApplicationAdapter() {
				public void handlePreferences(ApplicationEvent e) {
					prefs.setVisible(true);
				}
			});
		}

		JButton button = new JButton("New Game");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.restart();
				tf.setText(p.getProgress());
				moneytf.setText(new Double(p.getMoney()).toString());
			}
		});
		controls.add(button);
*/		tf.setText(p.getProgress());
		moneytf.setText(new Double(p.getMoney()).toString());
		controls.add(new JLabel("Progress"));
		tf.setEditable(false);
		
		controls.add(tf);
		controls.add(new JLabel("Cash Remaining"));
		moneytf.setEditable(false);
		controls.add(moneytf);

		timetf.setEditable(false);
		controls.add(new JLabel("Time"));
		
		p.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				tf.setText(p.getProgress());
				moneytf.setText(new Double(p.getMoney()).toString());				
			}
		});
		
		Container cp = getContentPane();
		cp.add(p, BorderLayout.SOUTH);
		cp.add(controls, BorderLayout.NORTH);
		pack();
		setVisible(true);
	}
	
	private void addMenus() {
		newItem = new JMenuItem("New Game", KeyEvent.VK_N);
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		file.add(newItem);
		openItem = new JMenuItem("Load Game", KeyEvent.VK_O);
//		file.add(openItem);
		file.addSeparator();
		saveItem = new JMenuItem("Save Game");
//		file.add(saveItem);
//		file.addSeparator();
		showScoresItem = new JMenuItem("Show Scores");
		file.add(showScoresItem);
		bar.add(file);
		
		helpItem = new JMenuItem("LOLsweeper Help");
		help.add(helpItem);
		bar.add(help);
		
		newItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.restart();
				tf.setText(p.getProgress());
				moneytf.setText(new Double(p.getMoney()).toString());
			}
		});
		
		showScoresItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScoreController.showScorePanel();
			}
		});
	}
}
