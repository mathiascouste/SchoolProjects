package eu.couste.game.ballsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import eu.couste.game.ballsgame.game.Ball;
import eu.couste.game.ballsgame.game.Bonus;
import eu.couste.game.ballsgame.game.Game;
import eu.couste.game.ballsgame.game.GameThread;
import eu.couste.game.ballsgame.game.Settings;

/**
 * Created by user on 15/10/2015.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint paint = new Paint();
    private int w,h;
    private boolean run = true;

    private GameThread gameThread;

    private Game game;

    public Game getGame() {
        return game;
    }


    public GameView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        this.paint.setColor(Color.BLACK);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(2);

        getHolder().addCallback(this);
        this.gameThread = GameThread.createInstance(this);
        this.game = new Game();
        Settings.getInstance().putListeners(this, game);
    }

    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);

        // on dessine la jeu
        if(this.game != null) {
            this.game.draw(canvas);
        }
        drawTextLevel(canvas);
        drawBonusBar(canvas);
    }

    private void drawBonusBar(Canvas canvas) {
        int margin = 5;
        int barHeight = 20;
        int rm = this.w - margin;
        int lm = margin;
        int tm = this.h - barHeight - margin;
        int bm = this.h - margin;

        Paint p = new Paint();
        p.setColor(Color.BLACK);
        canvas.drawRect(lm, tm, rm, bm, p);

        int thickness = 2;
        lm += thickness;
        tm += thickness;
        bm -= thickness;
        rm -= thickness;
        int realWidth = this.w - 2*margin - 2*thickness;

        double coef = (double)this.game.getBonusBar()/(double)Game.MAX_BONUSBAR;

        int bonusWidth = (int)((double)realWidth*coef);

        p.setColor(Color.RED);
        canvas.drawRect(lm, tm, rm, bm, p);

        rm -= realWidth - bonusWidth;

        p.setColor(Color.GREEN);
        canvas.drawRect(lm, tm, rm, bm, p);

        int circleRadius = 8;
        for(int i = 0 ; i < Game.BONUS_LEVELS.length ; i++) {
            float yp = this.h - barHeight/2 - margin;
            float bonusCircleCoef = (float)Game.BONUS_LEVELS[i]/(float)Game.MAX_BONUSBAR;
            float xp = lm + bonusCircleCoef*realWidth - circleRadius/2;
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(2);
            canvas.drawCircle(xp, yp, circleRadius, p);
            if(this.game.getBonusBar() >= Game.BONUS_LEVELS[i]) {
                p.setColor(Color.YELLOW);
                p.setStyle(Paint.Style.FILL);
                canvas.drawCircle(xp, yp, circleRadius, p);
            }
        }
    }

    private void drawTextLevel(Canvas canvas) {
        Paint p = new Paint();
        p.setTextSize(40);
        p.setColor(Color.BLACK);
        String lvlStr = this.game.getSpeedLevel() + "." + this.game.getBallLevel();
        canvas.drawText(lvlStr, 5, 45, p);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(gameThread.getState()==Thread.State.TERMINATED) {
            gameThread=GameThread.createInstance(this);
        }
        this.w = holder.getSurfaceFrame().width();
        this.h = holder.getSurfaceFrame().height();

        this.game.setWidth(this.w);
        this.game.setHeight(this.h);
        this.game.createLevel();
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.w = width;
        this.h = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

    public void update() {
        this.game.update();
    }

    public void pause() {
        this.game.setPause(true);
    }

    public void stopPause() {
        this.game.setPause(false);
    }

    public void timerEnds(Bonus bonus) {
        bonus.actionEnd(this.game);
    }

    public void setInclineForTargetCalculation(float x, float y) {
        Ball player = this.game.getPlayer();
        float targetX = player.getX() + this.w*x;
        float targetY = player.getY() + this.h*y;
        this.game.setTarget(targetX,targetY);
    }
}
