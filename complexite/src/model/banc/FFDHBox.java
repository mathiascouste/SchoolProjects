package model.banc;

import model.Boite;
import model.Rectangle;
import model.sac.Sac;

public class FFDHBox extends Boite {
	private int largeurOccupee;
	private int hauteurOccupee;

	public FFDHBox() {
		this.largeurOccupee = 0;
		this.hauteurOccupee = 0;
	}

	public boolean isEnoughWidthRemainingToPlaceRectangle(Rectangle rectangle) {
		return (getLargeur() - largeurOccupee) >= rectangle.getLargeur();
	}

	public boolean isEnoughHeighRemainingToPlaceBoite(FFDHBox ffdhBox) {
		return (getHauteur() - hauteurOccupee) >= ffdhBox.getHauteur();
	}

	public void place(Rectangle r) {
		this.getRectangles().add(r);
		r.setPositionX(largeurOccupee);
		largeurOccupee += r.getLargeur();
	}

	public void transfererRectangles(FFDHBox b) {
		Sac aTransferer = b.getRectangles();
		while (!aTransferer.isEmpty()) {
			Rectangle r = aTransferer.pop();
			r.setPositionY(this.hauteurOccupee);
			this.rectangles.add(r);
		}
		this.hauteurOccupee += b.getHauteur();
	}

}
