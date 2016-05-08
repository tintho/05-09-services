package edu.uw.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private static final String TAG = "Main";

    private MusicService mService;
    private boolean mServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(mServiceBound){
            unbindService(this);
        }

        super.onStop();
    }

    //when "Start" button is pressed
    public void handleStart(View v){
        Log.i(TAG, "Start pressed");

        startService(new Intent(MainActivity.this, CountingService.class));

    }

    //when "Stop" button is pressed
    public void handleStop(View v){
        Log.i(TAG, "Stop pressed");

        stopService(new Intent(MainActivity.this, CountingService.class));

    }


    /* Media controls */
    public void playMedia(View v){
        startService(new Intent(MainActivity.this, MusicService.class));
    }

    public void pauseMedia(View v){
        if(mServiceBound){
            mService.pauseMusic();
        }
    }

    public void stopMedia(View v){
        if(mServiceBound){
            mService.stopMusic();
        }
        //stopService(new Intent(MainActivity.this, MusicService.class));
    }


    //when "Quit" button is pressed
    public void handleQuit(View v){
        finish(); //end the Activity
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "Activity destroyed");
        super.onDestroy();
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.v(TAG, "Service bound");
        MusicService.LocalBinder binder = (MusicService.LocalBinder) service; //cast to specific type
        ((TextView)findViewById(R.id.txtSongTitle)).setText(binder.getSongName());

        mService = binder.getService(); //save reference to the service
        mServiceBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.v(TAG, "Service unbound");
        mServiceBound = false;
    }
}
