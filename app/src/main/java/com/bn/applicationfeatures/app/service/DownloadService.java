package com.bn.applicationfeatures.app.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.engine.DownloadEngine;
import com.bn.applicationfeatures.app.engine.DownloadListener;
import com.bn.applicationfeatures.app.util.FileUtil;
import com.bn.applicationfeatures.app.util.NotificationUtil;

import java.io.File;

public class DownloadService extends Service {

    public static final String TAG = "DownloadService";

    public static final String DATA_URL = "DATA_URL";

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationUtil.from(this)
                .createNotificationChannel(NotificationUtil.CHANNEL_DOWNLOAD,
                        false, false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(DATA_URL);
        startDownload(url, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void startDownload(String url, int notifId) {
        File directory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        DownloadEngine engine = new DownloadEngine(url, directory);
        engine.setDownloadListener(new DownloadHandle(this, notifId));
        engine.execute();
    }

    private class DownloadHandle implements DownloadListener {
        private int notifyId;
        private Context context;
        private NotificationCompat.Builder notification;

        public DownloadHandle(Context context, int notifyId) {
            this.context = context;
            this.notifyId = notifyId;
        }

        @Override
        public void onDownloadStarted() {
            notification = new NotificationCompat.Builder(context,
                    NotificationUtil.CHANNEL_DOWNLOAD);
            notification.setSmallIcon(R.drawable.ic_notifications_black_24dp);
            notification.setContentTitle("Downloading file...");
            notification.setContentText("0%");
            notification.setOngoing(true);
            notification.setProgress(100, 0, true);
            notification.addAction(0, context.getString(R.string.cancel), null);
            notification.setPriority(NotificationCompat.PRIORITY_LOW);
            notification.setDefaults(NotificationCompat.DEFAULT_ALL);

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.notify(notifyId, notification.build());
        }

        @Override
        public void onProgressChanged(int contentLenght, int progress, int persent) {
            notification.setProgress(100, persent, false);
            notification.setContentText(persent + "%");
            notification.setPriority(NotificationCompat.PRIORITY_LOW);
            notification.setDefaults(NotificationCompat.DEFAULT_LIGHTS);

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.notify(notifyId, notification.build());
        }

        @Override
        public void onCancelled() {

        }

        @Override
        public void onDownloaded(Uri uri, String mimeType) {
            NotificationCompat.Builder notifSuccess =
                    new NotificationCompat.Builder(context, NotificationUtil.CHANNEL_DOWNLOAD);

            if (uri != null) {
                File file = new File(uri.getPath());
                notifSuccess.setContentTitle("File Downloaded");
                notifSuccess.setContentText(file.getName());
                notifSuccess.setSmallIcon(R.drawable.ic_file_download_black_24dp);
                notifSuccess.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notifSuccess.setAutoCancel(true);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(FileUtil.getFileProvider(context, file), mimeType);
//                intent.setType(mimeType);
//                intent.setData(FileUtil.getFileProvider(context, file));
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                PendingIntent pending = PendingIntent.getActivity(
                        context, notifyId, intent, PendingIntent.FLAG_ONE_SHOT);
                notifSuccess.setContentIntent(pending);
            } else {
                notifSuccess.setContentTitle("File Downloaded");
                notifSuccess.setContentText("An error occurred while downloading the file");
                notifSuccess.setSmallIcon(R.drawable.ic_error_outline_black_24dp);
                notifSuccess.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notifSuccess.setAutoCancel(true);
            }

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.cancel(notifyId);
            manager.notify(notifyId, notifSuccess.build());
        }
    }

}
