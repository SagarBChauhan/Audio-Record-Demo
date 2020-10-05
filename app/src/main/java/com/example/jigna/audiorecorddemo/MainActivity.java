package com.example.jigna.audiorecorddemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button btnStartRecord,btnStopRecord,btnPlay,btnStop;
    MediaRecorder recorder;
    MediaPlayer player;
    String StorePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ControlInitialization();
        ButtonClicks();
    }

    private void ControlInitialization() {
        if(CheckPermissionFromDevice())
            requestPermission();
        btnStartRecord = (Button)findViewById(R.id.btnStartRecord);
        btnStopRecord = (Button)findViewById(R.id.btnStopRecord);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnStop = (Button)findViewById(R.id.btnStop);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},1);
    }

    private boolean CheckPermissionFromDevice()
    {
        int WRITE_EXTERNAL_STORAGE_RESULT = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int RECORD_AUDIO_RESULT = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO);
        return WRITE_EXTERNAL_STORAGE_RESULT == PackageManager.PERMISSION_GRANTED && RECORD_AUDIO_RESULT == PackageManager.PERMISSION_GRANTED;
    }

    private void SetUpMediaRecorder()
    {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(StorePath);
    }


    private void ButtonClicks()
    {
        btnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckPermissionFromDevice())
                {
                    StorePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_record_audio.3gp";
                    SetUpMediaRecorder();
                    try {
                        recorder.prepare();
                        recorder.start();
                        Toast.makeText(getApplicationContext(),"Recording Started",Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    requestPermission();
                }
            }
        });

        btnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recorder.stop();
                Toast.makeText(getApplicationContext(),"Recording Stopped",Toast.LENGTH_LONG).show();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = new MediaPlayer();
                try {
                    player.setDataSource(StorePath);
                    player.prepare();
                    player.start();
                    Toast.makeText(getApplicationContext(),"Player Started",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                player.release();
                SetUpMediaRecorder();
                Toast.makeText(getApplicationContext(),"Player Stopped",Toast.LENGTH_LONG).show();
            }
        });
    }
}
