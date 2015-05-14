package com.example.diego.myapplication.Auxiliares;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import com.example.diego.myapplication.R;
import com.example.diego.myapplication.Atividades.AdicionarTempoActivity;


public class AlarmService extends Service {
    public static final String NOTIFICATION_INTENT = "com.ufcg.PUSH_NOTIFICATION_INTENT";
    public static final String FROM_NOTIFICATION_EXTRA_KEY = "from.notification";
    public static final int NOTIFICATION_ID = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotification();
        cancelPreviousIntent();
        stopSelf();
    }

    private void cancelPreviousIntent() {
        PendingIntent pendingIntent = PendingIntent.getService(AlarmService.this, 0, new Intent(NOTIFICATION_INTENT), PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            pendingIntent.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotification() {
        String status = "Status";
        String title = "Tempo Livre";
        String message = "Inclua um tempo a uma tarefa";

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.icon, status, System.currentTimeMillis());

        Intent intentMensage = new Intent(this, AdicionarTempoActivity.class);
        intentMensage.putExtra(FROM_NOTIFICATION_EXTRA_KEY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intentMensage, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(this, title, message, pendingIntent);
        notification.vibrate = new long[]{150, 1000};
        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this, som);
            toque.play();
        } catch (Exception e) {

        }
        nm.notify(NOTIFICATION_ID, notification);
    }
}

