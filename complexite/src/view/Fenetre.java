package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;

import model.Boite;
import model.sac.Sac;

public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;

	public Fenetre(Sac boites) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(250, 250));
		//this.getContentPane().setLayout(
		//		new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.getContentPane().setLayout(new GridLayout(0,5));
		Boite b = null;
		for(int i = 0 ; i < boites.size() ; i++) {
			this.getContentPane().add(new BoitePanel((Boite) boites.get(i)));
		}
		this.pack();
	}
}
