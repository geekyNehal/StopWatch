package com.geekynehal.stopwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Removing title bar from splash activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Removing Notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setting content view of the activity.
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){

                                @Override
                                public void run() {
                                    Intent startAct=new Intent(SplashActivity.this,MainActivity.class);
                                    startActivity(startAct);
                                    finish();
                                }
                            }
        ,1000);

    }


}
