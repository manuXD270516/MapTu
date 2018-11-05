package com.example.usuario.maptu.Negocio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.maptu.R;

/**
 * Created by USUARIO on 15/10/2017.
 */

public class FrgNegocio_Horario extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //setHasOptionsMenu(true);
        return  inflater.inflate(R.layout.frgnegocio_horario,container,false);
    }
}
