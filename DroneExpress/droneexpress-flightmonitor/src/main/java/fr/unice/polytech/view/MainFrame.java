package fr.unice.polytech.view;

import javax.swing.JFrame;

import fr.unice.polytech.AppClient;


public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    public static MainFrame instance;

    public MainFrame() {
        super();
        instance = this;
        this.setSize(AppClient.SIZECOEF(800), AppClient.SIZECOEF(480));
        this.setContentPane(new FlightMonitorPanel());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        // this.setResizable(false);
    }
}
