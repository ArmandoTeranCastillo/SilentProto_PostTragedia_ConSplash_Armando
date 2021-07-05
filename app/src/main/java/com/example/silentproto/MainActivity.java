package com.example.silentproto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapLongClickListener { //PASO 2 : https://developers.google.com/maps/documentation/android-sdk/map?hl=es
    private  int permiso_ubicacion = 1,permiso_notificacion=2,permiso_storage;                                     //02 mayo, mapa funcionando, no ubicacion, mentira si, implementar mejor manejo de permisos
    private GoogleMap aMap;                                                     //09 mayo, mapa funcionando, ubicacion actual, implementar mejores permisos, POLICY ACCESS POR PEDIR
    private LatLng localizacionActual=null,localizacionActual2=null, localiGuardar, localiGuardar2;
    private Circle circle;
    Intent intentoService;
    Bundle paquetin;
    Thread hiloUbi;
    private Button btnGuardarUbi;
    Boolean mapListo=false;
    EditText txtNom;
    boolean esOtroPunto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //creacion de variables en la actividad
        setContentView(R.layout.activity_main);


        


        txtNom = findViewById(R.id.edtTxtNom);
        btnGuardarUbi =findViewById(R.id.btnprueba);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        intentoService=new Intent(this,DndService.class);   //este es el servicio que determinaria si el usuario esta dentro del "area" para activar el DND
        paquetin=new Bundle();

       // startService(intentoService);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //pedir el permiso de ubicacion, es necesario reiniciar la app al conceder

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Ya ha concedido el permiso anteriormente", Toast.LENGTH_LONG).show();
        } else {
            solicitarPermisoUbicacion();
        }
        //modo no molestar

        btnGuardarUbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                LatLng so=new LatLng(28.7365,-106.1013); //punto necesario suroeste
                LatLng ne=new LatLng(28.7369,-106.1007); //punto necesario noreste
                LatLng son=new LatLng(ne.latitude,so.longitude);
                LatLng nes=new LatLng(so.latitude,ne.longitude);
                LatLngBounds latLngBounds= new LatLngBounds(so,ne); //crea el area que veremos a continuacion
                ////////LatLngBounds.builder().include(localizacionActual).build().including(localizacionActual);
                PolygonOptions polygonOptions=new PolygonOptions().add(so,son,ne,nes,so); //crea un poligono que tenga sus esquinas en esos puntos
                Polygon polygon=aMap.addPolygon(polygonOptions); //dibuja el poligono que acabamos de crear
                LatLng test=new LatLng(aMap.getMyLocation().getLatitude(),aMap.getMyLocation().getLongitude()); //obtener la localizacion actual
                    Ubs ubs=new Ubs(); //instancia de la clase para poder estarla cambiando frecuentemente
                    ubs.setUbicacion(test); //le damos esa ubicacion
                if (latLngBounds.contains(ubs.getUbicacion())){ //si sí está ahí
                    Toast.makeText(MainActivity.this, "JALA", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No jala", Toast.LENGTH_SHORT).show();
                }*/

                if(esOtroPunto == true){
                    String nomLugar = txtNom.getText().toString();
                    //Hacemos que se guarden los puntos con localiGuardar y localiGuardar2
                    //ademas del nombre del lugar que se puso en el editText modificando un json
                }else{
                    Toast.makeText(MainActivity.this,"Debes crear un área diferente",Toast.LENGTH_SHORT).show();
                }
            }



            ///////

        });
    }

    /////////////////////

    @Override
    protected void onResume() {
        super.onResume();
        Ubs ubs=new Ubs();

        //meter la bandera mapListo para que testResumeLat no sea nulo y se espere

        /*hiloUbi=new Thread(){
            @Override
            public void run() {
                super.run();
                while(true){
                    try {

                        Thread.sleep(5000);
                        LatLng testResumeLat=null;
                        if (mapListo){
                            testResumeLat=new LatLng(aMap.getMyLocation().getLatitude(),aMap.getMyLocation().getLongitude());
                        }
                        ubs.setUbicacion(testResumeLat);
                        paquetin.putSerializable("Loca",ubs);
                        intentoService.putExtras(paquetin);
                        if (intentoService==null){
                            startService(intentoService);
                        }
                        Log.wtf("Main, Resume", "loc: "+ ubs.getUbicacion());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        hiloUbi.start();*/

    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    private void solicitarPermisoUbicacion() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Se necesita el permiso de ubicación")
                    .setMessage("Este permiso es necesario para acceder a su ubicación")
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, permiso_ubicacion);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    })
                    .create().show();
        }

    }
/////////////////////////////////////////////////////////////////////////////////////////////////
    public void solicitarPermisoNotificacion(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_NOTIFICATION_POLICY)){
            new AlertDialog.Builder(this)
                    .setTitle("Se necesita el control de notificaciones")
                    .setMessage("Este permiso es necesario para bloquear las notificaciones entrantes")
                    .setPositiveButton("Afirmativo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY},permiso_notificacion);
                        }
                    })
                    .setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY},permiso_notificacion);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permiso_ubicacion) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Ha concedido el permiso", Toast.LENGTH_SHORT).show();
                    System.out.println("Ha concedido un permiso, mostrado en consola");
                } else {
                    Toast.makeText(this, "Ha denegado el permiso", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    //codigo del mapa
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        aMap = googleMap;

        LatLng Chihuahua = new LatLng(28.7369, -106.1010); //masomenos mi casa
        aMap.addMarker(new MarkerOptions()          //agrega un marcador
                .position(Chihuahua)
                .title("aaaaaaaaaaa shiwawa"));
        aMap.moveCamera(CameraUpdateFactory.newLatLng(Chihuahua)); //mueve la camara al latlng Chihuahua, la de arriba
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        aMap.setMyLocationEnabled(true);    //permitir mi ubicacion
        aMap.setOnMyLocationButtonClickListener(this);  //boton de mi ubicacion
        aMap.setOnMyLocationClickListener(this);        //boton de presionar el punto azul que es mi ubicacion
        aMap.setOnMapLongClickListener(this);           //boton o accion de dejar presionado
        mapListo=true;
    }

    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
        Log.wtf("Loc",location.toString());

    }


    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        //localizacionActual =latLng; XD
        if (localizacionActual==null){
            localizacionActual=latLng;
            Toast.makeText(this, "Lat: "+localizacionActual, Toast.LENGTH_SHORT).show();
        }else if (localizacionActual2==null){
            localizacionActual2=latLng;
            Toast.makeText(this, "Lat: "+localizacionActual + "Lat2: "+localizacionActual2, Toast.LENGTH_SHORT).show();
            //
        LatLng so=new LatLng(localizacionActual.latitude,localizacionActual.longitude); //punto necesario suroeste
        LatLng ne=new LatLng(localizacionActual2.latitude,localizacionActual2.longitude); //punto necesario noreste
        LatLng son=new LatLng(ne.latitude,so.longitude);                                                //noroeste
        LatLng nes=new LatLng(so.latitude,ne.longitude);                                                //sureste

        LatLngBounds latLngBounds= new LatLngBounds(so,ne); //crea el area que a continuacion haremos ver con un poligono

        PolygonOptions polygonOptions=new PolygonOptions().add(so,son,ne,nes,so); //crea un poligono que tenga sus esquinas en esos puntos

        Polygon polygon=aMap.addPolygon(polygonOptions); //dibuja el poligono que acabamos de crear
        localiGuardar = localizacionActual;
        localiGuardar2 = localizacionActual2;
        esOtroPunto = true;
        }else{
            Toast.makeText(MainActivity.this,"Ya has seleccionado los dos puntos",Toast.LENGTH_SHORT).show();
        }

      /*CircleOptions circleOptions =new CircleOptions()
                .center(latLng)
                .radius(5);
        circle =aMap.addCircle(circleOptions);*/
      /*LatLng so=new LatLng(localizacionActual.latitude,localizacionActual.longitude); //punto necesario suroeste
        LatLng ne=new LatLng(localizacionActual2.latitude,localizacionActual2.longitude); //punto necesario noreste
        LatLng son=new LatLng(ne.latitude,so.longitude);                                                //noroeste
        LatLng nes=new LatLng(so.latitude,ne.longitude);                                                //sureste

        LatLngBounds latLngBounds= new LatLngBounds(so,ne); //crea el area que veremos a continuacion

        PolygonOptions polygonOptions=new PolygonOptions().add(so,son,ne,nes,so); //crea un poligono que tenga sus esquinas en esos puntos

        Polygon polygon=aMap.addPolygon(polygonOptions); //dibuja el poligono que acabamos de crear*/

    }
}
class Ubs implements Serializable{
    private LatLng Ubicacion;

    public LatLng getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        Ubicacion = ubicacion;
    }
}