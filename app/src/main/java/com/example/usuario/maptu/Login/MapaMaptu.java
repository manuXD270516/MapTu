package com.example.usuario.maptu.Login;

/*import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;*/

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.usuario.maptu.R;
import com.example.usuario.maptu.Model.Negocio_Model;
import com.example.usuario.maptu.Model.Ubicacion_Model;
import com.example.usuario.maptu.Webservices.Constantes;
import com.example.usuario.maptu.Webservices.VolleySingleton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/*import android.location.LocationListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;*/


public class MapaMaptu extends FragmentActivity implements GoogleMap.OnMapClickListener,OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    private CameraUpdate cameraUpdate;
    public double latitud; //= -17.7700793;
    public double longitud; //= -63.2087748;
    public boolean obtenerGPS;
    private Button btnVerLugares;
    private ImageView b1,b2,b3,b4,b5;
    private ArrayList<Negocio_Model> listNegociosLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_maptu);
        /*latitud=Double.parseDouble(getIntent().getStringExtra("latitudActual"));
        longitud=Double.parseDouble(getIntent().getStringExtra("longitudActual"));
        */
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        obtenerGPS=true;
        b1=(ImageView)findViewById(R.id.imgCat1);
        b2=(ImageView)findViewById(R.id.imgCat2);
        b3=(ImageView)findViewById(R.id.imgCat3);
        b4=(ImageView)findViewById(R.id.imgCat4);
        b5=(ImageView)findViewById(R.id.imgCat5);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);


    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng pos = new LatLng(-17.8  ,  -63.16671);
        /*latitud=Double.parseDouble(getIntent().getStringExtra("latitudActual"));
        longitud=Double.parseDouble(getIntent().getStringExtra("longitudActual"));*/
        /*if (getIntent()!=null){
            latitud=Double.parseDouble(getIntent().getStringExtra("latitudActual"));
            longitud=Double.parseDouble(getIntent().getStringExtra("longitudActual"));
        }*/
        LatLng pos = new LatLng(latitud, longitud);
        mMap.setOnMapClickListener(this);

        if (obtenerGPS) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            } else {
                locationStart();
            }
            obtenerGPS = !obtenerGPS;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);

        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            // Show rationale and request permission.
        }



        mMap.addMarker(new MarkerOptions().position(pos).title("Casa del Camba!!"));
        cameraUpdate=CameraUpdateFactory.newLatLngZoom(pos,16);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.animateCamera(cameraUpdate);
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    /*@Override
    public void onMapClick(LatLng puntoPulsado) {
        mMap.addMarker(new MarkerOptions().position(puntoPulsado)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }*/

    public void moveCamera(View view) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud,longitud)));
    }

    public void animateCamera(View view) {
        if (mMap.getMyLocation() != null)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mMap.getMyLocation().getLatitude(),
                            mMap.getMyLocation().getLongitude()), 15));
    }



    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    /*Address DirCalle = list.get(0);
                    mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onClick(View v) {
        String response;
        if (b1.getId() == v.getId()) {
            response="RESTAURANTES";
        } else if (b2.getId()==v.getId()){
            response="BARES, DISCOTECAS Y KARAOKES";
        } else if (b3.getId()==v.getId()){
            response="SALONES DE BELLEZA";
        } else if (b4.getId()==v.getId()){
            response="CLINICAS Y CENTROS DE ATENCION MEDICA";
        } else{
            response="LUGARES TURISTICOS";
        }
        Toast mensaje = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
        mensaje.setGravity(Gravity.CENTER_HORIZONTAL, 5, 5);
        mensaje.show();
    }


    public class Localizacion implements LocationListener {
        MapaMaptu mainActivity;

        public MapaMaptu getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(MapaMaptu mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            latitud=loc.getLatitude();
            longitud=loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            //mensaje1.setText(Text);
            //Toast.makeText(getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            Toast.makeText(getApplicationContext(),"GPS Desactivado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            Toast.makeText(getApplicationContext(),"GPS Activado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    public void getLugares(View view){
        mMap.clear();
        listNegociosLocation=new ArrayList<>();
        final Dialog dialog = new Dialog(MapaMaptu.this);
        dialog.setTitle("Distancia[Km] maxima de radio");
        dialog.setContentView(R.layout.mapa_ingresar_parametro);
        final EditText txtDistancia = (EditText) dialog.findViewById(R.id.txtDistancia);
        Button btnMarcarLugares = (Button) dialog.findViewById(R.id.btnMarcarLugares);
        int width = (int) (MapaMaptu.this.getResources().getDisplayMetrics().widthPixels * 0.9);
        // set height for dialog
        int height = (int) (MapaMaptu.this.getResources().getDisplayMetrics().heightPixels * 0.40);
        dialog.getWindow().setLayout(width, height);
        btnMarcarLugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double distanciaSelect;
                if (!txtDistancia.getText().toString().isEmpty()){
                   distanciaSelect=Double.parseDouble(txtDistancia.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(),"SE SELECCIONARA DOS KM COMO DISTANCIA POR DEFECTO",Toast.LENGTH_LONG).show();
                    distanciaSelect=2.0;
                }
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        marcarLugaresCercanos(distanciaSelect);
                    }
                }, 3000);

            }
        });
        dialog.show();
    }

    private void marcarLugaresCercanos(double distancia) {
        JsonObjectRequest peticion=new JsonObjectRequest(Request.Method.GET,
                Constantes.HTTP_NEGOCIO +"?accion=3&km="+String.valueOf(distancia)+"&latitud="+String.valueOf(latitud)+"&longitud="+String.valueOf(longitud),
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarUbicacionesCercanas(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Error Volley: " + error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(peticion);

    }

    private void procesarUbicacionesCercanas(JSONObject response) {
        try {
            String estado=response.getString("estado");
            if (estado.equals("1")){ // peticion exitosa
                JSONArray array=response.getJSONArray("ubicaciones");
                //vNegocio=new Negocio_Model[array.length()];
                for(int i=0;i<array.length();i++){
                    JSONObject negocioActual=array.getJSONObject(i);
                    Negocio_Model negocio_model=new Negocio_Model(array.length());
                    //negocio_model.setId(negocioActual.getInt("Id"));
                    negocio_model.setNombre(negocioActual.getString("lugar"));
                    // parche para obtener la categoria del lugar
                    negocio_model.setDescripcion(negocioActual.getString("categoria"));
                    //negocio_model.setDescripcion(negocioActual.getString("descripcion"));
                    //negocio_model.setDireccion(negocioActual.getString("direccion"));
                    //negocio_model.setHorario(negocioActual.getString("horario"));
                    //negocio_model.setLink(negocioActual.getString("link"));
                    //negocio_model.setDataImagenes(negocioActual.getString("imagen"),0);
                    Ubicacion_Model ubicacionActual=new Ubicacion_Model(negocioActual.getDouble("latitud"),negocioActual.getDouble("longitud"));;
                    negocio_model.setUbicacionLugar(ubicacionActual);
                    //Ubicacion_Model ubicacionActual=new Ubicacion_Model(negocioActual.getDouble("latitud"),negocioActual.getDouble("longitud"));
                    //negocio_model.setUbicacionLugar(null);
                    //vNegocio[i]=negocio_model;
                    listNegociosLocation.add(negocio_model);
                }
                // una vez fuera
                for (Negocio_Model negocioActual:listNegociosLocation){
                    Random colorRandom=new Random();
                    Ubicacion_Model ubicacionAct=negocioActual.getUbicacionLugar();
                    LatLng puntoActual=new LatLng(ubicacionAct.getLatitud(),ubicacionAct.getLongitud());
                    float color;
                    switch (negocioActual.getDescripcion()){
                        case "Restaurantes":
                            color=BitmapDescriptorFactory.HUE_YELLOW;
                            break;
                        case "Bares, Discotecas y Karaokes":
                            color=BitmapDescriptorFactory.HUE_VIOLET;
                            break;
                        case "Salones de Belleza":
                            color=BitmapDescriptorFactory.HUE_MAGENTA;
                            break;
                        case "Clinicas y Centros de Atencion Medica":
                            color=BitmapDescriptorFactory.HUE_RED;
                            break;
                        default: // "Lugares Turisticos":
                            color=BitmapDescriptorFactory.HUE_GREEN;
                            break;
                    }
                    mMap.addMarker(new MarkerOptions().position(puntoActual).title(negocioActual.getNombre())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(color)));
                }
                //adaptadorNegocio=new FragmentNegocio.AdaptadorNegocio(this);
                //lvNegocios.setAdapter(adaptadorNegocio);
                //Toast.makeText(context2.getApplicationContext(),"ya esta",Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),array.getJSONObject(1).getString("nombre").toString(),Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }*/


}
