package model;

import model.sac.Sac;
import model.sac.SacMaxWidth;

public class Boite extends Rectangle {
	protected Sac rectangles;

	public Boite() {
		this.rectangles = new Sac();
	}

	public Boite clone() {
		Boite clony = new Boite();
		clony.largeur = this.largeur;
		clony.hauteur = this.hauteur;
		clony.positionX = this.positionX;
		clony.positionY = this.positionY;
		clony.rectangles = this.rectangles.clone();
		return clony;
	}
	
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public Sac getRectangles() {
		return rectangles;
	}
}
