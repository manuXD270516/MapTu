package com.example.usuario.maptu.Negocio;

import com.example.usuario.maptu.R;
import com.example.usuario.maptu.Model.Negocio_Model;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class NegocioHeader extends AppCompatActivity implements FragmentNegocio.NegocioListener {

    public int select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio_header);
        select=Integer.parseInt(getIntent().getStringExtra("indexCategoriaSelect"));
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        toolbar.setTitle(getNombreCategoria(select));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentNegocio fragmentNegocio=(FragmentNegocio) getSupportFragmentManager().findFragmentById(R.id.FrgNegocio);
        //Toast.makeText(this,getIntent().getStringExtra("indexCategoriaSelect") ,Toast.LENGTH_SHORT).show();
        fragmentNegocio.setNegocioListener(this,select);
    }

    // dar el nombre al toolbar para la categoria respectiva
    private String getNombreCategoria(int categoriaSelectIndex) {
        switch (categoriaSelectIndex){
            case 1:
                return"Restaurantes";
            case 2:
                return"Bares, Discotecas y Karaokes ";
            case 3:
                return"Salones de Belleza";
            case 4:
                return"Clinicas Medicas";
            case 5:
                return"Lugares Turisticos";
            case 6:
                return"Numeros de Emergencia";
            case 7:
                return"Radiotaxis";
            default:
                return "Periodicos";
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
        //return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onNegocioSeleccionado(Negocio_Model negocio) {
        boolean hayDetalle = (getSupportFragmentManager().findFragmentById(R.id.FrgNegocioDetalle) != null);
        if (hayDetalle) { // hay un fragmento para abrir dentro de una tablet
            FragmentNegocioDetalle fragmentNegDetalle=((FragmentNegocioDetalle) getSupportFragmentManager().findFragmentById(R.id.FrgNegocioDetalle));
            fragmentNegDetalle.mostrarDetalle(negocio.getNombre(),negocio.getDescripcion(),negocio.getDireccion(),negocio.getTelefono(),negocio.getHorario(),negocio.getDataImagenes(0),String.valueOf(select),String.valueOf(negocio.getId()),negocio.getLink(),negocio.getCorreoElectronico());
            /*((FragmentNegocioDetalle) getSupportFragmentManager().findFragmentById(R.id.FrgNegocioDetalle))
                    .mostrarDetalle(negocio.getNombre(),negocio.getDescripcion(),negocio.getDireccion(),negocio.getTelefono(),negocio.getHorario(),negocio.getDataImagenes(0),String.valueOf(select),String.valueOf(negocio.getId()),negocio.getLink());*/
        } else { // crear una nueva actividad
            Intent detalle = new Intent(this, DetalleNegocio.class);
            //Toast.makeText(getApplicationContext(),"hola mundo ", Toast.LENGTH_SHORT).show();
            detalle.putExtra(DetalleNegocio.EXTRA_TITULO, negocio.getNombre());
            detalle.putExtra(DetalleNegocio.EXTRA_INFORMACION,negocio.getDescripcion());
            detalle.putExtra(DetalleNegocio.EXTRA_IDIMAGEN,negocio.getDataImagenes(0));
            detalle.putExtra(DetalleNegocio.EXTRA_DIRECCION,String.valueOf(negocio.getDireccion()));
            detalle.putExtra(DetalleNegocio.EXTRA_TELEFONO,String.valueOf(negocio.getTelefono()));
            detalle.putExtra(DetalleNegocio.EXTRA_HORARIO,String.valueOf(negocio.getHorario()));
            detalle.putExtra(DetalleNegocio.EXTRA_CATEGORIASELECT,String.valueOf(select));
            detalle.putExtra(DetalleNegocio.EXTRA_IDNEGOCIO,String.valueOf(negocio.getId()));
            detalle.putExtra(DetalleNegocio.EXTRA_LINK,negocio.getLink());
            detalle.putExtra(DetalleNegocio.EXTRA_EMAIL,negocio.getCorreoElectronico());
            startActivity(detalle);
            overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
        }
    }
}
