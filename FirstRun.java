import java.io.*;
import javax.swing.*;

public class FirstRun {
	public FirstRun() {
		File score = new File("score.dat");
		BufferedReader in;
		BufferedWriter out;
		String name;
		try { 
			in = new BufferedReader(new FileReader("stuff/instructions.txt")); 
			int lines = Integer.parseInt(in.readLine());
			if(!score.exists()) {
				for(int i = 0; i < lines; i++)
					JOptionPane.showMessageDialog(null, in.readLine(), "LOLsweeper", JOptionPane.INFORMATION_MESSAGE);
				name = JOptionPane.showInputDialog(null, "Oh, by the way, what is your name?", "LOLsweeper", JOptionPane.INFORMATION_MESSAGE);
				out = new BufferedWriter(new FileWriter("score.dat"));
				out.write("0\n"+name+"\n");
				out.close();
			}
		}
		catch(IOException e) {}
	}
}
