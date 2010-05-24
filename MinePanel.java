import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MinePanel extends JPanel {
	
	final boolean DEBUG = false;
	
	final double MINECOST = 19.5;
	final double SPACECOST = 2.5;
	
	final int NUMBOMBS = 40;
	
	int panelWidth;
	int panelHeight;
	int currentBombs;
	
	double money;
	
	int array[][];
	int draw[][];
	
	boolean didClick;
	boolean didFail;
	boolean didWin;
	boolean didWriteScore;
	
	BufferedImage fail = null;
	BufferedImage sure = null;
	BufferedImage unsure = null;
	
	public MinePanel(int w, int h) {
		setBackground(Color.white);
		panelWidth = w;
		panelHeight = h;
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(didFail || didWin) {
					return;
				}

				if(e.getButton() == 1) {
					processClick(e.getX()/50, e.getY()/50);
				} else if(e.getButton() == 3) {
					setQuestion(e.getX()/50, e.getY()/50);
				}

				if(DEBUG) {
					System.out.println("remaining $: "+money);
				}
			}
		});
		restart();
		try { 
			fail = ImageIO.read(new File("stuff/fail.png"));
			unsure = ImageIO.read(new File("stuff/unsure.png"));
			sure = ImageIO.read(new File("stuff/happymac.GIF"));
		} 
		catch(IOException e) {
			System.out.println("EXCEPTION: fail at drawing");
		}
	}
	
	public boolean saveGame() {
		File sav = new File("game.sav");
		return (sav.exists()) ? false : true;
	}
	
	public void restart() {
		didClick = didFail = didWin = didWriteScore = false;
		money = 899.;
		currentBombs = 0;
		array = new int[panelHeight/50][panelWidth/50];
		draw = new int[panelWidth/50][panelHeight/50];
		repaint();
	}
	
	public String getProgress() {
		if(didFail) {
			if(!didWriteScore) {
				ScoreController.writeScore(money-(NUMBOMBS-currentBombs)*MINECOST);
				didWriteScore = true;
			}
			JOptionPane.showMessageDialog(null, "YOU FIAL!", "LOLsweeper", JOptionPane.INFORMATION_MESSAGE);
			new LoseFrame();
			return "FAIL";
		}
		else if(didWin) {
			if(!didWriteScore) {
				ScoreController.writeScore(money);
				didWriteScore = true;
			}
			JOptionPane.showMessageDialog(null, "WINNER WINNER\nCHICKEN DINNER", "LOLsweeper", JOptionPane.INFORMATION_MESSAGE);
			return "WIN";
		}
		return currentBombs+"/"+NUMBOMBS;
	}
	
	public double getMoney() {
		return money;
	}
	
	private void setQuestion(int x, int y) {
		if(draw[x][y] == 0) {
			if(money < MINECOST) {
				if(DEBUG) {
					System.out.println("no more money!");
				}
				JOptionPane.showMessageDialog(null, "Not enough money!", "LOLsweeper", JOptionPane.ERROR_MESSAGE);
				return;
			}
			money -= MINECOST;
			draw[x][y] = -1;
			currentBombs++;
		}
		else if(draw[x][y] == -1) {
			draw[x][y] = -2;
			currentBombs--;
		}
		else if(draw[x][y] == -2) {
			draw[x][y] = 0;
		}
		repaint();
		if(DEBUG) {
			System.out.println("Progress: "+currentBombs+"/"+NUMBOMBS);
		}
		updateWin();
	}
	
	private void processClick(int x, int y) {
		if(x >= panelWidth/50 || y >= panelHeight/50 || x < 0 || y < 0) {
			return;
		}
		if(draw[x][y]==1) {
			return;
		}
		if(DEBUG) {
			System.out.println(x+" "+y);
		}
		if(money < SPACECOST) {
			if(DEBUG) {
				System.out.println("no more money!");
			}
			JOptionPane.showMessageDialog(null, "No more money!", "LOLsweeper", JOptionPane.ERROR_MESSAGE);
			didFail = true;
			return;
		}
		money -= SPACECOST;
		
		// TODO: light up clicked cell
		if(!didClick) {
			didClick = true;
			init(y, x);
		}
		if(array[y][x] == -1) {
			didFail = true;
			repaint();
			System.out.println("fail");
		}
		else if(array[y][x] == 0) {
			LinkedList<Point> q = new LinkedList<Point>();
			q.addLast(new Point(x, y));
			while(q.size() > 0) {
				Point p = q.remove();
				if(draw[p.x][p.y] == 1) 
					continue;
				draw[p.x][p.y] = 1;
				if(array[p.y][p.x] != 0) 
					continue;
				repaint();
				if(p.y>=1 && array[p.y-1][p.x] != -1) 
					q.addLast(new Point(p.x, p.y-1));
				if(p.y>=1 && p.x>=1 && array[p.y-1][p.x-1] != -1) 
					q.addLast(new Point(p.x-1, p.y-1));
				if(p.y>=1 && p.x+1<panelWidth/50 && array[p.y-1][p.x+1] != -1) 
					q.addLast(new Point(p.x+1, p.y-1));
				if(p.y+1<panelHeight/50 && array[p.y+1][p.x] != -1) 
					q.addLast(new Point(p.x, p.y+1));
				if(p.y+1<panelHeight/50 && p.x>=1 && array[p.y+1][p.x-1] != -1) 
					q.addLast(new Point(p.x-1, p.y+1));
				if(p.x+1<panelWidth/50 && p.y+1<panelHeight/50 && array[p.y+1][p.x+1] != -1) 
					q.addLast(new Point(p.x+1, p.y+1));
				if(p.x>=1 && array[p.y][p.x-1] != -1) 
					q.addLast(new Point(p.x-1, p.y));
				if(p.x+1<panelWidth/50 && array[p.y][p.x+1] != -1) 
					q.addLast(new Point(p.x+1, p.y));
				repaint();
			}
		}
		else {
			draw[x][y] = 1;
			repaint();
		}
		// check if won
		updateWin();
	}
	
	private void updateWin() {
		for(int i = 0; i < panelHeight/50; i++) {
			for(int j = 0; j < panelWidth/50; j++) {
				if(array[i][j] == -1 && draw[j][i] != -1) 
					return;
			}
		}
		didWin = true;
	}
	
	private void init(int x, int y) {
		for(int i = 0; i < panelHeight/50; i++) {
			for(int j = 0; j < panelWidth/50; j++) {
				draw[i][j] = 0;
//				if(Math.random()>=.85 && (i != x || j != y)) { 
//					array[i][j] = -1;
//					NUMBOMBS++;
//				}
			}
		}

		// set bomb locations
		for(int i = 0; i < NUMBOMBS;) {
			int coord = (int)(256*Math.random());
			int u = coord / 16;
			int v = coord % 16;
			if(u == x && v == y) {
				continue;
			}
			if(array[u][v] != -1) {
				array[u][v] = -1;
				++i;
			}
		}

//		if(NUMBOMBS*MINECOST+50>=money) {
//			restart();
//			init(x, y);
//		}
		
		// set other numbers
		for(int i = 0; i < panelHeight/50; i++) {
			for(int j = 0; j < panelWidth/50; j++) {
				if(array[i][j] != -1) {
					// north
					if(i > 0 && array[i-1][j] == -1) 
						array[i][j]++;
					// west
					if(j > 0 && array[i][j-1] == -1) 
						array[i][j]++;
					// south
					if(i+1 < panelHeight/50 && array[i+1][j] == -1) 
						array[i][j]++;
					// east
					if(j+1 < panelWidth/50 && array[i][j+1] == -1) 
						array[i][j]++;
					// northwest
					if(i > 0 && j > 0 && array[i-1][j-1] == -1) 
						array[i][j]++;
					// northeast
					if(i > 0 && j+1 < panelWidth/50 && array[i-1][j+1] == -1) 
						array[i][j]++;
					// southwest
					if(i+1 < panelHeight/50 && j > 0 && array[i+1][j-1] == -1) 
						array[i][j]++;
					// southeast
					if(i+1 < panelHeight/50 && j+1 < panelWidth/50 && array[i+1][j+1] == -1) 
						array[i][j]++;
				}
			}
		}
		
		// print coords
		if(DEBUG) {
			for(int i = 0; i < panelHeight/50; i++) {
				for(int j = 0; j < panelWidth/50; j++) {
					if(array[i][j] == -1) 
						System.out.print("x ");
					else 
						System.out.print(array[i][j]+" ");
				}
				System.out.println();
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		if(didFail) {
			for(int i = 0; i < panelHeight/50; i++) {
				for(int j = 0; j < panelWidth/50; j++) {
					if(array[i][j] == -1) {
						g.drawImage(fail, j*50, i*50, null);
					}
				}
			}
		}
		if(didClick) {
			for(int i = 0; i < panelHeight/50; i++) {
				for(int j = 0; j < panelWidth/50; j++) {
					if(array[j][i] != -1 && draw[i][j]==1) 
						g.drawString(array[j][i]+"", i*50+20, j*50+30);
					else if(draw[i][j] == -1) 
						g.drawImage(sure, i*50+10, j*50+10, null);
					else if(draw[i][j] == -2) 
						g.drawImage(unsure, i*50, j*50, null);
					else if(draw[i][j] == 0) 
						g.drawString("", i*50+20, j*50+30);
				}
			}
		}
	}
	
	private void drawGrid(Graphics g) {
		for(int i = 0; i < panelWidth; i+=50) {
			g.drawLine(0, i, 800, i);
			g.drawLine(i, 0, i, 800);
		}
	}
}
