package com.bn.applicationfeatures.features;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bn.applicationfeatures.BuildConfig;
import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.service.DownloadService;
import com.bn.applicationfeatures.features.content.download.DownloadProgressActivity;

public class DownloadFeature extends AppCompatActivity {

    public static final String NOTIF_CHANNEL_ID = BuildConfig.APPLICATION_ID
            + ".notification.channel.download";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_download);

        Button showNotif = findViewById(R.id.buttonNotif);
        showNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification(v);
            }
        });

        Button startService = findViewById(R.id.buttonService);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTheFuckingService();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNotificationChannel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.cancel(1);
    }

    private void startTheFuckingService() {
        if (!permissionGranted())
            return;

        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.DATA_URL,
                "https://sherlock-holm.es/stories/pdf/a4/1-sided/advs.pdf");
        startService(intent);
    }

    private boolean permissionGranted() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }

        } else {
            return true;
        }
        return false;
    }

    private void showNotification(View view) {
        NotificationCompat.Builder notify =
                new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID);
        notify.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notify.setContentTitle("Download Notification");
        notify.setContentText("Ngedownload");
        notify.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notify.setProgress(100, 40, false);
        notify.setOngoing(true);
        notify.addAction(0, "Cancel", null);

        Intent intent = new Intent(this, DownloadProgressActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        notify.setContentIntent(pendingIntent);
        notify.setAutoCancel(false);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, notify.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,
                    name, importance);
            channel.setDescription(description);

            NotificationManager manager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

}
