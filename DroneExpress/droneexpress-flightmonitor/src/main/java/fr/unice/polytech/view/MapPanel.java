package fr.unice.polytech.view;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.unice.polytech.AppClient;
import fr.unice.polytech.ImageManager;
import fr.unice.polytech.business.Flight;
import fr.unice.polytech.business.Warehouse;
import fr.unice.polytech.model.Datas;


public class MapPanel extends JPanel {

    private static final long serialVersionUID = -4890401968730216771L;
    public static MapPanel instance;
    private boolean showPast = false;
    private boolean showFuture = false;
    private boolean showEmpty = true;
    private Long parcelId = null;

    public MapPanel() {
        instance = this;
        this.setSize(AppClient.SIZECOEF(480), AppClient.SIZECOEF(480));
        this.setLocation(0, 0);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        super.paintComponent(g);

        g.drawImage(ImageManager.get("maps").getImage("france"), 0, 0, AppClient.SIZECOEF(480),
                AppClient.SIZECOEF(480), 0, 0, 800, 800, null);

        g.setColor(Color.green);
        for (Flight f : Datas.current) {
            if (this.parcelId != null && this.parcelId.longValue() != f.getParcelId()) {
                continue;
            }
            Point d = warehouseIdToPoint((int) f.getDepartureId());
            Point a = warehouseIdToPoint((int) f.getArrivalId());
            if (f.getParcelId() == -1) {
                if (this.showEmpty) {
                    g.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                            new float[] { 9 }, 0));
                    g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                            AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
                }
            } else {
                g.setStroke(new BasicStroke(3));
                g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                        AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
            }
        }

        if (this.showFuture) {
            g.setColor(Color.red);
            for (Flight f : Datas.future) {
                if (this.parcelId != null && this.parcelId.longValue() != f.getParcelId()) {
                    continue;
                }
                Point d = warehouseIdToPoint((int) f.getDepartureId());
                Point a = warehouseIdToPoint((int) f.getArrivalId());
                if (f.getParcelId() == -1) {
                    if (this.showEmpty) {
                        g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                0, new float[] { 9 }, 0));
                        g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                                AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
                    }
                } else {
                    g.setStroke(new BasicStroke(2));
                    g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                            AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
                }

                g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                        AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
            }
        }

        if (this.showPast) {
            g.setColor(Color.blue);
            for (Flight f : Datas.past) {
                if (this.parcelId != null && this.parcelId.longValue() != f.getParcelId()) {
                    continue;
                }
                Point d = warehouseIdToPoint((int) f.getDepartureId());
                Point a = warehouseIdToPoint((int) f.getArrivalId());
                if (f.getParcelId() == -1) {
                    if (this.showEmpty) {
                        g.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                0, new float[] { 9 }, 0));
                        g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                                AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
                    }
                } else {
                    g.setStroke(new BasicStroke(4));
                    g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                            AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
                }

                g.drawLine(AppClient.SIZECOEF(d.x), AppClient.SIZECOEF(d.y),
                        AppClient.SIZECOEF(a.x), AppClient.SIZECOEF(a.y));
            }
        }

        g.setColor(Color.black);
        for (Warehouse w : Datas.warehouses) {
            Point center = warehouseIdToPoint((int) w.getId());
            g.fillOval(AppClient.SIZECOEF(center.x - 5), AppClient.SIZECOEF(center.y - 5),
                    AppClient.SIZECOEF(10), AppClient.SIZECOEF(10));
        }
    }

    private Point warehouseIdToPoint(int id) {
        switch (id) {
        case 1:
            return new Point(305, 375);
        case 2:
            return new Point(371, 305);
        case 3:
            return new Point(362, 392);
        case 4:
            return new Point(427, 366);
        case 5:
            return new Point(246, 165);
        case 6:
            return new Point(346, 297);
        case 7:
            return new Point(383, 290);
        case 8:
            return new Point(294, 294);
        case 9:
            return new Point(270, 24);
        case 10:
            return new Point(123, 190);
        case 11:
            return new Point(247, 130);
        case 12:
            return new Point(255, 120);
        case 13:
            return new Point(200, 180);
        }
        return new Point();
    }

    public boolean isShowPast() {
        return showPast;
    }

    public void setShowPast(boolean showPast) {
        this.showPast = showPast;
    }

    public boolean isShowFuture() {
        return showFuture;
    }

    public void setShowFuture(boolean showFuture) {
        this.showFuture = showFuture;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public boolean isShowEmpty() {
        return showEmpty;
    }

    public void setShowEmpty(boolean showEmpty) {
        this.showEmpty = showEmpty;
    }
}
