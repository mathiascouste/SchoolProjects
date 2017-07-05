package eu.couste.game.ballsgame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by renevier-gonin on 08/10/2014.
 */
public class AcceleroListener  implements SensorEventListener {

    final float marge = 0.3f;
    final int PAS = 1;
    final int PAS_MAX = 15;

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    GameView view;

    // les 3 valeurs de l'accÃ©lÃ©rometre
    private float x;
    private float y;
    private float z;

    int multiX = 0;
    int multiY = 0;

    private float[]  gravity;
    private final float alpha = 0.8f; // http://developer.android.com/reference/android/hardware/SensorEvent.html
    long timestamp = -1;

    float vx = 0;

    public boolean isRunning() {
        return running;
    }

    boolean running = false;

    public AcceleroListener(SensorManager sm, GameView v) {
        this.mSensorManager = sm;
        this.view = v;

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
        running = false;
    }

    public void start() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        running = true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        x = -sensorEvent.values[0];
        y = sensorEvent.values[1];

        this.view.setInclineForTargetCalculation(x,y);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
