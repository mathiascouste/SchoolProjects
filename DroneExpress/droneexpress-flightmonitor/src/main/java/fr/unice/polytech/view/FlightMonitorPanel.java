package fr.unice.polytech.view;

import javax.swing.JPanel;

import fr.unice.polytech.AppClient;


public class FlightMonitorPanel extends JPanel {

    private static final long serialVersionUID = -2429508801502964028L;

    public FlightMonitorPanel() {
        this.setSize(AppClient.SIZECOEF(640), AppClient.SIZECOEF(480));
        this.setLayout(null);
        this.add(new MapPanel());
        this.add(new ControlPanel());
        this.add(new DeliveryManagementPanel());
    }
}
