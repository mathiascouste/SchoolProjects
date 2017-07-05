package eu.couste.game.ballsgame.game;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import eu.couste.game.ballsgame.AcceleroListener;
import eu.couste.game.ballsgame.GameActivity;
import eu.couste.game.ballsgame.GameView;
import eu.couste.game.ballsgame.ShakeListener;

/**
 * Created by user on 21/10/2015.
 */
public class Settings {
    private static Settings instance;

    public static Settings getInstance() {
        if(instance==null) instance = new Settings();
        return instance;
    }

    public static final int MOVE_CLICK = 1;
    public static final int MOVE_TOUCH = 2;
    public static final int MOVE_INCLINE = 3;
    public static final int BONUS_SHAKE = 1;
    public static final int BONUS_LONGPRESS = 2;
    public static final int BONUS_SCROLL = 3;

    private double ballSpeed = 1;
    private double sensitivity = 1;
    private int moveType = MOVE_CLICK;
    private int bonusType = BONUS_SHAKE;

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public double getBallSpeed() {
        return this.ballSpeed;
    }

    public void putListeners(final GameView view, final Game game) {
        if(moveType == MOVE_CLICK) {
            view.setOnTouchListener(new View.OnTouchListener() {
                Game game;
                public View.OnTouchListener setGame(Game game) {
                    this.game = game;
                    return this;
                }
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        this.game.setTarget(event.getX(), event.getY());
                    }
                    return false;
                }
            }.setGame(game));
        } else if (moveType == MOVE_TOUCH) {
            view.setOnTouchListener(new View.OnTouchListener() {
                Game game;
                public View.OnTouchListener setGame(Game game) {
                    this.game = game;
                    return this;
                }
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
                        this.game.setTarget(event.getX(), event.getY());
                    } else if(event.getAction() == MotionEvent.ACTION_UP){
                        this.game.setTarget(this.game.getPlayer().getX(), this.game.getPlayer().getY());
                    }
                    return true;
                }
            }.setGame(game));
        } else if (moveType == MOVE_INCLINE) {
            SensorManager sm = (SensorManager) GameActivity.currentGameActivity.getSystemService(Context.SENSOR_SERVICE);
            AcceleroListener aL = new AcceleroListener(sm, view);
            aL.start();
        }
        if(bonusType == BONUS_SHAKE) {
            new ShakeListener(view.getContext()).setOnShakeListener(new ShakeListener.OnShakeListener() {
                @Override
                public void onShake() {
                    game.activateBonus();
                }
            });
        } else if(bonusType == BONUS_LONGPRESS) {
            final GestureDetector gd = new GestureDetector(view.getContext(), new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    view.getGame().activateBonus();
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return false;
                }
            });
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gd.onTouchEvent(event);
                    return false;
                }
            });
        } else if(bonusType == BONUS_SCROLL) {

            final GestureDetector gd = new GestureDetector(view.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2,
                                       float velocityX, float velocityY) {
                    view.getGame().activateBonus();
                    return true;
                }

            });
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gd.onTouchEvent(event);
                    return false;
                }
            });
        }
    }

    public void setBonusType(int bonusType) {
        this.bonusType = bonusType;
    }
}
