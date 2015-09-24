package com.gli.mymediaplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback2 {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SurfaceView surface;
    private MediaPlayer mp1;
    private MediaPlayer mp2;
    private MediaPlayer current;
    private SurfaceHolder holder;
    private Button switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_main);

        surface = (SurfaceView) findViewById(R.id.sv);
        holder = surface.getHolder();
        holder.addCallback(this);

        switchButton = (Button) findViewById(R.id.switchbutton);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchsource();

            }
        });
    }

    private void switchsource() {
        Log.d(TAG, "onClick");
        if (current == mp1){
            Log.d(TAG,"onClick mp1");
            try {
                mp1.stop();
                mp1.setDisplay(null);
                mp1.reset();

                mp2.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.movie2));
                mp2.setDisplay(holder);
                mp2.prepareAsync();
                current = mp2;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG,"onClick mp2");
            try {
                mp2.stop();
                mp2.setDisplay(null);
                mp2.reset();

                mp1.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.movie1));
                mp1.setDisplay(holder);
                mp1.prepareAsync();
                current = mp1;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        mp1 = new MediaPlayer();
        mp1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onPrepared mp1");
                mp1.start();
            }
        });
        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switchsource();
            }
        });
        mp2 = new MediaPlayer();
        mp2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG,"onPrepared mp2");
                mp2.start();
            }
        });
        mp2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switchsource();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        mp1.stop();
        mp1.release();
        mp2.stop();
        mp2.release();
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
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG,"surfaceCreated");
        holder = surfaceHolder;

        try {
            mp1.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.movie1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp1.setDisplay(holder);
        mp1.prepareAsync();
        current = mp1;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
