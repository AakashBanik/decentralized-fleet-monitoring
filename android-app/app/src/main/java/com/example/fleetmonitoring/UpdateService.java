package com.example.fleetmonitoring;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.example.fleetmonitoring.ForegroundUpdateService.CHANNEL_ID;

public class UpdateService extends Service {

    int installedAppVersion;
    int externalAppVersion;
    private PackageInfo pi;
    String packagePath = "/storage/emulated/0/Download/app-debug.apk";
    File downloadFile = new File(packagePath);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("updateService");

        Intent notificationIntent = new Intent(this, Splashscreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Update Service")
                .setContentText("Background Update service has started for the Application")
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        //google firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("app-debug.apk");
        Toast.makeText(UpdateService.this, "Checking preliminary Files Before Update", Toast.LENGTH_SHORT).show();

        pathReference.getFile(downloadFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(Splashscreen.this, "Checking Preliminary Files", Toast.LENGTH_SHORT).show();
                Log.d("UpdateDownloaded", "update downloaded to " + packagePath);
                updateProcess();
            }
        });

        //firebase && storage access ends
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        downloadFile.delete();
        stopSelf();
    }

    private void updateProcess(){
        PackageManager pm = getPackageManager();
        //update starts
        if(downloadFile.exists()){

            PackageInfo packageInfo;
            try{
                packageInfo =  getPackageManager().getPackageInfo(getPackageName(), 0);
                installedAppVersion = packageInfo.versionCode;
            }catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


            pi = pm.getPackageArchiveInfo(packagePath, 0);
            externalAppVersion = pi.versionCode;

            if(installedAppVersion < externalAppVersion){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri fileUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", downloadFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    intent.setDataAndType(fileUri, "application/vnd.android" + ".package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(downloadFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } else{
                Toast.makeText(this, "No New Updates to Install", Toast.LENGTH_SHORT).show();
            }
        }
        //update ends
    }
}
