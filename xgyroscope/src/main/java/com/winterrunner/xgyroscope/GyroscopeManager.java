package com.winterrunner.xgyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * author: L.K.X
 * created on: 2017/7/20 下午3:54
 * description:
 */

public class GyroscopeManager implements SensorEventListener {

    // 将纳秒转化为秒
    private static final float NS2S = 1.0f / 1000000000.0f;

    private List<XImageView> list_views = new ArrayList<>();
    private SensorManager sensorManager;

    private long endTimestamp;
    private double angleX;
    private double angleY;

    //0到π/2
    private double maxAngle = Math.PI / 3;


    public void addView(XImageView xImageView) {
        if (xImageView != null && !list_views.contains(xImageView)) {
            list_views.add(xImageView);
        }
    }

    public void register(Context context) {
        if (sensorManager == null) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        Sensor mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);

        endTimestamp = 0;
        angleX = 0;
        angleY = 0;
    }

    public void unregister() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (endTimestamp == 0) {
            endTimestamp = event.timestamp;
            return;
        }
        angleX += event.values[0] * (event.timestamp - endTimestamp) * NS2S;
        angleY += event.values[1] * (event.timestamp - endTimestamp) * NS2S;

        if (angleX > maxAngle) {
            angleX = maxAngle;
        }
        if (angleX < -maxAngle) {
            angleX = -maxAngle;
        }


        if (angleY > maxAngle) {
            angleY = maxAngle;
        }
        if (angleY < -maxAngle) {
            angleY = -maxAngle;
        }

        for (XImageView view : list_views) {
            if (view != null) {
                view.update(angleY / maxAngle, angleX / maxAngle);
            }
        }
        endTimestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
