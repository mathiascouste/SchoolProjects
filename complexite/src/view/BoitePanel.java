package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Boite;
import model.Rectangle;
import model.sac.Sac;

public class BoitePanel extends JPanel {
	public static int COEF = 10;
	public static final int BORD = 5;
	private Boite boite;

	public BoitePanel(Boite b) {
		this.boite = b;
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(this.boite.getLargeur() * COEF + 2
				* BORD, this.boite.getHauteur() * COEF + 2 * BORD));
		this.setLayout(null);
		Sac s = boite.getRectangles();
		for (int i = 0; i < s.size(); i++) {
			this.add(new RectanglePanel(s.get(i)));
		}
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, this.boite.getLargeur() * COEF + 2 * BORD, BORD);
		g.fillRect(0, 0, BORD, this.boite.getLargeur() * COEF + 2 * BORD);
		g.fillRect(0, this.boite.getLargeur() * COEF + BORD,
				this.boite.getLargeur() * COEF + 2 * BORD, BORD);
		g.fillRect(this.boite.getLargeur() * COEF + BORD, 0, BORD,
				this.boite.getLargeur() * COEF + 2 * BORD);
		g.setColor(Color.gray);
		g.drawRect(BORD, BORD, this.boite.getLargeur() * COEF,
				this.boite.getLargeur() * COEF);
	}
}
