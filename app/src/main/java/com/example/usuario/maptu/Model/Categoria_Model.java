package com.example.usuario.maptu.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by USUARIO on 24/07/2017.
 */

public class Categoria_Model {

    private int ID;
    private String Nombre;
    private String Descripcion;
    private String DataImagen;
    private Bitmap Imagen;

    public Categoria_Model() {
    }

    public Categoria_Model(int ID, String nombre, String descripcion, Bitmap imagen) {
        this.ID = ID;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getDataImagen() {
        return DataImagen;
    }

    public void setDataImagen(String dataImagen) {
        DataImagen = dataImagen;
        try{
            byte[] byteData= Base64.decode(this.getDataImagen(),Base64.DEFAULT);
            this.Imagen = BitmapFactory.decodeByteArray(byteData,0,byteData.length);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap getImagen() {
        return Imagen;
    }


}
