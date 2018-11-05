package com.example.usuario.maptu.Inicio;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.maptu.AcercaDe.AcercaDe;
import com.example.usuario.maptu.Categorias.Categorias;
import com.example.usuario.maptu.Contacto.Contacto;
import com.example.usuario.maptu.Global;
import com.example.usuario.maptu.Login.MapaMaptu;
import com.example.usuario.maptu.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//import com.android.volley.toolbox.Volley;

public class Index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView lblIndexUser;
    MediaPlayer mediaPlayer1;
    public double latitudGPS, longitudGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme2);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        SharedPreferences preferences = getSharedPreferences("preferencias_maptu", Context.MODE_PRIVATE);
        //lblIndexUser=(TextView)findViewById(R.id.lblUserDescripcion);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        lblIndexUser = (TextView) hView.findViewById(R.id.lblUserDescripcion);

        if (Global.intentInicio.equals("index")){ // intent null
            //lblIndexUser.setText(preferences.getString("user_login", ""));
            lblIndexUser.setText(preferences.getString("user_fix",""));
            /*if (Global.auxUserIni.length()>0){
                lblIndexUser.setText(Global.auxUserIni);
            } else {
                lblIndexUser.setText(preferences.getString("user_login", ""));
            }*/
        } else { // intent llega desde el login
            lblIndexUser.setText(getIntent().getStringExtra("email_user"));
            //lblIndexUser.setText(preferences.getString("user_login", ""));
        }
        mediaPlayer1 = MediaPlayer.create(this, R.raw.videorecord);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (intervalo+tiempoClick>System.currentTimeMillis()){
                SharedPreferences preferences=getSharedPreferences("preferencias_maptu", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorPreferencias =preferences.edit();
                editorPreferencias.putBoolean("save_sesion",true);
                editorPreferencias.commit();
                finishAffinity();
                //super.finish();
                //super.onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(),"Presiona dos veces para salir de MapTu",Toast.LENGTH_SHORT).show();
            }
            tiempoClick=System.currentTimeMillis();
            mediaPlayer1.start();
            //super.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private static final int intervalo=2000;
    private long tiempoClick;
    @Override
    public void onBackPressed() {
        //super.finish(); return;

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        mediaPlayer1.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //getTheme().applyStyle(R.style.AppTheme2,true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mediaPlayer1.start();
        if (id == R.id.categorias) {
            irOpcionCategorias();
        } else if (id == R.id.ofertas) {
            opcionEnDesarrollo();
            //Toast.makeText(getApplicationContext(),"Opcion no disponible aun",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.contactenos) {
            irOpcionContacto();
            //Toast.makeText(getApplicationContext(),"Latitud = "+latitudGPS+" , Longitud = "+longitudGPS,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"Opcion no disponible aun",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.acerca_de) {
            irOpcionAcercaDe();
        } else if (id == R.id.lugares_Proximos) {
            irOpcionLugaresCercanos();
        } else if (id == R.id.perfil) {
            opcionEnDesarrollo();
            //Toast.makeText(getApplicationContext(),"Opcion no disponible aun",Toast.LENGTH_SHORT).show();
        } else {
            backLogin();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void irOpcionContacto() {
        Intent contactoActivity=new Intent(this,Contacto.class);
        contactoActivity.putExtra("user_email",lblIndexUser.getText().toString());
        startActivity(contactoActivity);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
    }

    private void opcionEnDesarrollo() {
        AlertDialog.Builder alertEnProgreso = new AlertDialog.Builder(this);
        alertEnProgreso.setTitle("Opciones");
        alertEnProgreso.setMessage("Maptu esta desarrollando actualmente esta opcion, pronto la tendras disponible.\n\nGracias por la comprension!!!");
        alertEnProgreso.setCancelable(true);
        alertEnProgreso.setIcon(R.mipmap.ic_settings_black_24dp);
        alertEnProgreso.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertEnProgreso.create();
        alert11.show();
    }

    private void irOpcionLugaresCercanos() {

        Intent intentMapa=new Intent(this,MapaMaptu.class);
        intentMapa.putExtra("latitudActual",String.valueOf(latitudGPS));
        intentMapa.putExtra("longitudActual",String.valueOf(longitudGPS));
        startActivity(intentMapa);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
    }

    private  void irOpcionAcercaDe(){
        startActivity(new Intent(Index.this,AcercaDe.class));
        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
    }

    public void verificarOutSession() {
        new AlertDialog.Builder(Index.this)
                .setTitle("Sesion MapTu")
                .setMessage("¿Desea cerrar sesion y salir de la aplicacion?")
                .setIcon(R.drawable.ic_supervisor_account_black_24dp)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences preferences=getSharedPreferences("preferencias_maptu", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editorPreferencias =preferences.edit();
                                editorPreferencias.putBoolean("save_sesion",false);
                                editorPreferencias.commit();
                                finishAffinity();
                                //Index.super.finish();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        //showToast("Este ejemplo no fue impresionante para usted. :(");
                        dialog.cancel();
                    }
                }).show();
    }

    private void backLogin(){
        MediaPlayer p=MediaPlayer.create(this,R.raw.initiated);
        p.start();
        verificarOutSession();
        //startActivity(new Intent(this,LoginActivity.class));
    }
    private void irOpcionCategorias(){
        Intent mainCategorias=new Intent(getApplicationContext(),Categorias.class);
        startActivity(mainCategorias);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                    //messageTextView2.setText("Mi direccion es: \n" + address.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
