package com.example.fleetmonitoring;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Splashscreen extends AppCompatActivity {

    //Variables
    Animation fromBottom;
    ImageView carSS;
    ImageView locSS;
    Animation fromtop;
    WebView webviewload;
    ProgressBar progressBar;
    int GET_PERMISSION = 1;

    //variables end


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Webview
        webviewload = (WebView) findViewById(R.id.webviewload);
        webviewload.loadUrl("https://mysterious-spire-66642.herokuapp.com/temp");
        //webview ends

        //animation area
        carSS = (ImageView) findViewById(R.id.carSS);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        carSS.setAnimation(fromBottom);

        locSS = (ImageView) findViewById(R.id.locSS);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        locSS.setAnimation(fromtop);
        //animation ends

        String[] Permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

        if(!getPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, GET_PERMISSION);
        } else{
           init();
           startJob();
        }

    }

    //for progress bar animation
    public void progressAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, 0, 100f);
        anim.setDuration(4500);
        progressBar.setAnimation(anim);
    }
    //progress bar animation ends

    //Permissions

    public static boolean getPermissions(Context context, String... permissions) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && context !=null && permissions !=null){
            for (String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startJob();
            init();
        }
    }

    //Permissions End

    private void init(){

        //Progress Bar Area
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.rgb(238, 130, 238), android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();
        //Progress Bar Area Ends
    }

    private void startJob() {
        Intent intent = new Intent(this, UpdateService.class);
        intent.putExtra("updateService", "Update Service Started");

        startService(intent);
    }
}