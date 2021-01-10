package com.example.listadetareas.Notificacion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    String identidad;
    String nombre;

    @Override
    public void onReceive(Context context, Intent intent) {

        identidad = intent.getStringExtra("IDENTIDAD");
        nombre = intent.getStringExtra("NOMBRE");

        Intent service1 = new Intent(context, NotificationService.class);

        service1.putExtra("Identidad", identidad);
        service1.putExtra("Nombre", nombre);

        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        //Log.d("WALKIRIA", " ALARM RECEIVED!!!");
    }
}
