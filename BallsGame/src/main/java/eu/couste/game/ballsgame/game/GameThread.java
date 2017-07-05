package eu.couste.game.ballsgame.game;

import android.graphics.Canvas;

import java.util.Random;

import eu.couste.game.ballsgame.GameView;

/**
 * Created by user on 16/10/2015.
 */
public class GameThread extends Thread {
    private static GameThread instance;

    public static GameThread createInstance(GameView gameView) {
        instance = new GameThread(gameView);
        return instance;
    }
    public static GameThread getInstance() {
        return instance;
    }

    private final static int FRAMES_PER_SECOND = 30;
    public final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private final GameView view;
    private boolean running = false;
    private long timer;
    private Bonus bonusTimer;

    private GameThread(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    // démarrage du thread
    @Override
    public void run()
    {
        long startTime;
        long sleepTime;

        while (running)
        {
            startTime = System.currentTimeMillis();

            synchronized (view.getHolder()) {
                view.update();
            }

            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.doDraw(c);
                }
            }
            finally
            {
                if (c != null) {view.getHolder().unlockCanvasAndPost(c);}
            }
            sleepTime = SKIP_TICKS-(System.currentTimeMillis() - startTime);
            if(timer > 0) {
                timer -= SKIP_TICKS;
                if(timer <= 0) {
                    this.view.timerEnds(bonusTimer);
                }
            }
            try {
                if (sleepTime >= 0) {sleep(sleepTime);}
            }
            catch (Exception e) {}
        }
    }

    public void setTimer(long duration, Bonus bonus) {
        this.timer = duration;
        this.bonusTimer = bonus;
    }
}
