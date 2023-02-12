package com.pollub.lab12example4;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // device sensor manager
    private SensorManager SensorManage;

    // define the compass picture that will be use
    private ImageView compassimage;

    // record the angle turned of the compass picture
    private float DegreeStart = 0f;

    TextView DegreeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        compassimage = (ImageView) findViewById(R.id.compass_image);

        // TextView that will display the degree
        DegreeTV = (TextView) findViewById(R.id.DegreeTV);

        // initialize your android device sensor capabilities
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        SensorManage.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // code for system's orientation sensor registered listeners
        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        if (degree<210&& degree>150){
            DegreeTV.setText("south");
        }
        else if (degree>210 && degree<240){
            DegreeTV.setText("soutwest");
        }
        else if (degree>240 && degree<280){
            DegreeTV.setText("west");
        }
        else if (degree>280 && degree<330){
            DegreeTV.setText("northwest");
        }
        else if (degree>330 && degree<30){
            DegreeTV.setText("north");
        }
        else if (degree>30 && degree<60){
            DegreeTV.setText("northeast");
        }
        else if (degree>60 && degree<105){
            DegreeTV.setText("east");
        }
        else if (degree>105 && degree<150){
            DegreeTV.setText("souteast");
        }

        //DegreeTV.setText("Heading: " + Float.toString(degree) + " degrees");

        // rotation animation - reverse turn degree degrees
        RotateAnimation ra = new RotateAnimation(
                DegreeStart,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        // set the compass animation after the end of the reservation status
        ra.setFillAfter(true);

        // set how long the animation for the compass image will take place
        ra.setDuration(210);

        // Start animation of compass image
        compassimage.startAnimation(ra);
        DegreeStart = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}