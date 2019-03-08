package com.example.fleetmonitoring;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageInfo;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class BackgroundUpdateService extends JobService {

    private boolean JOB_CANCELLED = false;
    private static final String TAG = "BackgroundUpdateService";
    private String packagePath = "/storage/emulated/0/Download/app-debug.apk";
    private File downloadFile = new File(packagePath);
    int installedAppVersion;
    int externalAppVersion;
    private PackageInfo pi;


    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "Background Update Job Started", Toast.LENGTH_SHORT).show();
        doWork(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "Job Cancelled due to poor/no networking.", Toast.LENGTH_SHORT).show();
        JOB_CANCELLED = true;
        return true;
    }

    public void doWork(final JobParameters jobParams){
        new Thread(new Runnable() {
            @Override
            public void run() {
                backgroundWork();

                try{
                    Thread.sleep(8000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                if(JOB_CANCELLED){
                    return;
                }
                jobFinished(jobParams, true);
            }
        });
    }

    public void backgroundWork() {

        //google firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("app-debug.apk");

        pathReference.getFile(downloadFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(BackgroundUpdateService.this, "Checking Preliminary Update Files", Toast.LENGTH_SHORT).show();
            }
        });
        //firebase && storage access ends
    }
}
