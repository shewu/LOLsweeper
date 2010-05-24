import javax.swing.*;

public class main {
	public static void main(String args[]) {
		if(System.getProperty("os.name").contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		new FirstRun();
		new leFrame();
	}
}

