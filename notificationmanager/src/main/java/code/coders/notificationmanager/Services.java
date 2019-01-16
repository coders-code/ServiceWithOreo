package code.coders.notificationmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.concurrent.ExecutionException;

public class Services {
    public void startService(Context context, Class<?> aClass) {
        boolean b = true;
        try {
            b = new ForegroundCheckTask().execute(context).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (!b)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) context.startForegroundService(new Intent(context, aClass));
            else context.startService(new Intent(context, aClass));
        else context.startService(new Intent(context, aClass));
    }
}
