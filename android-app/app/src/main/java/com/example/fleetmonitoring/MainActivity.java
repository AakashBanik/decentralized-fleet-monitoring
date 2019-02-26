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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private WebView webview;
    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    private boolean mapPageSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);

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
                if (id == R.id.hum){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/hum");
                    mapPageSet = false;
                    setTitle("Humidity");
                    dl.closeDrawers();
                }
                else if (id == R.id.temp){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/temp");
                    mapPageSet = false;
                    setTitle("Temperature");
                    dl.closeDrawers();
                }
                else if (id == R.id.location){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/map");
                    mapPageSet = true;
                    setTitle("Car Location");
                    dl.closeDrawers();
                }
                else if (id == R.id.pressure){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/pre");
                    mapPageSet = false;
                    setTitle("Pressure");
                    dl.closeDrawers();
                }
                else if (id == R.id.vibration){
                    webview.loadUrl("https://mysterious-spire-66642.herokuapp.com/vib");
                    mapPageSet = false;
                    setTitle("Vibration");
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
                        if(mapPageSet == false){
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
