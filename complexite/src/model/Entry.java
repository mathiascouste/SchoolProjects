package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.sac.Sac;

public class Entry {

	private Boite boite;
	private Sac rectangles;

	public Entry() {
		this.boite = new Boite();
		this.rectangles = new Sac();
	}

	/**
	 * Créer une boîte et des rectangles à partir du fichier texte en entrée. Le
	 * fichier texte fait 2 lignes : la première donne les dimensions de la
	 * boîte : 3x3 La seconde ligne donne les dimensions des rectangles séparées
	 * par une virgule : 2x2,2x1...
	 * 
	 * @param filename
	 *            le nom du fichier à lire.
	 */
	public void readFile(String filename) {
		String ligne1 = "", ligne2 = "";
		if (filename != null) {
			// lecture du fichier texte
			try {
				InputStream ips = new FileInputStream(filename);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);

				ligne1 = br.readLine();
				ligne2 = br.readLine();

				br.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			if (!ligne1.isEmpty() && !ligne2.isEmpty()) {
				// Nettoyage des string
				ligne1.trim();
				ligne2.trim();
				// Mise à jour des dimensions de la boite
				this.boite.setDimensionsFromString(ligne1);
				// Création des rectangles
				for (String retval : ligne2.split(",")) {
					Rectangle rec = new Rectangle();
					rec.setDimensionsFromString(retval);
					this.rectangles.add(rec);
				}
			}
		}
	}

	/**
	 * Retourne la boite à remplir.
	 * 
	 * @return la boite.
	 */
	public Boite getBoite() {
		return boite;
	}

	/**
	 * Remplace la boite actuelle par une nouvelle boite.
	 * 
	 * @param boite
	 *            la nouvelle boite.
	 */
	public void setBoite(Boite boite) {
		this.boite = boite;
	}

	/**
	 * Retourne le Sac contenant tous les rectangles.
	 * 
	 * @return un Sac de rectangles.
	 */
	public Sac getRectangles() {
		return rectangles;
	}

	/**
	 * Remplace l'ensemble des rectangles par un nouvel ensemble de rectangles.
	 * 
	 * @param rectangles
	 */
	public void setRectangles(Sac rectangles) {
		this.rectangles = rectangles;
	}

	public static Entry generate(int largeur, int hauteur, int quantite) {
		Entry toRet = new Entry();
		Boite b = new Boite();
		b.setLargeur(largeur);
		b.setHauteur(hauteur);
		toRet.setBoite(b);
		Sac rects = new Sac();
		for (int i = 0; i < quantite; i++) {
			rects.add(Rectangle.genAleatoire(largeur, hauteur));
		}
		toRet.rectangles = rects;
		return toRet;
	}

	public String toString() {
		String toRet = "";
		toRet += boite + "\n";
		toRet += rectangles + "\n";
		return toRet;
	}

	public void save(String filename) {
		String path = System.getProperty("user.dir") + "/" + filename;
		FileWriter fw;
		try {
			fw = new FileWriter(path);
			BufferedWriter output = new BufferedWriter(fw);
			output.write(this.toString());
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getBorneInf() {
		double surfBox = boite.getHauteur() * boite.getLargeur();
		double surfTot = 0;
		for (int i = 0; i < rectangles.size(); i++) {
			surfTot += rectangles.get(i).getHauteur() * rectangles.get(i).getLargeur();
		}
		double bI = surfTot / surfBox;
		return Math.ceil(bI);
	}
}
