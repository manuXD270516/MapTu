package com.example.usuario.maptu.Login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.usuario.maptu.R;

public class MaptuPresentacion extends AppCompatActivity {

    MediaPlayer mediaPlayer1;
    Thread hilo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maptu_presentacion);
        //overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
        mediaPlayer1=MediaPlayer.create(this,R.raw.platinum);
        hilo=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2800);
                    startActivityForResult(new Intent(MaptuPresentacion.this,LoginActivity.class),0);
                    overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        hilo.start();
        mediaPlayer1.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0 || resultCode==1) {
            hilo.stop();
            finishAffinity();
            //super.finish();
        }
    }

}
