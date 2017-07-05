package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Entry;

public class RightPanel extends JPanel {
	private JPanel top, bottom;
	private JLabel timeLabel, nbLabel, entryNRect, entrySize , messageL;
	private Entry entry;

	public RightPanel() {
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.initTop();
		this.initBottom();

		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		this.add(this.top);
		this.add(this.bottom);
	}

	private void initTop() {
		this.top = new JPanel();
		this.top.setLayout(new BoxLayout(this.top, BoxLayout.PAGE_AXIS));
		this.timeLabel = new JLabel();
		this.nbLabel = new JLabel();
		this.entryNRect = new JLabel();
		this.entrySize = new JLabel();
		this.updateTime(0);
		this.updateNb(0);
		this.updateEntryNRect(0);
		this.updateEntrySize("?x?");

		this.top.add(this.entryNRect);
		this.top.add(this.entrySize);
		this.top.add(this.timeLabel);
		this.top.add(this.nbLabel);
	}

	public void updateNb(int i) {
		this.nbLabel.setText("Nb boite(s) .......... " + i);
	}

	public void updateTime(long i) {
		this.timeLabel.setText("Temps ................ " + i + "ms");
	}

	private void updateEntryNRect(int i) {
		this.entryNRect.setText("Nb rectangle(s) ..... " + i);
	}

	private void updateEntrySize(String str) {
		this.entrySize.setText("Taille de la boite ... " + str);
	}

	private void initBottom() {
		this.bottom = new JPanel();
		this.bottom.setBorder(BorderFactory.createLineBorder(Color.gray));
		this.messageL = new JLabel("...");
		this.bottom.add(this.messageL);
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
		this.updateEntrySize(entry.getBoite().toString());
		this.updateEntryNRect(entry.getRectangles().size());
	}
	
	public void affiche(String message) {
		this.messageL.setText(message);
	}
}
