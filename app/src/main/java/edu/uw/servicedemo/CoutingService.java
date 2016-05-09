package edu.uw.servicedemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CountingService extends IntentService {

    private static final String TAG = "Counting";

    public CountingService(){
        super("CountingService");
    }

    private int count = 0;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "Handling intent");

        for (count = 0; count < 10; count ++) {
            Log.v(TAG, "Count: " + count);
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


}
