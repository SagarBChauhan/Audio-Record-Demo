package com.example.jigna.audiorecorddemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class CaptureVideoActivity extends AppCompatActivity {

    Button btnStartVideo;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);
        ControlInitialization();
        ButtonClicks();
    }

    private void ButtonClicks() {
        btnStartVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent objIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
              //  File Video_File = GetFilePath();
               // Toast.makeText(getApplicationContext(),Video_File.getAbsolutePath().toString(),Toast.LENGTH_LONG).show();
                //Uri Video_Uri = Uri.fromFile(Video_File);
                //objIntent.putExtra(MediaStore.EXTRA_OUTPUT,Video_File);
                objIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                startActivityForResult(objIntent,1);
            }
        });

    }



    private File GetFilePath()
    {
        String External = Environment.getExternalStorageDirectory().getAbsolutePath();
        File folder = new File(External + "/test");

        if(folder.exists())
        {
            folder.mkdir();
        }
        File Video_File = new File(folder,"/sample_video.mp4");
        return Video_File;
    }

    private void ControlInitialization()
    {
        btnStartVideo = (Button)findViewById(R.id.btnStartVideo);
    }
}
