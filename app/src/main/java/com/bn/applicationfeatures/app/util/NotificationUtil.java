package com.bn.applicationfeatures.app.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.bn.applicationfeatures.BuildConfig;
import com.bn.applicationfeatures.R;

public class NotificationUtil {

    public static final String CHANNEL_DEFAULT = BuildConfig.APPLICATION_ID
            + ".notification.channel.default";

    public static final String CHANNEL_DOWNLOAD = BuildConfig.APPLICATION_ID
            + ".notification.channel.download";

    private Context context;

    private NotificationUtil(Context context) {
        this.context = context;
    }

    public static NotificationUtil from(Context context) {
        return new NotificationUtil(context);
    }

    public void createNotificationChannel(String channelId, boolean vibration, boolean sound) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.notification_channel_name);
            String description = context.getString(R.string.notification_channel_description);

            NotificationChannel channel = new NotificationChannel(
                    channelId, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            channel.enableVibration(vibration);
            channel.setSound(null, null);

            NotificationManager manager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

}
