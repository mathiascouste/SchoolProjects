package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Boite;
import model.sac.Sac;

public class LeftPanel extends JPanel {
	private Sac boites;
	private JPanel content;

	public LeftPanel() {
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.content = new JPanel();
		this.content.setBackground(Color.white);

		JScrollPane scroll = new JScrollPane(this.content);
		scroll.setPreferredSize(new Dimension(600, 400));

		this.add(scroll);
	}

	public void setBoites(Sac boites) {
		this.boites = boites;
		this.updateBoites();
	}

	private void updateBoites() {
		this.content.removeAll();
		this.content.repaint();
		this.content.setLayout(new GridLayout(0, 5));
		for (int i = 0; i < boites.size(); i++) {
			if (i == 0) {
				int Z = boites.get(0).getHauteur();
				System.out.println(Z);
				int k = 100;
				if(Z <= 10) {
					k = 15;
				} else if(Z <= 25) {
					k = 10;
				} else if(Z <= 50) {
					k = 3;
				} else {
					k = 1;
				}
				BoitePanel.COEF = k;
			}
			this.content.add(new BoitePanel((Boite) boites.get(i)));
		}
	}
}
