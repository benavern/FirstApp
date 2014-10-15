package com.example.zezen.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;


public class dessin extends Activity implements SensorEventListener {

    //private float overflowX =0;
    //private float overflowY =0;

    //the sensor manager
    private SensorManager sManager;
    private String player_name ="";
    private String difficulte = "3";
    private SoundPool soundPool;
    private int soundID;
    private boolean loaded = false, plays=false;
    AudioManager audioManager;

    Context context;
    int duration = Toast.LENGTH_SHORT;

    Chronometer mChronometer;

    dessin_view v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dessin);

        context = getApplicationContext();

        mChronometer = new Chronometer(this);
        mChronometer.start();



        // récupération de la taille de l'écran
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //récupération du nom du player
        player_name = (String) getIntent().getSerializableExtra("player_name");
        String difficulte = (String) getIntent().getSerializableExtra("difficulte");
        v = new dessin_view(this, metrics.widthPixels, metrics.heightPixels, player_name, difficulte);

        //initioalisation des variables et création des colliders
        v.init(this);

        //les sons
        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });


        soundID = soundPool.load(this, R.raw.splash_sound, 1);


        setContentView(v);
    }


    public void playSound(View v, int id) {

//        if (id == 1 ) {
//            soundID = soundPool.load(this, R.raw.splash_sound, 1);
//        }
//        else if( id == 2 ){
//            soundID = soundPool.load(this, R.raw.perdu, 1);
//        }  pourquoi ça marche pas ??????????????????????

        if (loaded && !plays) {
            soundPool.play(soundID, 1, 1, 1, 0, 1f);
            //Toast.makeText(this, "Played sound", Toast.LENGTH_SHORT).show();
            //plays = true;
        }
    }


    //when this Activity starts
    @Override
    protected void onResume()
    {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        //else it will output the Roll, Pitch and Yawn values
        /*tv.setText("Orientation X (Roll) :"+ Float.toString(event.values[2]) +"\n"+
                "Orientation Y (Pitch) :"+ Float.toString(event.values[1]) +"\n"+
                "Orientation Z (Yaw) :"+ Float.toString(event.values[0]));
                */
        v.set_offsets(Math.round(event.values[0]), Math.round(event.values[1]));
        v.set_chrono(SystemClock.elapsedRealtime() - mChronometer.getBase());

        v.invalidate();
        //overflowY = event.values[1];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void victoire(){
        Toast toast = Toast.makeText(context, "Vous avez gagné, bravo !!!", duration);
        toast.show();
        Intent intent = new Intent(dessin.this, Victoire.class);
        intent.putExtra("player_name",player_name);
        intent.putExtra("chrono",v.get_chrono_string());
        startActivity(intent);
        this.finish();
    }

    public void perdu(){
        Toast toast = Toast.makeText(context, "Vous avez perdu!!!", duration);
        toast.show();
        Intent intent = new Intent(dessin.this, FirstActivity.class);
        startActivity(intent);
        this.finish();
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dessin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
