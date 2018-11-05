package com.example.usuario.maptu.Negocio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by USUARIO on 15/10/2017.
 */

public class PaginaAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    ArrayList<String> datosNegocio;

    public PaginaAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> datosNegocio) {
        super(fm);
        this.datosNegocio=new ArrayList<>();
        this.datosNegocio=datosNegocio;
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle argumentos=new Bundle();
        argumentos.putStringArrayList("datosNegocio",datosNegocio);
        switch (position) {
            case 0: // fragment informacion
                FrgNegocio_Informacion tab1 = new FrgNegocio_Informacion();
                tab1.setArguments(argumentos);
                return tab1;
            case 1: // fragment direccion
                FrgNegocio_Direccion tab2 = new FrgNegocio_Direccion();
                return tab2;
            case 2: // fragment Horario
                FrgNegocio_Horario tab3 = new FrgNegocio_Horario();
                return tab3;
            case 3: // fragment menu
                FrgNegocio_Menu tab4 = new FrgNegocio_Menu();
                return tab4;
            default: // fragment ubicacion
                return new FrgNegocio_Ubicacion();
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
