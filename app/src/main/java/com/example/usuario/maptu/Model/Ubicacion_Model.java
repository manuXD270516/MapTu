package com.example.usuario.maptu.Model;

/**
 * Created by USUARIO on 01/10/2017.
 */

public class Ubicacion_Model {

    public double Latitud;
    public double Longitud;

    public Ubicacion_Model(){
        //////////////
    }

    public Ubicacion_Model(double latitud, double longitud){
        this.Latitud=latitud;
        this.Longitud=longitud;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }
}
