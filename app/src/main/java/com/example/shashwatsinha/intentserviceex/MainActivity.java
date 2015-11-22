package com.example.shashwatsinha.intentserviceex;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    boolean mBound = false;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("shashwat", "Now Service is connected");
            mBound = true;
            BoundService boundService = ((BoundService.LocalBinder) iBinder).getService();
            boundService.randomNumberGenerator();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    ServiceConnection diffServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("shashwat", "Different app bind service connected");
            Messenger msg = new Messenger(iBinder);
            Message message = Message.obtain(null, 1);
            try {
                msg.send(message);
            } catch (RemoteException de) {
                de.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), DownloadService.class);
                startService(intent);
            }
        });
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), DownloadServiceService.class);
        //startService(intent);
        //startService(intent);
        //startService(intent);
        Intent boundIntent = new Intent();
        boundIntent.setClass(getApplicationContext(), BoundService.class);
        bindService(boundIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        Intent boundDiffIntent = new Intent();
        System.out.println("shashwat is connected");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
