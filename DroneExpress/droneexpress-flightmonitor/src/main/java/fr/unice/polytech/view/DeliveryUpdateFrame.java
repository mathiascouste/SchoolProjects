package fr.unice.polytech.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import fr.unice.polytech.business.Delivery;
import fr.unice.polytech.model.Datas;
import fr.unice.polytech.servicesconsumers.Itinerary;


public class DeliveryUpdateFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = -6817753903631874325L;

    private Delivery delivery;

    private JComboBox<String> ends;

    private JButton create = new JButton("create");

    public DeliveryUpdateFrame(Delivery delivery) {
        super();
        this.delivery = delivery;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        this.setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("Delivery Creation"));
        createCombo(panel);

        panel.add(this.create);
        this.create.addActionListener(this);

        this.pack();
        this.setVisible(true);
    }

    private void createCombo(JPanel panel) {
        String[] warehousesNames = new String[Datas.warehouses.size()];
        for (int i = 0; i < Datas.warehouses.size(); i++) {
            warehousesNames[i] = Datas.warehouses.get(i).getName();
        }

        this.ends = new JComboBox<>(warehousesNames);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.add(new JLabel("New Arrival"));
        p.add(this.ends);
        panel.add(p);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.create) {
            long deliveryId = delivery.getId();
            long warehouseId = Datas.warehouses.get(ends.getSelectedIndex()).getId();
            new Itinerary().changeItinerary(deliveryId, warehouseId);
            this.dispose();
        }
    }
}
