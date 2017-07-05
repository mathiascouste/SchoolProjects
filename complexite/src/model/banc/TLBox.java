package model.banc;

import model.Boite;
import model.Rectangle;
import model.sac.Sac;
import model.sac.SacMaxWidth;

public class TLBox extends Boite {
	private Rectangle[][] cases;

	public TLBox(Boite b) {
		this.largeur = b.getLargeur();
		this.hauteur = b.getHauteur();
	}

	/**
	 * Rempli 4 sacs différents en fonction du sac initial : - sac1 : tous les
	 * rectangles dont la hauteur ET la largeur sont plus grandes que la moitié
	 * de la hauteur et de la largeur de la boite. - sac2 : tous les rectangles
	 * dont la hauteur est plus grande que la moitié de la hauteur de la boite.
	 * - sac3 : tous les rectangles dont la longueur est supérieur à la moitié
	 * de la longueur de la boite. - sac4 : tous les autres rectangles.
	 * 
	 * @param rectangles
	 *            le sac initial
	 */
	public void fill(Sac rectangles) {
		updateCases();
		Sac s = new SacMaxWidth();
		Rectangle rect;
		while ((rect = rectangles.pop()) != null) {
			if (!this.placeTL(rect)) {
				s.add(rect);
			}
		}
		rectangles.addAll(s);
	}

	private void updateCases() {
		this.cases = new Rectangle[this.largeur][];
		for (int i = 0; i < this.largeur; i++) {
			this.cases[i] = new Rectangle[this.hauteur];
			for (int j = 0; j < this.hauteur; j++) {
				this.cases[i][j] = null;
			}
		}
	}

	public boolean placeTL(Rectangle r) {
		for (int i = 0; i < this.largeur; i++) {
			for (int j = 0; j < this.hauteur; j++) {
				if (verify(i, j, r.getLargeur(), r.getHauteur())) {
					this.truePlace(i, j, r);
					return true;
				}
			}
		}
		return false;
	}

	private void truePlace(int i, int j, Rectangle r) {
		this.rectangles.add(r);
		r.setPositionX(i);
		r.setPositionY(j);
		int largeur = r.getLargeur();
		int hauteur = r.getHauteur();
		for (int x = 0; x < largeur; x++) {
			for (int y = 0; y < hauteur; y++) {
				this.cases[i + x][j + y] = r;
			}
		}
	}

	private boolean verify(int i, int j, int largeur, int hauteur) {
		if ((this.largeur - i < largeur) || (this.hauteur - j < hauteur)) {
			return false;
		}
		for (int x = 0; x < largeur; x++) {
			for (int y = 0; y < hauteur; y++) {
				if (this.cases[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}

	public Rectangle[][] getCases() {
		return cases;
	}
}
