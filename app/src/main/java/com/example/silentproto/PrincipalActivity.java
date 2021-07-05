package com.example.silentproto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class PrincipalActivity extends AppCompatActivity {
private Button btnMisc;
private AppCompatButton btnLugares;
private Switch switchDND;
int permiso_notificacion=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        switchDND=findViewById(R.id.switchDND);
        btnLugares=findViewById(R.id.btnLugares);
        btnMisc=findViewById(R.id.btnMisc);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnLugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentoLugares=new Intent(getApplicationContext(),ListaLugaresActivity.class);
                startActivity(intentoLugares);
            }
        });
        switchDND.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Context context=getApplicationContext();
                AudioManager myAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                NotificationManager myNOtificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intentoDND=new Intent(getApplicationContext(),MClaseiNoti.class);
                if (b==true){
                    System.out.println("a");
                    if (ContextCompat.checkSelfPermission(PrincipalActivity.this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)== PackageManager.PERMISSION_GRANTED) {
                        //verificar por que el calcelNot no funciona
                        startService(intentoDND);
                        Toast.makeText(PrincipalActivity.this, "YaaaAAa", Toast.LENGTH_SHORT).show();

                        myAudioMgr.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        myNOtificationMgr.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
                    }else{
                        System.out.println("b");

                    }
                }else{
                    Toast.makeText(context, "Ya noooooo", Toast.LENGTH_SHORT).show();
                    //solicitarPermisoNotificacion();
                    stopService(intentoDND);
                    myAudioMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    myNOtificationMgr.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                }
            }
        });
    }

}