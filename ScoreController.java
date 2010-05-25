import java.io.*;
import java.util.*;
import javax.swing.*;
import java.text.*;

public class ScoreController {
	static BufferedReader read;
	static BufferedWriter write;
	static String name;

	private static ArrayList<Score> getScores() {
		ArrayList<Score> list = new ArrayList<Score>();
		try {
			read = new BufferedReader(new FileReader("score.dat"));
			int lines = Integer.parseInt(read.readLine());
			System.out.println("Reading score.dat…read "+lines+" lines.");
			for(int i = 0; i < lines; i++) {
				StringTokenizer st = new StringTokenizer(read.readLine());
				list.add(new Score(Double.parseDouble(st.nextToken()), Long.parseLong(st.nextToken())));
			}
			Collections.sort(list);
			name = read.readLine();
		} catch(IOException e) { 
			System.out.println("read fail"); 
		}
		return list;
	}
	
	public static void writeScore(Double score) {
		try {
			ArrayList<Score> list = getScores();
			list.add(new Score(score));
			Collections.sort(list);
			System.out.println("printing scores list: ");
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
			write = new BufferedWriter(new FileWriter("score.dat"));
			write.write(list.size()+"\n");
			for(int i = 0; i < list.size(); i++) {
				write.write(list.get(i).score+" "+list.get(i).time+"\n");
			}
			write.write(name+"\n");
			write.close();
		} catch(IOException e) { 
			System.out.println("write fail"); 
		}
	}
	
	public static void showScorePanel() {
		ArrayList<Score> scores = getScores();
		String display = "The high scores for "+name+" are:\n\n";
		try {
			for(int i = 0; i < Math.min(5, scores.size()); i++) {
				display += scores.get(i).score+" "+new SimpleDateFormat("MM/dd/yyyy").format(new Date(scores.get(i).time))+"\n";
			}
		} catch(IndexOutOfBoundsException e) {}
		JOptionPane.showMessageDialog(null, display, "LOLsweeper", JOptionPane.INFORMATION_MESSAGE);
	}
}

