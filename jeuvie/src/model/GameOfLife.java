package model;

import java.util.Scanner;

import view.Fenetre;

import Oscillator.Clock;

import model.grid.Grid;
import model.structure.complex.Clown;
import model.structure.gun.GosperGliderGun;
import model.structure.spaceship.Ant;
import model.structure.spaceship.HWSS;
import model.structure.spaceship.LWSS;
import model.structure.stilllife.Biblock;
import model.structure.stilllife.Block;

public class GameOfLife {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Grid grille = new Grid(51,51,"");
		grille.initializeCases();

		grille.poseStrucure(24, 24, new Clown());
		
		Fenetre fen = new Fenetre(grille);
		//fen.setVisible(true);

		/*System.out.println(grille+"\n");
		grille.makeTheyRun();
		
		System.out.println("START");
		int nn = 0;
		while((nn=grille.nbActive()) != 0) {
			try {
				System.out.println(grille+"\n"+nn);
				Thread.sleep(1000/27);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(grille+"\n"+nn);
		
		/*Scanner sc = new Scanner(System.in);
		for(;;) {
			System.out.println(grille+"\n");
			sc.next();
			grille.doTheThing();
		}*/
		//grille.stopAll();
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
}
