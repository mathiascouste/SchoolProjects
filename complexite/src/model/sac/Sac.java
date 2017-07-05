package model.sac;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import model.Rectangle;

/**
 * Classe Sac. Liste de rectangles qui respectent tous un même critère de taille
 * (hors sac initial).
 */
public class Sac {
	protected List<Rectangle> rectangles;

	public Sac() {
		this.rectangles = new ArrayList<Rectangle>();
	}
	
	public Sac clone() {
		Sac clony = new Sac();
		for(Rectangle r : rectangles) {
			clony.add(r.clone());
		}
		return clony;
	}

	public Rectangle get(int index) {
		return this.rectangles.get(index);
	}

	/**
	 * Ajoute un nouveau rectangle au sac.
	 * 
	 * @param rect
	 *            le nouveau rectangle
	 * @return true si le rectangle a bien été ajouté, false s'il est déjà dans
	 *         la liste.
	 */
	public boolean add(Rectangle rect) {
		if (!this.rectangles.contains(rect)) {
			this.rectangles.add(rect);
			return true;
		}
		return false;
	}

	public void addAll(Sac rects) {
		for (int i = 0; i < rects.size(); i++) {
			this.add(rects.get(i));
		}
	}

	/**
	 * Vide le sac.
	 */
	public void clear() {
		this.rectangles.clear();
	}

	/**
	 * Retourne vrai si le sac contient le rectangle, faux sinon.
	 * 
	 * @param rect
	 *            le rectangle a testé
	 * @return vrai si le rectangle est dans le sac.
	 */
	public boolean contains(Rectangle rect) {
		return this.rectangles.contains(rect);
	}

	/**
	 * Retourne vrai si l'ensemble des rectangles de la collection passée en
	 * paramètre sont contenus dans le sac, faux sinon.
	 * 
	 * @param arg0
	 *            la collection de rectangles
	 * @return un booléen
	 */
	public boolean containsAll(Collection<Rectangle> arg0) {
		return this.rectangles.containsAll(arg0);
	}

	/**
	 * Retourne vrai si le sac est vide.
	 * 
	 * @return un booléen.
	 */
	public boolean isEmpty() {
		return this.rectangles.isEmpty();
	}

	public Iterator<Rectangle> iterator() {
		return this.rectangles.iterator();
	}

	/**
	 * Retire le rectangle passé en paramètre du sac.
	 * 
	 * @param arg0
	 *            le rectangle à retirer.
	 * @return vrai si le rectangle a été retiré, faux sinon.
	 */
	public boolean remove(Object arg0) {
		return this.rectangles.remove(arg0);
	}

	/**
	 * Retire tous les rectangles passés en paramètre du sac.
	 * 
	 * @param arg0
	 *            une collection de rectangle à supprimer.
	 * @return vrai si leur retrait a réussi, faux sinon.
	 */
	public boolean removeAll(Collection<Rectangle> arg0) {
		return this.rectangles.removeAll(arg0);
	}

	public boolean retainAll(Collection<Rectangle> arg0) {
		return this.rectangles.retainAll(arg0);
	}

	/**
	 * Retourne le nombre de rectangles contenus dans le sac.
	 * 
	 * @return la taille du sac.
	 */
	public int size() {
		return this.rectangles.size();
	}

	public Object[] toArray() {
		return this.rectangles.toArray();
	}

	public Object[] toArray(Object[] arg0) {
		return this.rectangles.toArray(arg0);
	}

	public String toString() {
		String toRet = "";
		for (int i = 0; i < this.rectangles.size() - 1; i++) {
			toRet += this.rectangles.get(i) + ",";
		}
		if (this.rectangles.size() > 0) {
			toRet += this.rectangles.get(this.rectangles.size() - 1);
		}
		return toRet;
	}

	/**
	 * Retire le dernier rectangle du sac et le retourne.
	 * 
	 * @return le dernier rectangle du sac.
	 * @throws SacVideException
	 */
	public Rectangle pop() {
		if (!this.rectangles.isEmpty()) {
			return this.rectangles.remove(0);
		} else {
			return null;
		}
	}
	
	public Rectangle top() {
		if(!this.rectangles.isEmpty()) {
			return this.rectangles.get(0);
		} else {
			return null;
		}
	}

	class SacVideException extends Exception {
	}
}
