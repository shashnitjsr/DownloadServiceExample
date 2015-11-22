package com.example.shashwatsinha.intentserviceex;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by shashwatsinha on 14/11/15.
 */
public class DownloadServiceService extends Service {


    ServiceHandler handler;


    class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("shashwat", "com.example.shashwatsinha.intentserviceex.DownloadService.DownloadServiceService onhandleMessage handleMessage for " + msg.arg1);
            synchronized (this) {
                try {
                    wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d("shashwat", "com.example.shashwatsinha.intentserviceex.DownloadService.DownloadServiceService onhandleMessage handleMessage for " + msg.arg1 + "stooping here");
            stopSelf(msg.arg1);

        }
    }


    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("Service thread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Looper looper = thread.getLooper();
        handler = new ServiceHandler(looper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("shashwat", "com.example.shashwatsinha.intentserviceex.DownloadService.DownloadServiceService onstartCommand startId " + startId);
        Message msg = Message.obtain();
        msg.arg1 = startId;
        handler.sendMessage(msg);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
