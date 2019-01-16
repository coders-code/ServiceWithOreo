package code.coders.notificationmanager;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.Objects;

public class NotificationO {
    private static NotificationManager notifManager;
    public NotificationO() {
    }
    public Notification askNotification(Context context, Class<?> aClass) {
        android.app.Notification notification = null;
        if (Build.VERSION.SDK_INT >= 26) {
            int icon = R.drawable.noti_icon;
            String CHANNEL_ID = "Location";
            Intent notificationIntent = new Intent(context, aClass);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel", NotificationManager.IMPORTANCE_HIGH);
            ((NotificationManager) Objects.requireNonNull(context.getSystemService(Context.NOTIFICATION_SERVICE))).createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle("Cerrebro").setSmallIcon(icon).setContentIntent(pendingIntent).build();
        }
        return notification;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : Objects.requireNonNull(manager).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }

    public static void showNotification(CharSequence c, Context mContext, String title, String message, int notiFyID, Class<?> cls) {
        int icon = R.drawable.noti_icon;
        if (notifManager == null)
            notifManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        Intent notificationIntent = new Intent(mContext, cls);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
        Notification notification;
        String CHANNEL_ID = "Coder";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, c, NotificationManager.IMPORTANCE_HIGH);notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);notification = mBuilder.setContentTitle(title).setSmallIcon(icon).setDefaults(Notification.DEFAULT_ALL).setAutoCancel(true).setStyle(new NotificationCompat.BigTextStyle().bigText(message)).setContentIntent(contentIntent).setChannelId(CHANNEL_ID).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).setContentText(message).build();notifManager.createNotificationChannel(mChannel);notifManager.notify(notiFyID, notification);
        } else {
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0).setAutoCancel(true).setContentTitle(title).setStyle(new NotificationCompat.BigTextStyle().bigText(message)).setContentIntent(contentIntent).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).setPriority(Notification.PRIORITY_HIGH).setContentText(message).build();notifManager.notify(notiFyID, notification);
        }
    }

}
