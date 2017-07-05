package eu.couste.game.ballsgame.game;

/**
 * Created by user on 05/11/2015.
 */
public abstract class Bonus {
    private int step;
    private String name;

    public Bonus(String name, int step) {
        this.name = name;
        this.step = step;
    }

    public abstract void actionStart(Game game);
    public abstract void actionEnd(Game game);
    public abstract int getMillisDuration();

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
