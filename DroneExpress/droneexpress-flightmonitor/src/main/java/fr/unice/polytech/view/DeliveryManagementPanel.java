package fr.unice.polytech.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import fr.unice.polytech.AppClient;
import fr.unice.polytech.business.Delivery;


public class DeliveryManagementPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    public List<Delivery> deliveries = new ArrayList<>();

    private JButton newDelivery = new JButton("new delivery"),
            updateDelivery = new JButton("change arrival");

    private JList<String> deliveryList = new JList<>();

    public DeliveryManagementPanel() {
        this.setSize(AppClient.SIZECOEF(160), AppClient.SIZECOEF(480));
        this.setLocation(AppClient.SIZECOEF(640), 0);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        this.add(new JLabel("Delivery Management"));

        JScrollPane pan = new JScrollPane(deliveryList);
        this.add(pan);

        this.add(newDelivery);
        this.add(updateDelivery);

        this.newDelivery.addActionListener(this);
        this.updateDelivery.addActionListener(this);
    }

    private long n = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.newDelivery) {
            new DeliveryCreationFrame(this);
        } else if (e.getSource() == this.updateDelivery) {
            new DeliveryUpdateFrame(this.deliveries.get(deliveryList.getSelectedIndex()));
        }
    }

    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
        updateList();
    }

    private void updateList() {
        String[] strs = new String[deliveries.size()];
        for (int i = 0; i < deliveries.size(); i++) {
            strs[i] = "Delivery n#" + deliveries.get(i).getId();
        }
        deliveryList.setListData(strs);
    }

}
