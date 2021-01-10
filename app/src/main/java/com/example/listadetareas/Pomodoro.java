package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Pomodoro extends AppCompatActivity {

    //VARIABLES
    TextView Crono, Pomo;
    EditText Trabajo, Descanso, Ciclos;
    boolean CronoFinalizado = false, EstadoTrabajo = true, FinalizarClick = false;
    long minuto, VarTrabajo, VarDescanso, VarCiclos;
    int Ciclo = 0;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;
    boolean CronoActivo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        //RELACION CON LA PARTE GRAFICA
        Pomo = (TextView) findViewById(R.id.tvPomodoro);
        Crono = (TextView) findViewById(R.id.tvCrono);
        Trabajo = (EditText) findViewById(R.id.etTrabajo);
        Descanso = (EditText) findViewById(R.id.etDescanso);
        Ciclos = (EditText) findViewById(R.id.etCiclos);

    }

    //INICIA EL CRONOMETRO HACIA ATRAS
    public void INICIACUENTA(View view){
        CAMPOTXTVACIO();
        FinalizarClick = true;

        //EVITA PODER ACTIVAR EL BOTON INICIAR VARIAS VECES
        if(CronoActivo) {

            ASIGNACION();

        //CRONOMETRO ATRAS
        CountDownTimer cuenta = new CountDownTimer(minuto, 1000) {
            @Override
            public void onTick(long l) {

                //SI HA SIDO APRETADO EL BOTON FINALIZADO CANCELA Y VUELVE AL DEFAULT
                if(CronoFinalizado){

                        cancel();
                        DEFAULT();
                }

                else{
                    //COMPRUEVA QUE SE ESTA REALIZANDO   //SE PODRIA AÑADIR OTRO CAMPO PARA HACER UNA SERIE CONTINUA DE UN NUMERO FINITO DE REPETICIONES
                    TITULOESTADOTRABAJO();

                    Pomo.setBackgroundColor(Color.parseColor("#FBD301"));
                    CRONOPERACION(l);

                    //ACTIVA O DESACTIVA LOS EDITEXT
                    if(CronoActivo){
                        TXTNOACTIVO();
                        CronoActivo = false;
                    }
                }
            }

            //AL FINALIZAR
            @Override
            public void onFinish() {
                //COMPRUEBA SI ESTA TRABAJANDO O DESCANSANDO
                TRABAJA();

                //CREA LA NOTIFICACION
                createNotificationChannel();
                createNotification(Pomo.getText().toString(), "Se ha finalizado el "+Pomo.getText().toString());

                Toast.makeText(Pomodoro.this, "Finalizo", Toast.LENGTH_LONG).show();

                Pomo.setText("POMODORO");
                Pomo.setBackgroundColor(Color.parseColor("#000000"));
                Ciclo = Ciclo +1;

                //ESTO HAY QUE MODIFICARLO PARA QUE SEA ATOMATICO
                if(Ciclo ==2){
                    if(VarCiclos ==1){
                        Crono.setText("00:00");
                        Trabajo.setText("");
                        Descanso.setText("");
                        Ciclos.setText("");
                        Ciclo = 0;
                    }
                    else {
                        VarCiclos = VarCiclos - 1;
                        Ciclos.setText(VarCiclos+"");
                    }
                }

                TXTACTIVO();

                FinalizarClick = false;
                CronoActivo = true;
            }
        }.start();

        }

    }

    //VERIFICA SI LOS EDITEXT ESTAN VACIOS
    private void CAMPOTXTVACIO() {
        //CAMPO TRABAJO
        if(Trabajo.getText().toString().isEmpty()) {
            VarTrabajo = (1 * 60) * 1000;
            Trabajo.setText("1");   //Esto cambiarlo a 25
        }
        else {
            VarTrabajo = (Long.parseLong(Trabajo.getText().toString()) * 60) * 1000;
        }

        //CAMPO DESCANSO
        if(Descanso.getText().toString().isEmpty()){
            VarDescanso = (1 * 60) * 1000;
            Descanso.setText("1");  //Esto cambiarlo a 5
        }
        else {
            VarDescanso = (Long.parseLong(Descanso.getText().toString()) * 60) * 1000;
        }

        //CAMPO CICLOS
        if(Ciclos.getText().toString().isEmpty()){
            VarCiclos = 1;
            Ciclos.setText(VarCiclos+"");
        }
        else {
            VarCiclos = Integer.parseInt(Ciclos.getText().toString());

            if(VarCiclos < 0){
                Ciclos.setText("Ciclo: 0");
            }
        }
    }

    //ASIGNA EL VALOR A MINUTOS
    public void ASIGNACION(){
        if(EstadoTrabajo){
            minuto = VarTrabajo;
        }
        else{
            minuto = VarDescanso;
        }
    }

    //METODO DEFAULT
    private void DEFAULT(){
        Crono.setText("00:00");
        Trabajo.setText("");
        Descanso.setText("");
        Ciclos.setText("");
        Pomo.setText("POMODORO");
        Pomo.setBackgroundColor(Color.parseColor("#0BB0DE"));
        TXTACTIVO();
        CronoFinalizado = false;
        CronoActivo = true;
    }

    //COMPRUEBA SI SE ESTA TRABAJANDO
    public void TITULOESTADOTRABAJO(){
        if(EstadoTrabajo){
            Pomo.setText("TRABAJO");
        }
        else{
            Pomo.setText("DESCANSO");
        }
    }

    //OPERACION DENTRO DEL CRONOMETRO
    public void CRONOPERACION(long l){
        long Tiempo = l/1000;
        int m = (int) Tiempo/60;
        long s = Tiempo % 60;
        String minutes = String.format("%02d", m);
        String segundos = String.format("%02d", s);
        Crono.setText(minutes+":"+segundos);
    }

    //CUANDO EL CRONOMETRO ESTA ACTIVO NO SE PUEDE AGREGAR TIEMPOS
    public void TXTNOACTIVO() {
        Trabajo.setEnabled(false);
        Descanso.setEnabled(false);
        Ciclos.setEnabled(false);
    }

    //CUANDO EL CRONOMETRO ESTA ACTIVO NO SE PUEDE AGREGAR TIEMPOS
    public void TXTACTIVO() {
        Trabajo.setEnabled(true);
        Descanso.setEnabled(true);
        Ciclos.setEnabled(true);
    }

    //COMPRUEBA EN QUE TIEMPO SE ESTA (TRABAJO-DESCANSO)
    private void TRABAJA(){
        if(EstadoTrabajo){
            EstadoTrabajo = false;
        }
        else{
            EstadoTrabajo = true;
        }
    }

    //METODO PARA FINALIZAR EL CONTADOR DEL POMODORO
    public void CANCELAR(View view){
        if(FinalizarClick){
            FINALIZADOV();
            CronoActivo = true;
            FinalizarClick = false;
        }
    }

    //DEVUELVE FINALIZADOV
    public boolean FINALIZADOV(){
        CronoFinalizado = true;
        return CronoFinalizado;
    }

    //METODO DE REGRESAR
    public void BACK(View view){
        FINALIZADOV();
        CronoActivo = true;
        FinalizarClick = false;
        onBackPressed();
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
}