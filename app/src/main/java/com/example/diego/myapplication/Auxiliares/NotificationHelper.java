package com.example.diego.myapplication.Auxiliares;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NotificationHelper extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(AlarmService.NOTIFICATION_INTENT);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, it, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
            setAlarm(context, getAlarmTime(), pendingIntent);
        }
    }

    private long getAlarmTime() {
        Integer hour = 22;
        Integer minute = 00;

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    private void setAlarm(Context context, long time, PendingIntent pendingIntent) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
