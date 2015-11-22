package com.example.shashwatsinha.intentserviceex;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by shashwatsinha on 16/11/15.
 */
public class BoundService extends Service {

    private IBinder mBinder = new LocalBinder();

    class LocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void randomNumberGenerator() {
        Toast.makeText(getApplicationContext(), "Random Number Generated " + new Random().nextInt(100), Toast.LENGTH_LONG).show();
    }
}
