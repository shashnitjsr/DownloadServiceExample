package com.example.shashwatsinha.intentserviceex;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by shashwatsinha on 11/11/15.
 */
public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("shashwat", "DownloadService onHandleIntent");
        try {
            URL url = new URL("http://raag.me//music/data/Hindi%20Movies/Chori%20Chori-(Babul%20Supriyo)/Aate%20Aate-Babul%20Supriyo::Raag.Me::.mp3");

            URLConnection connection = url.openConnection();

            connection.connect();
            if (isExternalStorageWriteable()) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());

                byte[] buffer = new byte[1024];
                File f = getMusicStorageDir();
                if (f == null) {
                    Log.d("shashwat", "onHandleIntent returning null");
                    //return;
                }

                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(f));
                while (bufferedInputStream.read(buffer) != -1) {
                    bufferedOutputStream.write(buffer);
                    bufferedOutputStream.flush();
                }

                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                Log.d("shashwat", "onHandleIntent ending");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public boolean isExternalStorageWriteable() {
        String state = Environment.getExternalStorageState();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public File getMusicStorageDir() {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "aate aate.mp3");
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (f != null && f.exists()) {
            return f;
        } else
            return null;
    }


}
