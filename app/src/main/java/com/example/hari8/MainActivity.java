package com.example.hari8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textview;
    private TextView x;
    private TextView y;
    private TextView z;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Sensor orientationSensor;
    private Vibrator vibrator;
    private boolean isProximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = findViewById(R.id.X);
        y = findViewById(R.id.Y);
        z = findViewById(R.id.Z);
        textview = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//            orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            isProximitySensor = true;
        }
        else{
            textview.setText("Sensor tidak tidak aktif");
            isProximitySensor = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        textview.setText(sensorEvent.values[0] + " cm");
//        x.setText("X : " + sensorEvent.values[0]);
//        y.setText("Y : " + sensorEvent.values[1]);
//        z.setText("Z : " + sensorEvent.values[2]);
//

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else{
            vibrator.vibrate(500);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isProximitySensor){
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
//            sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isProximitySensor){
            sensorManager.unregisterListener(this);
        }

    }
}