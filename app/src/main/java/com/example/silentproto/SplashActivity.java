package com.example.silentproto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation topAnim, bottonAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_launcher);

        //Cargar animaciones
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottonAnim = AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        image = findViewById(R.id.imgVw);
        logo = findViewById(R.id.txtVwLogo);
        slogan = findViewById(R.id.txtVwSlogan);

        //Implementar animaciones a cada componente
        image.setAnimation(topAnim);
        logo.setAnimation(bottonAnim);
        slogan.setAnimation(bottonAnim);

        //Una vez terminada la animacion, abrir el activity pricipal
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN); //Poner un tiempo de espera


    }
}