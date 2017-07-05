package view.generate;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Entry;

public class GenFrame extends JFrame implements ActionListener {
	private JTextField h, w, quantite, nom;
	private JLabel taille, x, quantiteL, nomL;
	private JButton goB;

	public GenFrame() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setLayout(new GridLayout(4, 2));

		this.w = new JTextField("L");
		this.h = new JTextField("H");
		this.quantite = new JTextField();
		this.nom = new JTextField();

		this.taille = new JLabel("taille");
		this.x = new JLabel("x");
		this.quantiteL = new JLabel("quantité");
		this.nomL = new JLabel("file name");

		this.goB = new JButton("GENERER");
		this.goB.addActionListener(this);

		this.add(this.nomL);
		this.add(this.nom);
		this.add(this.taille);

		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
		pan.add(this.w);
		pan.add(this.x);
		pan.add(this.h);
		this.add(pan);

		this.add(this.quantiteL);
		this.add(this.quantite);
		this.add(new JPanel());
		this.add(this.goB);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == goB) {
			if (this.verifier()) {
				Entry en = Entry.generate(new Integer(w.getText()),
						new Integer(h.getText()),
						new Integer(quantite.getText()));
				en.save(this.nom.getText());
				dispose();
			} else {
				JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(
						null,
						"Les champs doivent être correctement remplis (entier pour la taille et le nombre)",
						"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean verifier() {
		try {
			Integer.parseInt(h.getText());
		} catch (Exception e) {
			return false;
		}
		try {
			Integer.parseInt(w.getText());
		} catch (Exception e) {
			return false;
		}
		try {
			Integer.parseInt(quantite.getText());
		} catch (Exception e) {
			return false;
		}
		if (nom.getText().isEmpty()) {
			return false;
		}
		return true;
	}
}
