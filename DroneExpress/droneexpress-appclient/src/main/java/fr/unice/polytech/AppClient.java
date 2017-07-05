package fr.unice.polytech;

import java.time.LocalDateTime;
import java.util.*;

import fr.unice.polytech.business.Delivery;
import fr.unice.polytech.model.Datas;
import fr.unice.polytech.model.FreqTime;
import fr.unice.polytech.servicesconsumers.Itinerary;
import fr.unice.polytech.servicesconsumers.WarehouseManagement;


public class AppClient {

    private static Scanner sc = new Scanner(System.in);
    private static List<FreqTime> etaps = new ArrayList<>();

    public static void main(String[] args) {
        loadWarehouses();
        startInConsole();
    }

    private static void loadWarehouses() {
        Datas.warehouses.clear();
        Datas.warehouses.addAll(new WarehouseManagement().getWarehouses());
    }

    private static void startInConsole() {
        int fromFreq = 0;
        int toFreq = 0;
        int timePerFreq = 0;
        int stepSize = 0;

        System.out.println("Fréquence de démarrage");
        fromFreq = sc.nextInt();

        System.out.println("Fréquence d'arrêt");
        toFreq = sc.nextInt();

        System.out.println("Taille des étapes");
        stepSize = sc.nextInt();

        System.out.println("Temps par étape");
        timePerFreq = sc.nextInt();

        for (int i = fromFreq; i <= toFreq; i += stepSize) {
            etaps.add(new FreqTime(i, timePerFreq));
        }
        commencer();
    }

    private static void commencer() {
        System.out.println("Les etapes :");
        for (FreqTime ft : etaps) {
            runThis(ft);
        }
    }

    private static void runThis(FreqTime ft) {
        long sleepTime = 1000 / ft.frequency;
        long nStep = ft.frequency * ft.time;
        List<Thread> runnables = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < nStep; i++) {
            long departure = Datas.warehouses.get(rand.nextInt(Datas.warehouses.size())).getId();
            long arrival = departure;
            while (arrival == departure) {
                arrival = Datas.warehouses.get(rand.nextInt(Datas.warehouses.size())).getId();
            }

            final Delivery d = new Delivery();
            LocalDateTime prevu = LocalDateTime.now().plusMinutes(rand.nextInt(10000) + 5);
            d.setDepartureTime(prevu);
            d.setDepartureLocation(departure);
            d.setArrivalLocation(arrival);
            d.setParcelId((long) rand.nextInt(Integer.MAX_VALUE));

            runnables.add(new Thread(new Runnable() {

                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    Delivery del = new Itinerary().createItinerary(d);
                    if (del != null) {
                        ft.addSuccess();
                    }
                    ft.addTime(System.currentTimeMillis() - start);
                }
            }));
        }
        for (Thread r : runnables) {
            r.start();
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Thread r : runnables) {
            try {
                r.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ft);
    }
}
