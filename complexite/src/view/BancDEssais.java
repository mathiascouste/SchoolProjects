package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.generate.GenFrame;

import model.Boite;
import model.Entry;
import model.Rectangle;
import model.banc.BancEssais;

public class BancDEssais extends JFrame {
	private JFrame THIS;
	private JPanel top, middle, left, right;
	private JFileChooser fileChooser;
	private JComboBox<String> comboAlgo, comboTri, comboN;
	private JButton fileButton, goButton;

	private JMenuItem quitter, generer;

	private Entry entree;

	public BancDEssais() {
		this.THIS = this;
		this.setTitle("Banc d'essais");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800, 500));

		this.initMenu();
		this.initTop();
		this.initMiddle();

		this.setLayout(new BorderLayout());
		this.add(this.top, BorderLayout.NORTH);
		this.add(this.middle, BorderLayout.CENTER);

		this.pack();
	}

	private void initMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu option = new JMenu("Option");
		menubar.add(option);
		quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new MenuListener());
		generer = new JMenuItem("Generer");
		generer.addActionListener(new MenuListener());
		option.add(generer);
		option.add(quitter);
		this.setJMenuBar(menubar);

	}

	private void initMiddle() {
		this.initLeft();
		this.initRight();

		this.middle = new JPanel();
		this.middle.setLayout(new BoxLayout(this.middle, BoxLayout.LINE_AXIS));
		this.middle.add(this.left);
		this.middle.add(this.right);
	}

	private void initTop() {
		this.fileChooser = new JFileChooser(new File(
				System.getProperty("user.dir")));

		this.top = new JPanel();
		this.fileButton = new JButton("choisir un fichier");
		this.fileButton.addActionListener(new ButtonListener());
		this.comboAlgo = new JComboBox<String>();
		this.addItemsInComboBox(this.comboAlgo, BancEssais.getAlgos());
		this.comboAlgo.addItemListener(new ComboBoxAlgoListener());
		this.comboTri = new JComboBox<String>();
		this.addItemsInComboBox(this.comboTri,
				BancEssais.getTris(BancEssais.STR_TL));
		this.comboN = new JComboBox<String>();
		this.addItemsInComboBox(this.comboN, this.getNs(50));

		this.goButton = new JButton("GO");
		this.goButton.addActionListener(new ButtonListener());
		this.top.add(this.fileButton);
		this.top.add(this.comboAlgo);
		this.top.add(this.comboTri);
		this.top.add(this.comboN);
		this.top.add(this.goButton);
		this.top.setLayout(new BoxLayout(this.top, BoxLayout.LINE_AXIS));
	}

	private String[] getNs(int n) {
		String[] toRet = new String[n];
		for (int i = 0; i < n; i++) {
			toRet[i] = new Integer(i + 1).toString();
		}
		return toRet;
	}

	private void addItemsInComboBox(JComboBox<String> combo, String[] tab) {
		combo.removeAllItems();
		for (String str : tab) {
			combo.addItem(str);
		}
	}

	private void initLeft() {
		this.left = new LeftPanel();
	}

	private void initRight() {
		this.right = new RightPanel();
	}

	public class ComboBoxAlgoListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				addItemsInComboBox(comboTri,
						BancEssais.getTris((String) event.getItem()));
			}
		}
	}

	public class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == fileButton) {
				fileChooser.showOpenDialog(null);
				fileButton.setText(fileChooser.getSelectedFile().getName());
				entree = new Entry();
				entree.readFile(fileChooser.getSelectedFile().getPath());
				((RightPanel) right).setEntry(entree);
			} else if (event.getSource() == goButton) {
				if (new Integer(comboN.getSelectedItem().toString()).intValue() == 1) {
					trucNormal();
				} else {
					multiTest(new Integer(comboN.getSelectedItem().toString())
							.intValue());
				}

			}
		}
	}

	public class MenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == quitter) {
				System.exit(0);
			} else if (e.getSource() == generer) {
				new GenFrame();
			}
		}

	}

	public void trucNormal() {
		if (entree == null) {
			// Boîte du message d'erreur
			JOptionPane jop3 = new JOptionPane();
			jop3.showMessageDialog(null,
					"Selectionnez un fichier d'entrée avant de lancer",
					"Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		BancEssais be = new BancEssais();
		be.setInput_boite(entree.getBoite());
		be.setInput_rectangles(entree.getRectangles());
		// be.setShow(false);

		be.setAlgorithm(BancEssais.getAlgorithm((String) comboAlgo
				.getSelectedItem()));
		be.setPositioning(BancEssais.getPositioning((String) comboAlgo
				.getSelectedItem()));
		be.setOrdering(BancEssais.getOrdering((String) comboTri
				.getSelectedItem()));
		THIS.setCursor(Cursor.WAIT_CURSOR);

		double borneInf = entree.getBorneInf();

		long time = be.process();

		double borneSup = be.getOutput_boites().size();
		((RightPanel) right).updateTime(time);
		((RightPanel) right).updateNb(be.getOutput_boites().size());
		((LeftPanel) left).setBoites(be.getOutput_boites());
		((RightPanel) right).affiche("Ratio du test : " + borneSup / borneInf
				+ " de l'optimal");
		THIS.setCursor(Cursor.DEFAULT_CURSOR);
	}

	public void multiTest(int n) {
		JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		String l = jop.showInputDialog(null, "Largeur et hauteur de la boite",
				"0", JOptionPane.QUESTION_MESSAGE);
		String q = jop.showInputDialog(null, "Quantité de rectangle", "0",
				JOptionPane.QUESTION_MESSAGE);
		List<Entry> entrees = new ArrayList<Entry>();
		long tempsTotal = 0;
		for (int i = 0; i < n; i++) {
			entrees.add(Entry.generate(new Integer(l), new Integer(l),
					new Integer(q)));
		}
		BancEssais be = new BancEssais();

		be.setAlgorithm(BancEssais.getAlgorithm((String) comboAlgo
				.getSelectedItem()));
		be.setPositioning(BancEssais.getPositioning((String) comboAlgo
				.getSelectedItem()));
		be.setOrdering(BancEssais.getOrdering((String) comboTri
				.getSelectedItem()));
		THIS.setCursor(Cursor.WAIT_CURSOR);
		be.setShow(false);
		double totRatio = 0;
		for (int i = 0; i < n; i++) {
			double borneInf = entrees.get(i).getBorneInf();
			be.setInput_boite(entrees.get(i).getBoite());
			be.setInput_rectangles(entrees.get(i).getRectangles());
			tempsTotal += be.process();
			double borneSup = be.getOutput_boites().size();
			totRatio += borneSup / borneInf;
		}
		totRatio /= n;
		((RightPanel) right).updateTime(tempsTotal);
		((RightPanel) right).updateNb(-1);
		((RightPanel) right).affiche("Ratio du test : " + totRatio
				+ " de l'optimal");

		THIS.setCursor(Cursor.DEFAULT_CURSOR);
	}
}
