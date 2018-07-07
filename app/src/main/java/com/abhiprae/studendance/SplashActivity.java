package com.abhiprae.studendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by Abhiprae on 2/17/2017.
 */

public class SplashActivity extends AppCompatActivity{

    ProgressBar progress;
    SharedPreferences pref;
    public static final String MYPREF = "STUDENDANCE";
    String first_run="";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = getSharedPreferences(MYPREF,MODE_PRIVATE);
        first_run = pref.getString("first_run","yes");
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
            }
        }).start();
    }
    private void doWork() {
        for (int i=0; i<100; i+=10) {
            try {
                Thread.sleep(300);
                progress.setProgress(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void startApp() {
        if(first_run=="yes") {
            Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
