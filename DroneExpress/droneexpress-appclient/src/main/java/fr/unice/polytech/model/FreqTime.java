package fr.unice.polytech.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class FreqTime {

    public int frequency;
    public int time;
    public long tempsReponse;
    private int n = 0;
    private int success = 0;

    private Lock lock = new ReentrantLock();

    public FreqTime(int frequency, int time) {
        this.frequency = frequency;
        this.time = time;
        this.tempsReponse = -1;
    }

    public void addTime(long time) {
        lock.lock();
        tempsReponse = (tempsReponse * n + time) / (n + 1);
        n++;
        lock.unlock();
    }

    @Override
    public String toString() {
        return "freq=" + frequency + " - time=" + time + " - response time=" + tempsReponse
                + " - success=" + success + "/" + n;
    }

    public void addSuccess() {
        lock.lock();
        success++;
        lock.unlock();
    }
}
