package com.example.usuario.maptu.AcercaDe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.usuario.maptu.R;

public class AcercaDe extends AppCompatActivity {

    private TextView mTextMessage;
    FrameLayout pantallaActual;
    Fragment fragment;
    FragmentManager fragmentManager;

    int x=1; // codigo nada
    MediaPlayer p;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            p.start();
            switch (item.getItemId()) {
                case R.id.about_empresa:
                    fragment=new FragmentEmpresa_AcercaDe();
                    pantallaActual.setBackgroundDrawable(getResources().getDrawable(R.drawable.empresa_acercade_fondo));
                    mTextMessage.setText(R.string.title_about_empresa);
                    break;
                case R.id.about_people:
                    fragment=new FragmentIntegrantes_AcercaDe();
                    pantallaActual.setBackgroundDrawable(getResources().getDrawable(R.drawable.grupotrabajo_acercade_fondo));
                    mTextMessage.setText(R.string.title_about_personas);
                    break;
                case R.id.about_app:
                    fragment=new FragmentApp_AcercaDe();
                    pantallaActual.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_acercade_fondo));
                    mTextMessage.setText(R.string.title_about_app);
                    break;
            }
            final FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor_acercade,fragment).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        mTextMessage = (TextView) findViewById(R.id.message);
        pantallaActual=(FrameLayout) findViewById(R.id.frameAcercaDe);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        p=MediaPlayer.create(this,R.raw.videorecord);
        fragmentManager=getSupportFragmentManager();

    }

}
