package com.example.usuario.maptu.Negocio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usuario.maptu.R;

import java.util.ArrayList;

/**
 * Created by USUARIO on 15/10/2017.
 */

public class FrgNegocio_Direccion extends Fragment implements View.OnClickListener {

    private LinearLayout l1,l2,l3,l4;
    private ArrayList<String> datosImport;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);4
        super.onCreateView(inflater, container, savedInstanceState);
        //setHasOptionsMenu(true);
        return  inflater.inflate(R.layout.frgnegocio_direccion,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datosImport=FragmentNegocioDetalle.datosExport;
        l1=(LinearLayout)getView().findViewById(R.id.llTelefono);
        l2=(LinearLayout)getView().findViewById(R.id.llFacebook);
        l3=(LinearLayout)getView().findViewById(R.id.llPaginaWeb);
        l4=(LinearLayout)getView().findViewById(R.id.llEmail);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llTelefono:
                //realizarLlamada(datosImport.get(1));
                break;
            case R.id.llFacebook:
                //verLink(datosImport.get(2));
                break;
            case R.id.llPaginaWeb:
                //verLink(datosImport.get(2));
            default:
                //verContactoEmail(d);
                break;
        }
    }
}
