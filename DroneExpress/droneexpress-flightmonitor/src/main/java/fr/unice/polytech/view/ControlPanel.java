package fr.unice.polytech.view;

import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.unice.polytech.AppClient;
import fr.unice.polytech.model.Datas;


public class ControlPanel extends JPanel implements ChangeListener, ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int SLIDERSIZE = 1000;

    private JSlider slider = new JSlider(0, SLIDERSIZE, 0);
    private JCheckBox showPast = new JCheckBox("show past flights");
    private JCheckBox showFuture = new JCheckBox("show future flights");
    private JCheckBox showEmpty = new JCheckBox("show empty flights");
    private JTextField parcelId = new JTextField();
    private JButton resetParcelId = new JButton("All parcels");
    private JButton refreshFlights = new JButton("Refresh flights");

    public ControlPanel() {
        this.setSize(AppClient.SIZECOEF(160), AppClient.SIZECOEF(480));
        this.setLocation(AppClient.SIZECOEF(480), 0);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(new JLabel("Control panel"));

        this.add(this.slider);
        this.slider.addChangeListener(this);

        this.add(this.showPast);
        this.showPast.addActionListener(this);
        this.add(this.showFuture);
        this.showFuture.addActionListener(this);
        this.add(this.showEmpty);
        this.showEmpty.addActionListener(this);
        this.showEmpty.setSelected(true);

        JPanel pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
        pan.add(new JLabel("panel id : "));
        pan.add(parcelId);
        this.add(pan);
        this.parcelId.addKeyListener(this);

        this.add(this.resetParcelId);
        this.resetParcelId.addActionListener(this);

        this.add(this.refreshFlights);
        this.refreshFlights.addActionListener(this);

        this.positionasseSlider();
    }

    public void positionasseSlider() {
        if (Datas.START != null && Datas.END != null) {
            Duration duration = Duration.between(Datas.START, Datas.END);
            Duration duration2 = Duration.between(Datas.START, LocalDateTime.now());
            double durationMinute = duration.toMinutes();
            double durationMinute2 = duration2.toMinutes();
            double coef = durationMinute2 / durationMinute;
            this.slider.setValue((int) (coef * SLIDERSIZE));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.slider) {
            double coef = this.slider.getValue() / (double) SLIDERSIZE;
            LocalDateTime sliderTime = Datas.getSliderTime(coef);
            Datas.filterPastCurrentFutureFlight(sliderTime);
            MapPanel.instance.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.showPast) {
            MapPanel.instance.setShowPast(this.showPast.isSelected());
        } else if (e.getSource() == this.showFuture) {
            MapPanel.instance.setShowFuture(this.showFuture.isSelected());
        } else if (e.getSource() == this.showEmpty) {
            MapPanel.instance.setShowEmpty(this.showEmpty.isSelected());
        } else if (e.getSource() == this.resetParcelId) {
            MapPanel.instance.setParcelId(null);
            this.parcelId.setText("");
        } else if (e.getSource() == this.refreshFlights) {
            this.positionasseSlider();
            Datas.loadFlights();
            Datas.filterPastCurrentFutureFlight(LocalDateTime.now());
        }
        MapPanel.instance.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == this.parcelId) {
            try {
                Long id = new Long(this.parcelId.getText());
                MapPanel.instance.setParcelId(id);
            } catch (RuntimeException rE) {

            }
        }
        MapPanel.instance.repaint();
    }
}
