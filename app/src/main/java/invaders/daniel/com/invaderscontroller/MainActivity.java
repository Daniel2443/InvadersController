package invaders.daniel.com.invaderscontroller;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.*;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private static final String TAG = "MainActivity";
    private ImageView imageView;
    private Socket client;
    private PrintWriter printwriter;
    private EditText textField;
    private Button button;
    private String messsage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());



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
            try {
                while(true) {
                    client = new Socket("172.26.47.192", 44444);  //connect to server
                    printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

                    //printwriter.flush();

                    //printwriter.close();
                    client.close();   //closing the connection
                }
            } catch (Exception e) {
                e.printStackTrace();}
        }
        else if (y >3){
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            System.out.println("Derecha ");
            imageView.setImageResource(R.drawable.ship_right);
            try {
                while(true) {
                    client = new Socket("172.26.47.192", 44444);  //connect to server
                    printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
                    //printwriter.flush();

                    //printwriter.close();
                    client.close();   //closing the connection
                }
            } catch (Exception e) {
                e.printStackTrace();}
        }
        else if (-1 < y || y < 1) {
            System.out.println("Estable ");
            imageView.setImageResource(R.drawable.ship);
            try {
                while(true) {
                    client = new Socket("172.26.47.192", 44444);  //connect to server
                    printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
                    //printwriter.flush();

                    //printwriter.close();
                    client.close();   //closing the connection
                }
            } catch (Exception e) {
                e.printStackTrace();}
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
