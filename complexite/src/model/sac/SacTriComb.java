package model.sac;

import model.Rectangle;

public class SacTriComb extends Sac {
	public int[] ordre;

	public SacTriComb(String s) {
		String[] ord = s.split(",");
		this.ordre = new int[ord.length];
		for (int i = 0; i < ord.length; i++) {
			this.ordre[i] = new Integer(ord[i]);
		}
	}

	public boolean add(Rectangle rect) {
		return this.rectangles.add(rect);
	}

	public void addAll(Sac rects) {
		if (this.ordre.length != rects.size())
			return;
		for (int i = 0; i < rects.size(); i++) {
			this.add(rects.get(this.ordre[i]));
		}
	}
}
