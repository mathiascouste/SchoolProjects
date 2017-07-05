package fr.unice.polytech;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DisponibilitySlot {

    private static int nextId = 1;
    private static final Lock lock = new ReentrantLock();

    private long id;
    private long droneId;
    // begining of the disponibility slot ; null means now
    private LocalDateTime begining;
    // end if the distonibility slot ; null means the end of time
    private LocalDateTime end;

    public DisponibilitySlot() {
        lock.lock();
        this.id = nextId++;
        lock.unlock();
    }

    @Override
    public String toString() {
        return "id=" + id + " / droneId=" + droneId + " / begining=" + begining + " / end=" + end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDroneId() {
        return droneId;
    }

    public void setDroneId(long droneId) {
        this.droneId = droneId;
    }

    public LocalDateTime getBegining() {
        return begining;
    }

    public void setBegining(LocalDateTime begining) {
        this.begining = begining;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public long getDurationBetweenEndAndThis(LocalDateTime expectedDate) {
        if (this.end == null) {
            return Long.MAX_VALUE;
        } else if (expectedDate.isAfter(this.end)) {
            return -1;
        } else {
            return Duration.between(expectedDate, this.end).toMinutes();
        }
    }

    public long getLenght() {
        if (this.end == null) {
            return Long.MAX_VALUE;
        } else {
            return Duration.between(this.begining, this.end).toMinutes();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DisponibilitySlot)) {
            return false;
        }
        DisponibilitySlot other = (DisponibilitySlot) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
