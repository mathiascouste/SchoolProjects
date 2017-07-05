package model.banc;

import java.util.ArrayList;
import java.util.List;

import model.Boite;
import model.sac.Sac;
import model.sac.SacMaxArea;
import model.sac.SacMaxHeight;
import model.sac.SacMaxWidth;
import model.sac.SacTriComb;

public class BancEssais {
	public static final int DEFAULT = 0;
	public static final int AREADESC = 1;
	public static final int HEIGHDESC = 2;
	public static final int WIDTHDESC = 3;
	public static final int TOPLEFT = 5;
	public static final int ALGO_FFDH = 10;
	public static final int ALGO_TL = 11;
	public static final int ALGO_NP = 12;
	public static final int ALGO_OPTIMUM = 13;
	public static final String STR_FFDH = "First Fit Decreasing Height";
	public static final String STR_TL = "Top Left";
	public static final String STR_NP = "Non Polynomial";
	public static final String STR_OPTIMUM = "OPTIMUM";
	public static final String STR_DEFAULT = "Pas de tri";
	public static final String STR_AREADESC = "Tri par surfaces décroissantes";
	public static final String STR_HEIGHTDESC = "Tri par hauteurs décroissantes";
	public static final String STR_WIDTHDESC = "Tri par largeurs décroissantes";

	private int ordering;
	private int positioning;
	private int algorithm;
	private boolean show;

	private Sac input_rectangles;
	private Boite input_boite;

	private Sac ordered_rectangles;
	private Sac output_boites;

	public BancEssais() {
		this.ordering = DEFAULT;
		this.positioning = TOPLEFT;
		this.algorithm = ALGO_TL;
		this.show = true;
	}

	public long process() {
		if (!this.prepear())
			return -1;
		if (this.show)
			this.showWork();
		long startTime = System.currentTimeMillis();
		switch (this.algorithm) {
		case ALGO_TL:
			execute_TL();
			break;
		case ALGO_FFDH:
			execute_FFDH();
			break;
		case ALGO_NP:
			execute_NP();
			break;
		case ALGO_OPTIMUM:
			execute_optimum();
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	private void execute_optimum() {
		BancEssais be = new BancEssais();
		be.setInput_boite(this.input_boite);
		be.setInput_rectangles(this.input_rectangles);
		be.setShow(this.show);
		int n = this.input_rectangles.size();
		if(n <= 5) {
			be.setAlgorithm(ALGO_NP);
		} else if(n <= 1000) {
			be.setAlgorithm(ALGO_TL);
			be.setOrdering(AREADESC);
		} else {
			be.setAlgorithm(ALGO_FFDH);
		}
		be.process();
		this.output_boites = be.getOutput_boites();
	}

	public void clear() {
		/*
		 * if (this.input_rectangles != null) this.input_rectangles.clear();
		 */
		if (this.ordered_rectangles != null)
			this.ordered_rectangles.clear();
		if (this.output_boites != null)
			this.output_boites.clear();
	}

	private void execute_NP() {
		int meilleureTaille = Integer.MAX_VALUE;

		List<String> combinaisons = Permutation4
				.recupererCombinaisons(this.input_rectangles.size());
		BancEssais monBanc = new BancEssais();
		monBanc.setAlgorithm(BancEssais.ALGO_TL);
		monBanc.setOrdering(BancEssais.DEFAULT);
		monBanc.setPositioning(BancEssais.TOPLEFT);
		monBanc.setInput_boite(this.input_boite);
		monBanc.setShow(false);
		int k = 0;
		for (String str : combinaisons) {
			monBanc.clear();
			Sac s = new SacTriComb(str);
			s.addAll(this.input_rectangles);
			monBanc.setInput_rectangles(s);
			monBanc.process();
			Sac output = monBanc.getOutput_boites();
			if (meilleureTaille > output.size()) {
				meilleureTaille = output.size();
				this.output_boites = output.clone();
			}
		}
	}

	private void execute_TL() {
		while (!ordered_rectangles.isEmpty()) {
			TLBox b = new TLBox(this.input_boite);
			b.fill(this.ordered_rectangles);
			this.output_boites.add(b);
		}
	}

	private void execute_FFDH() {
		// PASSE 1 [
		// > Creer un sac sac1 de boite
		ArrayList<FFDHBox> s1 = new ArrayList<FFDHBox>();
		// > prendre le nth rectangle
		while (!this.ordered_rectangles.isEmpty()) {
			// > tester (en largeur) si il rentre dans une des boites du sac
			boolean toPlace = true;
			for (int j = 0; j < s1.size() && toPlace; j++) {
				if (s1.get(j).isEnoughWidthRemainingToPlaceRectangle(
						this.ordered_rectangles.top())) {
					// si oui : l'y mettre
					s1.get(j).place(this.ordered_rectangles.pop());
					toPlace = false;
				}
			}
			if (toPlace) {
				// si non : créer une nouvelle boite de meme hauteur que le
				// rectangle
				FFDHBox newBox = new FFDHBox();
				newBox.setHauteur(this.ordered_rectangles.top().getHauteur());
				newBox.setLargeur(this.input_boite.getLargeur());
				// > l'y mettre
				newBox.place(this.ordered_rectangles.pop());
				// > ajouter la boite dans le sac sac1
				s1.add(newBox);
			}
			// ]
		}
		// PASSE 2 [
		// > dans le sac output
		ArrayList<FFDHBox> s2 = new ArrayList<FFDHBox>();
		// > prendre la nth boite de sac1
		while (!s1.isEmpty()) {
			boolean toPlace = true;
			for (int i = 0; i < s2.size() && toPlace; i++) {
				// > tester (en hauteur) si elle rentre dans une boite du sac
				if (s2.get(i).isEnoughHeighRemainingToPlaceBoite(s1.get(0))) {
					// si oui : l'y mettre
					s2.get(i).transfererRectangles(s1.remove(0));
					toPlace = false;
				}
			}
			if (toPlace) {
				// si non : creer un nouvelle boite copy de input_boite
				FFDHBox newBox = new FFDHBox();
				newBox.setHauteur(this.input_boite.getHauteur());
				newBox.setLargeur(this.input_boite.getLargeur());
				// > l'y mettre
				newBox.transfererRectangles(s1.remove(0));
				// > ajouter la boite dans le sac ouput
				s2.add(newBox);
			}
			// ]
		}
		for (Boite b : s2) {
			this.output_boites.add(b);
		}
	}

	private void showWork() {
		System.out.println("######### TODO #########");
		System.out.println("Put " + this.ordered_rectangles.size()
				+ " rectangles : ");
		System.out.println(this.input_rectangles);
		System.out.println("in this box : " + this.input_boite);
	}

	private boolean prepear() {
		// Force le tri selon algorithm
		switch (this.algorithm) {
		case ALGO_FFDH:
			this.ordering = HEIGHDESC;
			break;
		}

		// Préparation du tri et creation Sac
		if (input_rectangles != null && input_boite != null) {
			switch (this.ordering) {
			case AREADESC:
				this.ordered_rectangles = new SacMaxArea();
				break;
			case HEIGHDESC:
				this.ordered_rectangles = new SacMaxHeight();
				break;
			case WIDTHDESC:
				this.ordered_rectangles = new SacMaxWidth();
				break;
			default:
				this.ordered_rectangles = new Sac();
			}
			this.ordered_rectangles.addAll(input_rectangles);
			this.output_boites = new Sac();
			return true;
		} else {
			return false;
		}
	}

	public void setPositioning(int positioning) {
		this.positioning = positioning;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public void setAlgorithm(int algorithm) {
		this.algorithm = algorithm;
	}

	public Sac getOutput_boites() {
		return output_boites;
	}

	public void setInput_rectangles(Sac input_rectangles) {
		this.input_rectangles = input_rectangles;
	}

	public void setInput_boite(Boite input_boite) {
		this.input_boite = input_boite;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public static String[] getAlgos() {
		String[] algos = { STR_TL, STR_FFDH, STR_NP, STR_OPTIMUM };
		return algos;
	}

	public static String[] getTris(String algo) {
		switch (algo) {
		case STR_TL:
			String[] tris = { STR_DEFAULT, STR_AREADESC, STR_HEIGHTDESC,
					STR_WIDTHDESC };
			return tris;
		default:
			String[] tri = { STR_DEFAULT };
			return tri;
		}

	}

	public static int getAlgorithm(String stralgo) {
		switch (stralgo) {
		case STR_TL:
			return ALGO_TL;
		case STR_FFDH:
			return ALGO_FFDH;
		case STR_NP:
			return ALGO_NP;
		case STR_OPTIMUM:
			return ALGO_OPTIMUM;
		default:
			return ALGO_FFDH;
		}
	}

	public static int getPositioning(String stralgo) {
		return TOPLEFT;
	}

	public static int getOrdering(String strtri) {
		switch (strtri) {
		case STR_DEFAULT:
			return DEFAULT;
		case STR_AREADESC:
			return AREADESC;
		case STR_HEIGHTDESC:
			return HEIGHDESC;
		case STR_WIDTHDESC:
			return WIDTHDESC;
		default:
			return DEFAULT;
		}
	}

}
