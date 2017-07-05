package model.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.structure.Structure;

public class Grid {
	private int width,height;
	private Case [][] cases;
	private String rule;
	
	public Grid(String rule) {
		this(10,10,rule);
	}
	
	public Grid(int width,int height,String rule) {
		this.width = width;
		this.height = height;
		this.cases = new Case[this.width][this.height];
		this.rule = rule;
	}
	
	public int initializeCases() {
		int cpt = 0;
		for(int i = 0 ; i < this.width ; i++) {
			for(int j = 0 ; j < this.height ; j++) {
				this.cases[i][j] = new Case(this.rule);
				cpt++;
			}
		}
		for(int i = 0 ; i < this.width ; i++) {
			for(int j = 0 ; j < this.height ; j++) {
				this.cases[i][j].setNeighborhood(getNeighborhood(i,j));
			}
		}
		return this.width*this.height;
	}
	
	private Case[][] getNeighborhood(int x, int y) {
		Case [][] nbh = new Case[3][3];
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				nbh[i][j] = this.cases[(x+i-1+width)%width][(y+j-1+height)%height];
			}
		}
		return nbh;
	}

	public void makeTheyRun() {
		for(int i = 0 ; i < this.width ; i++) {
			for(int j = 0 ; j < this.height ; j++) {
				Thread thread = new Thread(this.cases[i][j]);
				thread.start();
			}
		}
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Case[][] getCases() {
		return cases;
	}
	public void setCases(Case[][] cases) {
		this.cases = cases;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public void stopAll() {
		for(int i = 0 ; i < this.width ; i++) {
			for(int j = 0 ; j < this.height ; j++) {
				this.cases[i][j].stop();
			}
		}
	}
	public String toString() {
		String toRet = "";
		toRet += "┏";
		for(int i = 0 ; i < this.height ; i++) toRet += "━";
		toRet += "┓\n";
		for(int i = 0 ; i < this.width ; i++) {
			toRet += "┃";
			for(int j = 0 ; j < this.height ; j++) {
				toRet += this.cases[i][j].getSign();
				/*if(this.cases[i][j].isAlive()) {
					toRet += "☮";
				} else {
					toRet += " ";
				}*/
			}
			toRet += "┃\n";
		}
		toRet += "┗";
		for(int i = 0 ; i < this.height ; i++) toRet += "━";
		toRet += "┛\n";
		return toRet;
	}

	public void randomBorn(double d) {
		for(int i = 0 ; i < this.width ; i++) {
			for(int j = 0 ; j < this.height ; j++) {
				if(Math.random() < d) {
					this.cases[i][j].setState(true);
				}
			}
		}
	}

	public int nbActive() {
		int cpt = 0;
		for(int i = 0 ; i < this.width ; i++) {
			for(int j = 0 ; j < this.height ; j++) {
				if(this.cases[i][j].isAlive() || this.cases[i][j].willBorn) cpt++;
			}
		}
		return cpt;
	}
	
	public void poseStrucure(int x, int y, boolean [][] tab) {
		for(int i = 0 ; i < tab.length ; i++) {
			for(int j = 0 ; j < tab[i].length ; j++) {
				this.cases[(i+x)%this.width][(j+y)%this.height].setState(tab[i][j]);
			}
		}
	}
	public void poseStrucure(int x, int y,Structure structure) {
		poseStrucure(x,y,intToBool(structure.getTable()));
	}

	public void doTheThing() {
		for(int i = 0 ; i < this.width ; i++) {
			for(int j = 0 ; j < this.height ; j++) {
				this.cases[i][j].doTheThing();
			}
		}
	}
	public static boolean[][] intToBool(int[][] tab) {
		boolean[][] toRet = new boolean[tab.length][];
		for(int i = 0 ; i < tab.length ; i++) {
			toRet[i] = new boolean[tab[i].length];
			for(int j = 0 ; j < tab[i].length ; j++) {
				if(tab[i][j] == 1) {
					toRet[i][j] = true;
				} else {
					toRet[i][j] = false;
				}
			}
		}
		return toRet;
	}

	public void paint(Graphics g) {
		  for(int i = 0 ; i < this.width ; i++) {
			  for(int j = 0 ; j < this.height ; j++) {
				  if(this.cases[i][j].isAlive()) {
					  g.setColor(Color.red);
					  g.fillRect(j*5, i*5, 5,5);
				  } else {
					  double age = this.cases[i][j].getDate()%10;
					  age = 1/(age+1);
					  age *= 255;
					  int cc = (int)age;
					  Color c = new Color(cc,cc,cc,120);
					  g.setColor(c);
					  g.fillRect(j*5, i*5, 5,5);
				  }
			  }
		  }
	}
}
