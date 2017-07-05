package view;

import java.awt.Dimension; 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import model.grid.Grid;
 
public class Fenetre extends JFrame{
	private Grid grid;
	private int largeur, hauteur;
	private Panneau pan;

  public Fenetre(Grid grid){
	  this.grid = grid;
	  this.largeur = grid.getWidth()*5;
	  this.hauteur = grid.getHeight()*5;
	  
	  this.pan = new Panneau(this.hauteur,this.largeur,this.grid);
	  
    this.setTitle("Animation");
    this.setSize(this.largeur, this.hauteur);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setContentPane(pan);
    this.setVisible(true);
    
    go();
  }

  private void go(){
	  this.grid.makeTheyRun();
    for(;;){
    	pan.repaint();
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}