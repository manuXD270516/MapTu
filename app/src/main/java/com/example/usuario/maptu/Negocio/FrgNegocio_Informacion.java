package com.example.usuario.maptu.Negocio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.maptu.R;

import java.util.ArrayList;

/**
 * Created by USUARIO on 15/10/2017.
 */

public class FrgNegocio_Informacion extends Fragment {

    public ArrayList<String> datosImport;
    TextView labelInfo;
    Bundle argumentos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //setHasOptionsMenu(true);
        View view= inflater.inflate(R.layout.frgnegocio_informacion,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datosImport=FragmentNegocioDetalle.datosExport;
        try{
            labelInfo=(TextView)getView().findViewById(R.id.labelInformacion);
            argumentos=getArguments();
            if (argumentos!=null){
                datosImport=argumentos.getStringArrayList("datosNegocio");
                if (!datosImport.isEmpty()){
                    labelInfo.setText(datosImport.get(0));
                }
            } else {
                labelInfo.setText("nada por aca");
            }
        } catch (Exception e){
            String er=e.getMessage().toString();
            int x=1;
        }

    }
}
