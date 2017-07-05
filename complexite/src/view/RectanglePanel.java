package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Rectangle;

public class RectanglePanel extends JPanel {
	private Rectangle rectangle;

	public RectanglePanel(Rectangle r) {
		this.rectangle = r;
		this.setBackground(Color.GREEN);
		this.setBounds(BoitePanel.BORD + BoitePanel.COEF * r.getPositionX(),
				BoitePanel.BORD + BoitePanel.COEF * r.getPositionY(),
				BoitePanel.COEF * r.getLargeur(),
				BoitePanel.COEF * r.getHauteur());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
