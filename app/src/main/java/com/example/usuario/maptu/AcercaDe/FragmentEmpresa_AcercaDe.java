package com.example.usuario.maptu.AcercaDe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.usuario.maptu.R;

/**
 * Created by USUARIO on 18/06/2017.
 */

public class FragmentEmpresa_AcercaDe extends Fragment{

    ImageView imageViewFb,imageViewTw;

    public FragmentEmpresa_AcercaDe(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_acercade_empresa,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageViewFb=(ImageView)getView().findViewById(R.id.imgFacebook);
        imageViewFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFb=new Intent(Intent.ACTION_VIEW);
                intentFb.setData(Uri.parse("https://m.facebook.com/App-comunicaciones-SRL-1208521972604579/?tsid&log=157&seq=1786777834&rk=0&fbtype=274"));
                startActivity(intentFb);
            }
        });

        imageViewTw=(ImageView)getView().findViewById(R.id.imgTwitter);
        imageViewTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTw=new Intent(Intent.ACTION_VIEW);
                intentTw.setData(Uri.parse("https://mobile.twitter.com/Maptu_App"));
                startActivity(intentTw);
            }
        });

    }
}
