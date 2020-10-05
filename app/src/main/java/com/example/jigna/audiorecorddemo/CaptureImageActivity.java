package com.example.jigna.audiorecorddemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CaptureImageActivity extends AppCompatActivity {

    Button btnCaptureImage;
    ImageView CapturedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        ControlInitialization();
        ButtonClicks();
    }

    private void ControlInitialization() {
        if(!CheckPermission())
            requestPermission();
        btnCaptureImage = (Button)findViewById(R.id.btnCaptureImage);
        CapturedImage = (ImageView)findViewById(R.id.CapturedImage);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
    }

    private boolean CheckPermission()
    {
        int CAMERA_RESULT = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        return CAMERA_RESULT == PackageManager.PERMISSION_GRANTED;
    }

    private void ButtonClicks()
    {
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent objIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(objIntent, 7);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 7 && resultCode == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            CapturedImage.setImageBitmap(bitmap);
        }
    }
}
