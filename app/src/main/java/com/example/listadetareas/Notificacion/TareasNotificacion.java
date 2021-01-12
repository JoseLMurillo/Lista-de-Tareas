package com.example.listadetareas.Notificacion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.listadetareas.DataBaseWorks;
import com.example.listadetareas.R;
import com.example.listadetareas.Work;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TareasNotificacion extends Service {

    ///////////////   PRUEBA
    Handler handler = new Handler();
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;

    Map<Integer, String>mapaHora, mapaTarea, mapaFecha, mapaNombre;
    
    Calendar hoy = Calendar.getInstance();
    ArrayList<Work> tareasBD;
    ArrayList<String> fecha_Tareas;
    DataBaseWorks dataBaseWorks;
    Boolean ContadorMinuto = true;
    ///////////////////

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){

        dataBaseWorks = new DataBaseWorks(this);
        fecha_Tareas = new ArrayList<String>();
        mapaHora = new HashMap<Integer, String>();
        tareasBD = new ArrayList<Work>();
        mapaFecha = new HashMap<Integer, String>();
        mapaTarea = new HashMap<Integer, String>();
        mapaHora = new HashMap<Integer, String>();
        mapaNombre = new HashMap<Integer, String>();

        tareasBD = OBTENDATOS();
        LLENARMAPA();
        //LLENARARREGLO2();
        LLENARMAPANOMBRE();
        LLENARMAPAFECHA();
        LLENARHORA();

        ejecutarTarea();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //////////////////////
    //ciclo infinito para ejecutar algo //PRUEBA
    public void ejecutarTarea() {

            final int TIEMPO = 20000;   //NO ENTIENDO PORQUE MATA AL SERVICIO SI EL HANDLER ES MAYOR A 3 SEGUNDOS  //POBAR USAR OTRO POSTDELEAY DENTRO DE ESTE PARA VER SI EL TIEMPO SE ALARGA
            handler.postDelayed(new Runnable() {
                public void run() {

                    if (ContadorMinuto == true){

                        // función a ejecutar
                        NOTIFICACIONESDIARIO();
                        createNotificationChannel();
                    }
                    handler.postDelayed(this, TIEMPO);
                }

            }, TIEMPO);
    }

    //ciclo infinito para ejecutar algo //PRUEBA
    public void UnMINUTO() {

        final int TIEMPO2 = 60000;   //NO ENTIENDO PORQUE MATA AL SERVICIO SI EL HANDLER ES MAYOR A 3 SEGUNDOS  //POBAR USAR OTRO POSTDELEAY DENTRO DE ESTE PARA VER SI EL TIEMPO SE ALARGA
        handler.postDelayed(new Runnable() {
            public void run() {

                ContadorMinuto = true;

                handler.postDelayed(this, TIEMPO2);
            }

        }, TIEMPO2);

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

    ////////////////////////////

    ///ESTO PROCESA LA NOTIFICACION
    public void NOTIFICACIONESDIARIO() {

        //CREO QUE DEBO DE HACER UNA LISTA DE LOS ID DEL MAPA QUE CUMPLEN LA CONDICION Y LUEGO ENVIARLOS PARA LA NOTIFICACION

        for (Map.Entry<Integer, String> entry : mapaFecha.entrySet()) {   //MODIFICAR MAPTAREA2 PARA QUE LA LLAVE SEA EL ID Y EL VALOR SEA LA FECHA, ASI CREO QUE SE LOGRA CONTROLAR LA MUESTRA DE CADA UNO DE LOS VALORES

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = df.format(c.getTime());

            SimpleDateFormat dh = new SimpleDateFormat("HH:mm");
            String formattedHour = dh.format(c.getTime());

            SimpleDateFormat ds = new SimpleDateFormat("ss");
            String formattedSec = ds.format(c.getTime());


            if(formattedDate.equals(entry.getValue())){
                int HORA, MINUTO;

                HORA = Integer.parseInt(mapaHora.get(entry.getKey()).substring(0,2));
                MINUTO = Integer.parseInt(mapaHora.get(entry.getKey()).substring(3));


                if(Integer.parseInt(formattedHour.substring(0,2)) == HORA){

                    //Toast.makeText(this, "ID:"+entry.getKey()+" Comparo Hora: "+formattedHour.substring(0,2)+" - "+HORA, Toast.LENGTH_LONG).show();

                    if(Integer.parseInt(formattedHour.substring(3)) == MINUTO && ContadorMinuto == true){

                        hoy.set(Calendar.HOUR_OF_DAY, HORA);
                        hoy.set(Calendar.MINUTE, MINUTO);
                        hoy.set(Calendar.SECOND, 0);

                        Toast.makeText(this, "ID:"+entry.getKey()+" Comparo Minuto: "+formattedHour.substring(3)+" - "+MINUTO, Toast.LENGTH_LONG).show();

                        Utils.setAlarm(entry.getKey(), hoy.getTimeInMillis(), TareasNotificacion.this);
                        ContadorMinuto = false;
                        UnMINUTO();
                        //Utils.setAlarm(entry.getKey(), entry.getKey().toString(), mapaNombre.get(entry.getKey()), hoy.getTimeInMillis(), TareasNotificacion.this);
                    }
                }
            }

            //SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
            /*System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());*/
        }
    }

    //CREA LA NOTIFICACION  // AQUI SE DEBEN DE PASAR DATOS PARA QUE SEA UN MENSAJE PERSONALIZADO
    public void createNotification(int id, String titulo, String texto){

        ContadorMinuto = false;

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
        notificationManagerCompat.notify(id, builder.build());

        //NOTIFICACION_ID
    }

    //LLENA UN MAPA CON EL NOMBRE DE LA TAREA Y SU ID PARA ENVIARLO A WORKS ACTIVITY
    public void LLENARMAPA(){
        int llave;
        String valor;

        for(int i=0; i<tareasBD.size(); i++){
            llave = tareasBD.get(i).getIdWork();
            valor = tareasBD.get(i).getNombre();
            mapaTarea.put(llave, valor);
        }
    }

    //OBTIENE LOS DATOS DE LA BD
    public ArrayList<Work> OBTENDATOS(){
        Cursor Datos = dataBaseWorks.GetTareas();
        ArrayList<Work> tareas = new ArrayList<Work>();

        //VARIABLES
        int id;
        String name, date, desc, impor, hor;

        while (Datos.moveToNext()){
            id = Datos.getInt(Datos.getColumnIndex("id_tarea"));
            name = Datos.getString(Datos.getColumnIndex("nombre"));
            date = Datos.getString(Datos.getColumnIndex("fecha"));
            hor = Datos.getString(Datos.getColumnIndex("hora"));
            desc = Datos.getString(Datos.getColumnIndex("descripcion"));
            impor = Datos.getString(Datos.getColumnIndex("importancia"));

            Work work = new Work(id, name, date, hor, desc, impor);
            tareas.add(work);
        }
        return tareas;
    }

    //LLENA UN MAPA CON LA FECHA DE LA TAREA
    public void LLENARMAPAFECHA(){
        int llave;
        String valor;

        for(int i=0; i<tareasBD.size(); i++){
            llave = tareasBD.get(i).getIdWork();
            valor = tareasBD.get(i).getFecha();
            mapaFecha.put(llave, valor);
        }
    }

    //LLENA UN MAPA CON LA FECHA DE LA TAREA
    public void LLENARMAPANOMBRE(){
        int llave;
        String valor;

        for(int i=0; i<tareasBD.size(); i++){
            llave = tareasBD.get(i).getIdWork();
            valor = tareasBD.get(i).getNombre();
            mapaNombre.put(llave, valor);
        }
    }

    //LLENA UN MAPA CON EL NOMBRE DE LA TAREA Y SU ID PARA ENVIARLO A WORKS ACTIVITY
    public void LLENARHORA(){
        int llave;
        String valor;

        for(int i=0; i<tareasBD.size(); i++){
            valor = tareasBD.get(i).getHora();
            llave = tareasBD.get(i).getIdWork();
            mapaHora.put(llave, valor);
        }
    }
}
