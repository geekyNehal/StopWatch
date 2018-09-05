package com.geekynehal.stopwatch;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{
    DrawerLayout drawerLayout;
    Button startPauseButton,resetButton,lapButton;
    TextView txtTimer;
    LinearLayout container;
    NavigationView nvDrawer;
    //Handler is used to perform a task.
    Handler customHandler=new Handler();

    long startTime=0L,timeinMilliseconds=0L,timeSwapBuff=0L,updateTime=0L;
    boolean watchRunning=false;

    /*Since Updating the time at each milliseconds will take lot of processing,so we perform this task on a thread using
     Runnable(an Interface) and Handler*/
    Runnable updateTimeThread=new Runnable() {
        @Override
        public void run()
        {
            //Runnable Interface requires of the class to implement this method.
            timeinMilliseconds=SystemClock.uptimeMillis()-startTime;
            updateTime=timeSwapBuff+timeinMilliseconds;
            //updateTime is in milliseconds so we divide it with 1000 to get in seconds.
            int secs=(int) (updateTime/1000);
            int min=secs/60;
            int milliseconds=(int) (updateTime%1000);
            //The %d is used for formatting the time string as 00:01:454 so that it appears like time.
            txtTimer.setText(""+min+":"+String.format("%02d",secs)+":"+String.format("%03d",milliseconds));
            customHandler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for connecting MainActivity class with app_bar_main xml file
        setContentView(R.layout.activity_main);

        //Here we are telling the compiler about the variable used which implies some id in the xml file through findViewById()
        Toolbar toolbar=findViewById(R.id.toolbar);
        nvDrawer=findViewById(R.id.nav_view);
        startPauseButton=findViewById(R.id.startPauseButton);
        resetButton=findViewById(R.id.resetButton);
        lapButton=findViewById(R.id.lapButton);
        txtTimer=findViewById(R.id.timer);
        container=findViewById(R.id.container);

        //setting the toolbar as default toolbar
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);

        //for hamburger icon to be visible
         ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,
                 toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
         drawerLayout.setDrawerListener(toggle);

         //animating hamburger icon (changing its state whenever clicked)
        toggle.syncState();

        //Handling the click event of  Start buttons
        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!watchRunning) {
                    //This initialises startTime in milliseconds.
                    startTime = SystemClock.uptimeMillis();
                    //Here the Handler is attached with the thread to deliver message to Runnable
                    customHandler.postDelayed(updateTimeThread, 0);
                    watchRunning=true;
                    startPauseButton.setText("Pause");
                    resetButton.setVisibility(View.INVISIBLE);
                    lapButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    timeSwapBuff+=timeinMilliseconds;
                    //this will remove those runnables that have not yet begun processing from the queue.
                    customHandler.removeCallbacks(updateTimeThread);
                    startPauseButton.setText("Start");
                    resetButton.setVisibility(View.VISIBLE);
                    watchRunning=false;
                    lapButton.setVisibility(View.INVISIBLE);
                }
            }
        });
       resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!watchRunning) {
                     timeinMilliseconds=timeSwapBuff=0L;
                     updateTime=0L;
                     startTime=SystemClock.uptimeMillis();
                     customHandler.postDelayed(updateTimeThread,0);

                     resetButton.setVisibility(View.INVISIBLE);
                     startPauseButton.setText("Pause");
                     watchRunning=true;
                     lapButton.setVisibility(View.VISIBLE);
                }
                else
                {

                }
            }
        });
        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater is a class used to instantiate xml file into view object.Here we used inflater to show all the laps
                through row (a xml file) to the view*/
                LayoutInflater inflater=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflate gives us object references  to that layout to call findViewById on.
                View addView=inflater.inflate(R.layout.row,null);
                TextView txtValue=addView.findViewById(R.id.textcontent);
                txtValue.setText(txtTimer.getText());
                container.addView(addView);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(nvDrawer))
        {
            drawerLayout.closeDrawers();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
