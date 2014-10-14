package com.example.zezen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ZeZeN on 01/10/2014.
 */
public class Splash extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        Thread chrono = new Thread(){
            public void run(){
                try{
                    //essaie
                    sleep(3000);
                } catch(InterruptedException e){
                    //erreur
                    e.printStackTrace();
                } finally{
                    //sinon...
                    Intent i = new Intent("com.example.zezen.myapplication.FIRSTACTIVITY");
                    startActivity(i);
                }

            }
        };

        chrono.start();

    }
}
