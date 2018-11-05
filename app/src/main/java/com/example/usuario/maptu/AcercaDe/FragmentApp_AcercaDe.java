package com.example.usuario.maptu.AcercaDe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.maptu.R;

/**
 * Created by USUARIO on 18/06/2017.
 */

public class FragmentApp_AcercaDe extends Fragment {

    public FragmentApp_AcercaDe(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_acercade_app,container,false);
    }


    private void eliminar(){

    }
}
