import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Hakim on 07/04/14.
 */
public class Cell implements Runnable{
    private String id;
    private boolean alive    = false;
    private boolean willLive = false;
    private ArrayList<Cell> neighbours;
    private Semaphore readerSemaphore = new Semaphore(8, true);

    @Override
    public void run() {
        try {
            while(true) {
                int livingNeighbours = livingNeighboursCount();
                nextStep(livingNeighbours);
                System.out.print("|");
            }
        } catch (InterruptedException e) {
            System.err.println("Cell " + this +" has been interrupted"); e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        if(alive) return id + " - alive";
        else      return id + " - dead";
    }

    public ArrayList<Cell> getNeighbours()                {return neighbours;}
    public void setNeighbours(ArrayList<Cell> neighbours) {this.neighbours = neighbours;}
    public void setId(int x, int y)     {id = Integer.toString(x) + ", " + Integer.toString(y);}
    public void setAlive(boolean alive) {this.alive = alive;}

    public synchronized boolean isAlive() throws InterruptedException {
        readerSemaphore.acquire();
       // System.out.println(this + " has been read");
        return alive;
    }

    public synchronized void nextStep(int livingNeighbours) throws InterruptedException {
        /*Determine the next step (whether the cell will live or not) based on the
          number of living neighbours and applying the rules of the Game of Life. */
        switch(livingNeighbours) {
            case 2: if(this.isAlive()) willLive = true; else willLive = false; break;
            case 3: willLive = true;
            default: willLive = false;
        }

        /*If all 8 readerSemaphores have been acquired, this cell has been read by all
          its neighbours, and it is safe for it to change states.
        if(readerSemaphore.availablePermits() == 0) {
            alive = willLive;
            readerSemaphore.release(8);
        }
        */
        /* Or is this the correct solution: */
        while(readerSemaphore.availablePermits() >0) wait();

        alive = willLive;
        readerSemaphore.release(8);

    }

    private int livingNeighboursCount() throws InterruptedException {
        int livingNeighbours = 0;
        for(Cell c : neighbours) { if (c.isAlive()) livingNeighbours++;}
        return livingNeighbours;
    }



}
