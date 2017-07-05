package model;

import java.util.Random;

public class Rectangle {

	protected int largeur = 1;
	protected int hauteur = 1;
	protected int positionX;
	protected int positionY;

	public Rectangle clone() {
		Rectangle clony = new Rectangle();
		clony.largeur = this.largeur;
		clony.hauteur = this.hauteur;
		clony.positionX = this.positionX;
		clony.positionY = this.positionY;
		return clony;
	}

	/**
	 * Met à jour les dimensions du rectangle à partir d'une chaîne de
	 * caractères
	 * 
	 * @param str
	 */
	public void setDimensionsFromString(String str) {
		String[] retval = str.split("x");
		if (retval.length >= 2) {
			this.setLargeur(new Integer(retval[0]));
			this.setHauteur(new Integer(retval[1]));
		}
	}

	public String toString() {
		String str = "" + this.largeur;
		str += "x" + this.hauteur;
		return str;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public int getPositionX() {
		return this.positionX;
	}

	public void setPositionX(int x) {
		this.positionX = x;
	}

	public int getPositionY() {
		return this.positionY;
	}

	public void setPositionY(int y) {
		this.positionY = y;
	}

	public static Rectangle genAleatoire(int largeur, int hauteur) {
		Random rand = new Random();
		Rectangle r = new Rectangle();
		r.setLargeur(rand.nextInt(largeur) + 1);
		r.setHauteur(rand.nextInt(hauteur) + 1);
		return r;
	}
}
