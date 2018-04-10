package invaders.daniel.com.invaderscontroller;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private static final String TAG = "MainActivity";
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        Log.d(TAG, "onCreate: Iinitializing Sensor");
        //Create Sensor
        SM = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //Acelerometer
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register sensor Listener
        SM.registerListener(MainActivity.this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered Acelerometer");




    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ImageView imageView = (ImageView)findViewById(R.id.imageView);



        float y = sensorEvent.values[1];
        if(y < -3){
            System.out.println("Izquierda ");
            imageView.setImageResource(R.drawable.ship_left);
        }
        else if (y >3){
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            System.out.println("Derecha ");
            imageView.setImageResource(R.drawable.ship_right);
        }
        else if (-1 < y || y < 1) {
            System.out.println("Estable ");
            imageView.setImageResource(R.drawable.ship);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
