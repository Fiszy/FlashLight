package com.fiszy.flashlight;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    Camera camera;
    Camera.Parameters parameters;
    boolean isFlash = false;
    boolean isOn = false;
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (ImageButton)findViewById(R.id.imageButton);
        about = (TextView)findViewById(R.id.about);

        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
           // requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            camera = Camera.open();
            parameters = camera.getParameters();
            isFlash = true;
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFlash)
                {
                  if (!isOn)
                  {
                      imageButton.setImageResource(R.drawable.on);
                      parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                      camera.setParameters(parameters);
                      camera.startPreview();
                      isOn = true;
                  }
                  else
                  {
                      imageButton.setImageResource(R.drawable.off);
                      parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                      camera.setParameters(parameters);
                      camera.stopPreview();
                      isOn = false;
                  }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error Message...");
                    builder.setMessage("PHone does not have a flashlight");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutUs.class));
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (camera!=null)
        {
            camera.release();
            imageButton.setImageResource(R.drawable.off);
            camera = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
        camera = Camera.open();
        parameters = camera.getParameters();
        isFlash = true;
    }
}
