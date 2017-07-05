package fr.unice.polytech.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.*;

import fr.unice.polytech.business.Delivery;
import fr.unice.polytech.model.Datas;
import fr.unice.polytech.servicesconsumers.Itinerary;


public class DeliveryCreationFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = -6817753903631874325L;

    private DeliveryManagementPanel dmp;

    private Delivery delivery;
    private static long id = 1;

    private JComboBox<String> starts;
    private JComboBox<String> ends;

    private JButton create = new JButton("create");

    public DeliveryCreationFrame(DeliveryManagementPanel dmp) {
        super();
        this.dmp = dmp;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.delivery = new Delivery();
        this.delivery.setId(id);
        this.delivery.setParcelId(id);
        this.delivery.setDepartureTime(LocalDateTime.now());

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

        this.starts = new JComboBox<>(warehousesNames);
        this.ends = new JComboBox<>(warehousesNames);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.add(new JLabel("Departure"));
        p.add(this.starts);
        panel.add(p);

        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.add(new JLabel("Arrival"));
        p.add(this.ends);
        panel.add(p);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.create) {
            Delivery delivery = new Delivery();
            delivery.setId(id);
            delivery.setParcelId(id);
            delivery.setDepartureTime(LocalDateTime.now().plusMinutes(0));
            delivery.setDepartureLocation(Datas.warehouses.get(starts.getSelectedIndex()).getId());
            delivery.setArrivalLocation(Datas.warehouses.get(ends.getSelectedIndex()).getId());

            delivery = new Itinerary().createItinerary(delivery);
            if (delivery != null) {
                id++;
                dmp.addDelivery(delivery);
            }
            this.dispose();
        }
    }
}
