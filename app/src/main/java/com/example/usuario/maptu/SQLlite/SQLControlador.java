package com.example.usuario.maptu.SQLlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador {

    private DBHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {

        dbhelper.close();
    }

    public int cantTuplasSQL(String consultaSQL){
        return database.rawQuery(consultaSQL,null).getCount();
    }
    public void insertarDatos(String email,String password) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.USER_EMAIL, email);
        cv.put(DBHelper.USER_PASS,password);
        database.insert(DBHelper.TABLE_USER, null, cv);
    }

    public Cursor leerDatos() {
        String[] todasLasColumnas = new String[] {
                DBHelper.USER_ID,
                DBHelper.USER_EMAIL
        };
        Cursor c = database.query(DBHelper.TABLE_USER, todasLasColumnas, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public int actualizarDatos(long iduser, String emailNew,String passNew) {
        ContentValues cvActualizar = new ContentValues();
        cvActualizar.put(DBHelper.USER_EMAIL, emailNew);
        cvActualizar.put(DBHelper.USER_PASS,passNew);
        int i = database.update(DBHelper.TABLE_USER, cvActualizar, DBHelper.USER_ID + " = " + iduser, null);
        return i;
    }

    public void deleteData(long iduser) {
        database.delete(DBHelper.TABLE_USER, DBHelper.USER_ID + "="
                + iduser, null);
    }
}