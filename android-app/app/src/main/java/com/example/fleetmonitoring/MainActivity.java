package com.example.fleetmonitoring;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private WebView webview;
    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    private boolean mapdashPageSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        FirebaseApp.initializeApp(this);

        webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/temp");

        dl = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.temp){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/temp");
                    mapdashPageSet = false;
                    setTitle("Temperature");
                    dl.closeDrawers();
                }
                else if (id == R.id.location){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/map");
                    mapdashPageSet = true;
                    setTitle("Car Location");
                    dl.closeDrawers();
                }
                else if (id == R.id.acceleration){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/acc");
                    mapdashPageSet = false;
                    setTitle("Acceleration");
                    dl.closeDrawers();
                }
                else if (id == R.id.vibration){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/vib");
                    mapdashPageSet = false;
                    setTitle("Vibration");
                    dl.closeDrawers();
                }
                else if (id == R.id.gyroscope){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/gyro");
                    mapdashPageSet = false;
                    setTitle("Gyroscope");
                    dl.closeDrawers();
                }
                else if (id == R.id.magnet){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/mag");
                    mapdashPageSet = false;
                    setTitle("Magnetometer");
                    dl.closeDrawers();
                }
                else if (id == R.id.dash){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/dash");
                    mapdashPageSet = true;
                    setTitle("Dashboard");
                    dl.closeDrawers();
                }
                else if (id == R.id.speed){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/speed");
                    mapdashPageSet = false;
                    setTitle("Speed");
                    dl.closeDrawers();
                }
                return true;
            }
        });


        Timer modded = new Timer();
        modded.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webview = (WebView) findViewById(R.id.webview);
                        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                        webview.getSettings().setJavaScriptEnabled(true);
                        if(mapdashPageSet == false){
                            webview.reload();
                        }
                    }
                });
            }
        }, 0, 5000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
