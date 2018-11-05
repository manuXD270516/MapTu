package com.example.usuario.maptu.Negocio;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.usuario.maptu.R;

public class DetalleNegocio extends AppCompatActivity {

    public static final String EXTRA_TITULO="titulo";
    public static final String EXTRA_INFORMACION="informacion";
    public static final String EXTRA_DIRECCION="direccion";
    public static final String EXTRA_TELEFONO="telefono";
    public static final String EXTRA_HORARIO="horarios";
    public static final String EXTRA_IDIMAGEN="idImagen";
    public static final String EXTRA_CATEGORIASELECT ="indiceCategoria" ;
    public static final String EXTRA_IDNEGOCIO = "idnegocio";
    public static final String EXTRA_LINK="link";
    public static final String EXTRA_EMAIL="correo";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_negocio);
        FragmentNegocioDetalle detalle =(FragmentNegocioDetalle) getSupportFragmentManager().findFragmentById(R.id.FrgNegocioDetalle);
        detalle.mostrarDetalle(getIntent().getStringExtra(EXTRA_TITULO),
                getIntent().getStringExtra(EXTRA_INFORMACION),
                getIntent().getStringExtra(EXTRA_DIRECCION),
                getIntent().getStringExtra(EXTRA_TELEFONO),
                getIntent().getStringExtra(EXTRA_HORARIO),
                getIntent().getStringExtra(EXTRA_IDIMAGEN),
                getIntent().getStringExtra(EXTRA_CATEGORIASELECT),
                getIntent().getStringExtra(EXTRA_IDNEGOCIO),
                getIntent().getStringExtra(EXTRA_LINK),
                getIntent().getStringExtra(EXTRA_EMAIL));
    }
}
