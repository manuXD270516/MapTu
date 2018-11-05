package com.example.usuario.maptu.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by USUARIO on 11/06/2017.
 */

public class Negocio_Model {

    private int id;
    private String Nombre;
    private String Descripcion;
    private String Direccion;
    private String Telefono;
    private String Horario;
    private String CorreoElectronico;
    private Ubicacion_Model UbicacionLugar;
    private String Link;
    private Bitmap[] Imagenes;
    private String[] DataImagenes;

    public Negocio_Model(int longitud) {
        Imagenes=new Bitmap[longitud];
        DataImagenes=new String[longitud];
    }

    public Negocio_Model(String nombre, String descripcion){
        Nombre = nombre;
        Descripcion = descripcion;
    }

    public Negocio_Model(String nombre, Bitmap[] imagenes){
        this.Nombre=nombre;
        this.Imagenes=imagenes;
    }

    public Negocio_Model(String nombre, String descripcion, Bitmap[] imagenes) {
        Nombre = nombre;
        Descripcion = descripcion;
        Imagenes=imagenes;
    }

    // todos los parametros


    public Negocio_Model(int id, String nombre, String descripcion, String direccion, String telefono, String horario, Ubicacion_Model ubicacionLugar, String link, Bitmap[] imagenes) {
        this.id=id;
        Nombre = nombre;
        Descripcion = descripcion;
        Direccion = direccion;
        Telefono = telefono;
        Horario = horario;
        UbicacionLugar = ubicacionLugar;
        Link = link;
        Imagenes=imagenes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public Ubicacion_Model getUbicacionLugar() {
        return UbicacionLugar;
    }

    public void setUbicacionLugar(Ubicacion_Model ubicacionLugar) {
        UbicacionLugar = ubicacionLugar;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        this.Link = link;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDataImagenes(int pos) {
        return DataImagenes[pos];
    }

    public void setDataImagenes(String dataImagen,int pos) {
        DataImagenes[pos] = dataImagen;
        try{
            byte[] byteData= Base64.decode(this.getDataImagenes(pos),Base64.DEFAULT);
            this.Imagenes[pos] = BitmapFactory.decodeByteArray(byteData,0,byteData.length);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap getImagen(int pos) {
        return Imagenes[pos];
    }


    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }
}
