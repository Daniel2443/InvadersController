package invaders.daniel.com.invaderscontroller;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import android.view.View.OnClickListener;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

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
    private String ip = "172.26.47.192";
    int cont = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                buttonClicked();
            }
        });


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        Log.d(TAG, "onCreate: Iinitializing Sensor");
        //Create Sensor
        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Acelerometer
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register sensor Listener
        SM.registerListener(MainActivity.this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered Acelerometer");


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);


        float y = sensorEvent.values[1];
        if (y < -1.5) {
            cont = 0;
            imageView.setImageResource(R.drawable.ship_left);
            try {
                client = new Socket(ip, 44444);  //connect to server
                printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
                printwriter.println("I");
                client.close();   //closing the connection
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (y > 1.5) {
            cont = 0;
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            imageView.setImageResource(R.drawable.ship_right);
            try {
                client = new Socket(ip, 44444);  //connect to server
                printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
                printwriter.println("D");
                client.close();   //closing the connection
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            imageView.setImageResource(R.drawable.ship);

            if (cont == 0) {
                try {
                    client = new Socket(ip, 44444);  //connect to server
                    printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
                    printwriter.println("E");
                    client.close();   //closing the connection

                } catch (Exception e) {
                    e.printStackTrace();
                }
                cont++;
            }
        }


    }
    private void buttonClicked(){
        System.out.println("Me tocarón bebé");
        try {
            client = new Socket(ip, 44444);  //connect to server
            printwriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            printwriter.println("F");
            client.close();   //closing the connection

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



        @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
