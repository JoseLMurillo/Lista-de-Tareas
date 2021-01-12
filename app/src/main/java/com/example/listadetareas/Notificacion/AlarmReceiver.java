package com.example.listadetareas.Notificacion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //CREA UN INTENT CON EL CONTEXTO QUE SIGUE SIENDO TAREASNOTIFICACION
        Intent service1 = new Intent(context, NotificationService.class);

        //VUELVE A HACER ALGO RARO QUE NO ENTIENDO
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        Log.d("LISTA DE TAREAS", " Recordatorio");
    }
}
