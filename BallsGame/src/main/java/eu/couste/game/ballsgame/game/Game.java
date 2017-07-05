package eu.couste.game.ballsgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import eu.couste.game.ballsgame.GameActivity;

/**
 * Created by user on 18/10/2015.
 */
public class Game {
    public static final int MAX_BALL_LEVEL = 5;
    public static final int MAX_BONUS = 20;
    public static final int MAX_BONUSBAR = 100;
    public static final int BONUS_STEP_1 = 5;
    public static final int BONUS_STEP_2 = 25;
    public static final int BONUS_STEP_3 = 50;
    public static final int BONUS_STEP_4 = 100;
    public static final int [] BONUS_LEVELS = {BONUS_STEP_1, BONUS_STEP_2, BONUS_STEP_3, BONUS_STEP_4};

    private List<Bonus> bonuses;
    private int score = 0;
    private int ballLevel;
    private int speedLevel;
    private int bonusBar = MAX_BONUSBAR/2;
    private double speedToUse;
    private Random rand;
    private boolean pause = false;
    private float width, height;
    private double targetX, targetY;
    private Ball player;
    private List<Ball> balls;
    private double playerSize = 10;
    private double idealBonusSize = 20;

    private boolean movingBalls = true;
    private double bonusSpeed = 1;

    public Game() {
        this(2, 1);
    }

    public Game(int ballLevel, int speedLevel) {
        this.speedLevel = speedLevel;
        this.ballLevel = ballLevel;
        this.balls = new ArrayList<>();
        this.initBonuses();
    }

    private void initBonuses() {
        this.bonuses = new ArrayList<>();
        this.bonuses.add(new Bonus("stopMove", BONUS_STEP_1) {
            @Override
            public void actionStart(Game game) {
                movingBalls = false;
            }

            @Override
            public void actionEnd(Game game) {
                movingBalls = true;
            }

            @Override
            public int getMillisDuration() {
                return 1000;
            }
        });
        this.bonuses.add(new Bonus("doublespeed", BONUS_STEP_2) {
            @Override
            public void actionStart(Game game) {
                bonusSpeed = 2;
            }
            @Override
            public void actionEnd(Game game) {
                bonusSpeed = 1;
            }

            @Override
            public int getMillisDuration() {
                return 2000;
            }
        });
        this.bonuses.add(new Bonus("triplespeed", BONUS_STEP_3) {
            @Override
            public void actionStart(Game game) {
                bonusSpeed = 3;
            }
            @Override
            public void actionEnd(Game game) {
                bonusSpeed = 1;
            }

            @Override
            public int getMillisDuration() {
                return 5000;
            }
        });
    }

    public void createLevel() {
        this.balls.clear();
        rand = new Random();
        this.pause = false;

        this.player = new Ball(this.width/2, this.height/2, Ball.PLAYER);
        this.player.setSpeed(Ball.NORMAL_SPEED*speedLevel);
        this.player.setRadius(this.playerSize);
        this.targetX = this.width/2;
        this.targetY = this.height/2;
        for(int i = 0 ; i < this.ballLevel ; i++) {
            Ball friend = new Ball(this.width*this.rand.nextFloat(), this.height*this.rand.nextFloat(), Ball.FRIEND);
            Ball enemy = new Ball(this.width*this.rand.nextFloat(), this.height*this.rand.nextFloat(), Ball.ENEMY);
            friend.setAngleVelocity((float) (rand.nextFloat()*2*Math.PI));
            enemy.setAngleVelocity((float) (rand.nextFloat()*2*Math.PI));
            friend.linkWith(enemy);
            enemy.linkWith(friend);
            friend.setSpeed(Ball.NORMAL_SPEED * Settings.getInstance().getBallSpeed() * speedLevel);
            enemy.setSpeed(Ball.NORMAL_SPEED*Settings.getInstance().getBallSpeed()*speedLevel);
            this.balls.add(friend);
            this.balls.add(enemy);
        }
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Ball getPlayer() {
        return this.player;
    }

    public void draw(Canvas canvas) {
        for(Ball ball : this.balls) {
            ball.draw(canvas);
        }
        if(player != null) {
            player.draw(canvas);
            Paint p = new Paint();
            p.setColor(Color.BLUE);
            p.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(this.player.getX(), this.player.getY(), (float)this.idealBonusSize, p);
        }

    }

    public void update(){
        if(!pause) {
            // update player's ball velocitys
            updatePlayerPosition();
            if(movingBalls) {
                for(Ball ball : this.balls) {
                    moveBall(ball);
                }
            }

            // check contact
            Set<Ball> toRemove = new HashSet<>();
            Set<Ball> collateral = new HashSet<>();
            for(Ball ball : this.balls) {
                if(ball.isInContact(this.player) && !collateral.contains(ball)) {
                    toRemove.add(ball);
                    collateral.add(ball.getLinkedWith());
                }
            }
            this.balls.removeAll(toRemove);
            this.balls.removeAll(collateral);
            for(Ball b : toRemove) {
                if(b.getType() == Ball.FRIEND) {
                    this.player.bigger(b.getRadius());
                } else {
                    this.player.smaller(b.getRadius());
                }
            }

            this.playerSize = this.player.getRadius();
            if(this.player.getRadius() < 5) {
                this.pause = true;
                GameActivity.currentGameActivity.mHandler.sendMessage(Message.obtain(GameActivity.currentGameActivity.mHandler, GameActivity.PopupHandler.GroupsActivity.CREATE_GAMEOVER_POPUP));
            }
            if(this.balls.size() == 0) {
                double diff = Math.abs(this.playerSize - this.idealBonusSize);
                double bonus = MAX_BONUS / (diff+1);
                this.bonusBar += bonus;
                if(this.bonusBar > MAX_BONUSBAR) {
                    this.bonusBar = MAX_BONUSBAR;
                }
                this.pause = true;
                GameActivity.currentGameActivity.mHandler.sendMessage(Message.obtain(GameActivity.currentGameActivity.mHandler, GameActivity.PopupHandler.GroupsActivity.CREATE_NEXTLEVEL_POPUP));
            }
        }
    }

    private void updatePlayerPosition() {
        double posX = this.player.getX();
        double posY = this.player.getY();
        double dX = targetX-posX, dY = targetY-posY;
        double dist = Math.sqrt(dX * dX + dY * dY);
        double parcourable = ((float)GameThread.SKIP_TICKS)*Ball.NORMAL_SPEED*this.speedLevel*bonusSpeed/1000;

        if(dist > parcourable) {
            double coef = parcourable/dist;
            double nX = posX + dX*coef;
            double nY = posY + dY*coef;
            this.player.setX(nX);
            this.player.setY(nY);
        } else {
            this.player.setX(targetX);
            this.player.setY(targetY);
        }
        if(this.player.getX() < 0) {
            this.player.setX(0);
        } else if(this.player.getX() > this.width) {
            this.player.setX(this.width);
        }
        if(this.player.getY() < 0) {
            this.player.setY(0);
        } else if(this.player.getY() > this.height) {
            this.player.setY(this.height);
        }
    }

    private void moveBall(Ball ball) {
        float lx = ball.getX();
        float ly = ball.getY();
        ball.move();

        /// check rebound
        if(ball.getX() < 0 || ball.getX() > this.width) {
            ball.invertX();
        }
        if(ball.getY() < 0 || ball.getY() > this.height) {
            ball.invertY();
        }
    }

    public void setTarget(float x, float y) {
        System.out.println("x=" + x + " y=" + y);
        this.targetX = x;
        this.targetY = y;
    }

    public void nextLevel() {
        this.ballLevel++;
        if(this.ballLevel == MAX_BALL_LEVEL +1) {
            this.speedLevel++;
            this.ballLevel -= MAX_BALL_LEVEL;
        }
    }

    public void setLevel(int ballLevel, int speedLevel) {
        this.ballLevel = ballLevel;
        this.speedLevel = speedLevel;
    }

    public int getSpeedLevel() {
        return this.speedLevel;
    }
    public int getBallLevel() {
        return this.ballLevel;
    }

    public int getBonusBar() {
        return this.bonusBar;
    }

    public void activateBonus() {
        int bStep = -1;
        for(int i = 0 ; i < BONUS_LEVELS.length ; i++) {
            if(this.bonusBar >= BONUS_LEVELS[i]) {
                bStep = i;
            }
        }
        if(bStep != -1) {
            int step = BONUS_LEVELS[bStep];
            List<Bonus> okBonus = new ArrayList<>();
            for(Bonus b : this.bonuses) {
                if(b.getStep() == step) {
                    okBonus.add(b);
                }
            }
            Random rand = new Random();
            Bonus theBonus = okBonus.get(rand.nextInt(okBonus.size()));
            theBonus.actionStart(this);
            GameThread.getInstance().setTimer(theBonus.getMillisDuration(), theBonus);
            this.bonusBar -= step;
        }
    }
}
