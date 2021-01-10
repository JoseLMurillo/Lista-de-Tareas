package com.example.listadetareas.Notificacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.listadetareas.DataBaseWorks;
import com.example.listadetareas.R;
import com.example.listadetareas.Work;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class AlarmaPrueba extends AppCompatActivity {

    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;


    //PRUEBA  /////////////
    Handler handler = new Handler();
    private final int TIEMPO = 8000;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;
    DataBaseWorks dataBaseWorks;
    ArrayList<Work> tareasBD;
    ArrayList<String> Fechas_Tareas;
    Map<String, Integer> mapaTarea;
    ///////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma_prueba);


        //PRUEBA//////////////////////
        //ejecutarTarea();
        ////////////


        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;

        hour = settings.getString("hour","");
        minute = settings.getString("minute","");

        notificationsTime = (TextView) findViewById(R.id.notifications_time);

        if(hour.length() > 0)
        {
            notificationsTime.setText(hour + ":" + minute);
        }

        findViewById(R.id.change_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AlarmaPrueba.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHour, finalMinute;

                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;
                        if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                        notificationsTime.setText(finalHour + ":" + finalMinute);

                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHour);
                        edit.putString("minute", finalMinute);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                        Toast.makeText(AlarmaPrueba.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                        //Utils.setAlarm(alarmID, today.getTimeInMillis(), AlarmaPrueba.this);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });
    }

    //METODO DE REGRESAR
    public void BACK(View view){
        onBackPressed();
    }

    //////////////////////

    public void FECHAS() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int selectedHour = 0, selectedMinute = 0;

        String finalHour, finalMinute;

        finalHour = "" + selectedHour;
        finalMinute = "" + selectedMinute;
        if (selectedHour < 10) finalHour = "0" + selectedHour;
        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;

        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, selectedHour);
        today.set(Calendar.MINUTE, selectedMinute);
        today.set(Calendar.SECOND, 0);

        SharedPreferences.Editor edit = settings.edit();
        edit.putString("hour", finalHour);
        edit.putString("minute", finalMinute);

        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
        edit.putInt("alarmID", alarmID);
        edit.putLong("alarmTime", today.getTimeInMillis());

        edit.commit();

        Toast.makeText(AlarmaPrueba.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

        //Utils.setAlarm(alarmID, today.getTimeInMillis(), AlarmaPrueba.this);
    }







    //ciclo infinito para ejecutar algo //PRUEBA
    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                // función a ejecutar
                Toast.makeText(AlarmaPrueba.this, "Hola", Toast.LENGTH_LONG).show(); // función para refrescar la ubicación del conductor, creada en otra línea de código
                createNotificationChannel();
                createNotification("Prueba", TIEMPO+"");
                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }

    //CREA EL CANAL DE LA NOTIFICACION
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //CREA LA NOTIFICACION  // AQUI SE DEBEN DE PASAR DATOS PARA QUE SEA UN MENSAJE PERSONALIZADO
    private void createNotification(String titulo, String texto){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(titulo);
        builder.setContentText(texto);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    ////////////////////////////
}
