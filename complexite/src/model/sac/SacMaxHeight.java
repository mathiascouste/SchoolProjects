package model.sac;

import model.Rectangle;

public class SacMaxHeight extends Sac {
	/**
	 * Ajoute un nouveau rectangle au sac en le placant par ordre decroissant de taille
	 * 
	 * @param rect
	 *            le nouveau rectangle
	 * @return true si le rectangle a bien été ajouté, false s'il est déjà dans
	 *         la liste.
	 */
	public boolean add(Rectangle rect) {
		for(int i = 0 ; i < this.rectangles.size() ; i++) {
			if(rect.getHauteur() >= this.rectangles.get(i).getHauteur()) {
				this.rectangles.add(i, rect);
				return true;
			}
		}
		this.rectangles.add(rect);
		return false;
	}
}
