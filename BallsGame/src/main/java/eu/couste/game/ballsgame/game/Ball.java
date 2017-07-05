package eu.couste.game.ballsgame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by user on 18/10/2015.
 */
public class Ball {
    public static final int PLAYER = 1;
    public static final int FRIEND = 2;
    public static final int ENEMY = 3;
    public static final double NORMAL_SPEED = 100;
    private double speed = NORMAL_SPEED;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float radius;
    private int color;
    private int type;
    private Ball linkedWith;

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Ball(float x, float y, int type) {
        this.speed = NORMAL_SPEED*Settings.getInstance().getBallSpeed();
        this.x = x;
        this.y = y;
        this.type = type;
        switch (type) {
            case PLAYER:
                this.radius = 10;
                this.color = Color.GRAY;
                break;
            case FRIEND:
                this.radius = 20;
                this.color = Color.GREEN;
                break;
            case ENEMY:
                this.radius = 30;
                this.color = Color.RED;
                break;
            default:
                this.radius = 10;
                this.color = Color.BLUE;
        }
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setAngleVelocity(float angle) {
        this.vx = (float)Math.cos((double)angle);
        this.vy = (float)Math.sin((double)angle);
    }

    public void setVelocity(double x, double y) {
        this.vx = (float)x;
        this.vy = (float)y;
    }

    public int getColor() {
        return this.color;
    }

    public float getRadius() {
        return radius;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawCircle(this.x, this.y, this.radius, paint);
    }

    public void linkWith(Ball enemy) {
        this.linkedWith = enemy;
    }

    public void move() {
        this.x += this.vx*(float)GameThread.SKIP_TICKS*this.speed/1000;
        this.y += this.vy*(float)GameThread.SKIP_TICKS*this.speed/1000;
    }

    public void invertX() {
        this.vx *= -1;
    }

    public void invertY() {
        this.vy *= -1;
    }

    public boolean isInContact(Ball player) {
        float dx = this.x - player.x;
        float dy = this.y - player.y;
        float pr = this.radius + player.radius;
        float distance = (float)Math.sqrt(dx*dx+dy*dy);
        if(distance <= pr) {
            return true;
        } else {
            return false;
        }
    }

    public Ball getLinkedWith() {
        return linkedWith;
    }

    public int getType() {
        return type;
    }

    public void bigger(float rad) {
        float area = (float) Math.PI*this.radius*this.radius;
        area += (float) Math.PI*rad*rad;
        this.radius = (float)Math.sqrt(area/Math.PI);
    }

    public void smaller(float rad) {
        float area = (float) Math.PI*this.radius*this.radius;
        area -= (float) Math.PI*rad*rad;
        this.radius = (float)Math.sqrt(area/Math.PI);
    }

    public void setX(double x) {
        this.x = (float)x;
    }

    public void setY(double y) {
        this.y = (float)y;
    }

    public void setRadius(double radius) {
        this.radius = (float)radius;
    }
}
