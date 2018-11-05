package com.example.usuario.maptu.SQLlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USUARIO on 23/06/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_USER = "usuario";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "correo";
    public static final String USER_PASS = "pass";

    static final String DB_NAME = "DBMAPTU";
    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table "
            + TABLE_USER + "(" + USER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_EMAIL + " TEXT NOT NULL, "
            + USER_PASS + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
