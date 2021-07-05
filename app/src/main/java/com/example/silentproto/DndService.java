package com.example.silentproto;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.BundleCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.Serializable;

public class DndService extends Service {
    Thread hiloInsaturado;
    LatLngBounds latLngBounds;
    public DndService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LatLng so=new LatLng(28.7365,-106.1013); //punto necesario suroeste
        LatLng ne=new LatLng(28.7369,-106.1007); //punto necesario noreste
        LatLng son=new LatLng(ne.latitude,so.longitude);
        LatLng nes=new LatLng(so.latitude,ne.longitude);
        latLngBounds= new LatLngBounds(so,ne);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        while (true) {
            Log.wtf("ServicioDND","ACK");
                Bundle bundle=intent.getExtras();
                Ubs ubs=(Ubs)bundle.getSerializable("Loca");
                LatLng wea=ubs.getUbicacion();
                Log.wtf("ServicioDND","Recibió: "+wea.toString());
                if (latLngBounds.contains(wea)){
                    //Toast.makeText(this, "JALA", Toast.LENGTH_SHORT).show();
                    Log.wtf("UbiDesdeServ","a: "+wea);
                }else{
                    //Toast.makeText(MainActivity.this, "No jala", Toast.LENGTH_SHORT).show();
                    Log.wtf("UbiDesdeServ","no jalo la  "+wea);
                }


        }
        /*hiloInsaturado=new Thread(){
            @Override
            public void run() {
                super.run();
                                        ////////////
            }
        }; ////////

        //hiloInsaturado.start();

    }*/
}

}
