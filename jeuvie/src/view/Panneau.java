package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.grid.Grid;

public class Panneau extends JPanel {
  private int hauteur, largeur;
  private Grid grid;

  public Panneau(int h,int l,Grid grid) {
	  super();
	  this.hauteur = h;
	  this.largeur = l;
	  this.grid = grid;
  }
  
  public void paintComponent(Graphics g){
	  // On d�cide d'une couleur de fond pour notre rectangle
	  g.setColor(Color.white);
	  // On dessine celui-ci afin qu'il prenne tout la surface
	  g.fillRect(0, 0, this.getWidth(), this.getHeight());
	  this.grid.paint(g);

	  //g2d.translate(-monTank.getxPos(), -monTank.getyPos());
  } 
}