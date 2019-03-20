package com.example.fleetmonitoring;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.example.fleetmonitoring.Settings.SHARED_PREF_NAME;


public class Splashscreen extends AppCompatActivity {

    //Variables
    Animation fromBottom;
    ImageView carSS;
    ImageView locSS;
    Animation fromtop;
    ProgressBar progressBar;
    int GET_PERMISSION = 1;
    Boolean switchLoading;

    //variables end


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        switchLoading= sharedPreferences.getBoolean("Loading", false);

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
           init(switchLoading);
           startJob();
        }

    }

    //for progress bar animation
    public void progressAnimation(int loadingDelay) {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, 0, 100f);
        anim.setDuration(loadingDelay);
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
            init(switchLoading);
            startJob();
        }
    }

    //Permissions End

    private void init(Boolean loadingToDo){

        //Progress Bar Area
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.rgb(238, 130, 238), android.graphics.PorterDuff.Mode.SRC_IN);
//        Color.rgb(238, 130, 238)
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        if(!loadingToDo){
            progressAnimation(4500);
        } else{
            progressAnimation(500);
        }
        //Progress Bar Area Ends
    }

    private void startJob() {
        Intent intent = new Intent(this, UpdateService.class);
        intent.putExtra("updateService", "Update Service Started");

        startService(intent);
    }
}