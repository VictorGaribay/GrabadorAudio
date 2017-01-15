package com.androidmorefast.moden.appgrabadoraaudio;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
    Button play,stop, grabar;
    private MediaRecorder miGrabacion;
    private String outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button)findViewById(R.id.btnPlay);
        stop=(Button)findViewById(R.id.btnStop);
        grabar =(Button)findViewById(R.id.btnGrabar);

        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.3gp";;



        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    miGrabacion =new MediaRecorder();
                    miGrabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
                    miGrabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    miGrabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    miGrabacion.setOutputFile(outputFile);
                    miGrabacion.prepare();
                    miGrabacion.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                grabar.setEnabled(false);
                grabar.setVisibility(View.INVISIBLE);
                stop.setEnabled(true);
                stop.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "La grabación comenzó", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miGrabacion.stop();
                miGrabacion.release();
                miGrabacion = null;

                stop.setEnabled(false);
                stop.setVisibility(View.INVISIBLE);

                play.setEnabled(true);
                grabar.setEnabled(true);
                grabar.setVisibility(View.VISIBLE);



                Toast.makeText(getApplicationContext(), "El audio grabado con éxito",Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "reproducción de audio", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
}