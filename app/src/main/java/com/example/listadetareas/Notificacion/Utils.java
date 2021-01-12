package com.example.listadetareas.Notificacion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;

public class Utils {

    //UTILS RECIBE UN ID, UN CALENDARIO EN LONG Y UN CONTEXTO
    public static void setAlarm(int i, Long timestamp, Context ctx) {

        //CREA UN ALARMANAGER QUE TOMA EL CONTEXTO // IMAGINO QUE ES PARA CONFIGURAR EL ALARMANAGER
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);

        //CREA UN INTENT CON EL CONTEXTO RECIBIDO Y CON EL DESTINO
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);

        PendingIntent pendingIntent; //CREA UN PENDING INTENT
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT);  //SOLO SE DISPARA UNA VEZ

        //ENVIA ALGO A UNA REFERENCIA UNO A UNO
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        //USA EL PENDININTENT
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }
}
