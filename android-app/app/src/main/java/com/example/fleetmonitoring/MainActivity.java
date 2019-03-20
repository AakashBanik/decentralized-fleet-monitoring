package com.example.fleetmonitoring;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.fleetmonitoring.Settings.SHARED_PREF_NAME;


public class MainActivity extends AppCompatActivity {
    private WebView webview;
    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    private boolean mapdashPageSet = false;
    private ActionBar actionBar;
    private int colors;
    Boolean switchDarkMode, betaEngineSet;
    Menu menu;
    MenuItem target;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        switchDarkMode= sharedPreferences.getBoolean("DarkMode", false);
        betaEngineSet = sharedPreferences.getBoolean("Engine", false);
        nv = (NavigationView) findViewById(R.id.nav_view);
        menu = nv.getMenu();

        if(switchDarkMode == true){
            //action bar color
            actionBar = getSupportActionBar();
            colors = getColor(R.color.black);
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            nv.setBackgroundColor(getColor(R.color.black));
            //action bar color ends
        } else{
            //action bar color
            actionBar = getSupportActionBar();
            colors = getColor(R.color.blueshade);
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            nv.setBackgroundColor(getColor(R.color.blueshade));
            //action bar color ends
        }

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        FirebaseApp.initializeApp(this);

        if(betaEngineSet == true){
            hide_components();
            webview.loadUrl("https://fir-node-app.firebaseapp.com/");
        } else{
            target = menu.findItem(R.id.main);
            target.setVisible(false);
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/temp");
        }

        dl = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItemClick(menuItem);
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

    public void menuItemClick(MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.temp){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/temp");
            mapdashPageSet = false;
            setTitle("Temperature");
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            dl.closeDrawers();
        }
        else if (id == R.id.location){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/map");
            mapdashPageSet = true;
            setTitle("Car Location");
            actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.black)));
            dl.closeDrawers();
        }
        else if (id == R.id.acceleration){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/acc");
            mapdashPageSet = false;
            setTitle("Acceleration");
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            dl.closeDrawers();
        }
        else if (id == R.id.vibration){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/vib");
            mapdashPageSet = false;
            setTitle("Vibration");
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            dl.closeDrawers();
        }
        else if (id == R.id.gyroscope){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/gyro");
            mapdashPageSet = false;
            setTitle("Vehicle Angular Velocity");
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            dl.closeDrawers();
        }
        else if (id == R.id.magnet){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/mag");
            mapdashPageSet = false;
            setTitle("Magnetometer");
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            dl.closeDrawers();
        }
        else if (id == R.id.dash){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/dash");
            mapdashPageSet = true;
            setTitle("Monitoring Dashboard");
            actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.black)));
            dl.closeDrawers();
        }
        else if (id == R.id.speed){
            webview.loadUrl("https://fir-mobile-node-app.firebaseapp.com/speed");
            mapdashPageSet = false;
            setTitle("Vehicle Speed");
            actionBar.setBackgroundDrawable(new ColorDrawable(colors));
            dl.closeDrawers();
        }
        else if (id == R.id.settings){
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        }
        else if(id == R.id.main){
            webview.loadUrl("https://fir-node-app.firebaseapp.com/");
            mapdashPageSet = false;
            setTitle("Main");
            actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.black)));
            dl.closeDrawers();
        }
    }

    public void hide_components(){
        target = menu.findItem(R.id.vibration);
        target.setVisible(false);
        target = menu.findItem(R.id.temp);
        target.setVisible(false);
        target = menu.findItem(R.id.speed);
        target.setVisible(false);
        target = menu.findItem(R.id.gyroscope);
        target.setVisible(false);
        target = menu.findItem(R.id.magnet);
        target.setVisible(false);
        target = menu.findItem(R.id.acceleration);
        target.setVisible(false);
    }

}
