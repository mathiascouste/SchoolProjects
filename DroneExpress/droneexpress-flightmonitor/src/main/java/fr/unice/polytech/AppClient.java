package fr.unice.polytech;

import java.io.IOException;

import fr.unice.polytech.model.Datas;
import fr.unice.polytech.view.MainFrame;


public class AppClient {

    private static final double coef = 1.7;

    public static int SIZECOEF(int size) {
        return (int) (size * coef);
    }

    public static void main(String[] args) {
        loadImages();
        Datas.loadWarehouses();
        Datas.loadFlights();

        new MainFrame();
    }

    private static void loadImages() {
        ImageManager.create("maps").addPath("france",
                System.getProperty("user.dir") + "/images/france.gif");
        try {
            ImageManager.get("maps").loadImages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
