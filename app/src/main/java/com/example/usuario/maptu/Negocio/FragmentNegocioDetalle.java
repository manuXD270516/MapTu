package com.example.usuario.maptu.Negocio;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.usuario.maptu.Login.MapaMaptu;
import com.example.usuario.maptu.Menu_ItemNegocio;
import com.example.usuario.maptu.R;
import com.example.usuario.maptu.Model.Negocio_Model;
import com.example.usuario.maptu.Webservices.Constantes;
import com.example.usuario.maptu.Webservices.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USUARIO on 11/06/2017.
 */

public class FragmentNegocioDetalle extends Fragment {

    private Button bUbicar;//,bMenus,bLlamar;
    public String valor,idn;
    public ViewPager viewPager;
    public Bitmap[] mImages;
    public ArrayList<String> dataExport;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar=(Toolbar)getView().findViewById(R.id.toolbarNegocio);
        toolbar.setTitle("  Atras");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getContext().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //bUbicar=(Button)getView().findViewById(R.id.btnUbicacion);

        //TabLayout c=(TabLayout)getView().findViewById(R.id.tablayout);
        /*TabLayout tabLayout = (TabLayout)getView().findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.information));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.telefono));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.horario));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.menu));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.ubicacion));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager  viewPager = (ViewPager) getView().findViewById(R.id.pager);
        //Negocio_Model negocioInfo=
        final PaginaAdapter adapter = new PaginaAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),dataExport);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        /*bMenus=(Button)getView().findViewById(R.id.btnMenusItems);
        bLlamar=(Button)getView().findViewById(R.id.btnLLamar);*/
        //bMenusItems = (Button) getView().findViewById(R.id.btnMenusItems);
        /*bUbicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getActivity(),MapaMaptu.class);
                // MANDAR LOS PARAMETROS DE LATITUD Y LONGITUD PARA QUE MARQUE EL PUNTO
                /*x.putExtra("latitudActual",17.7700793);
                x.putExtra("longitudActual",63.2087748);
                startActivity(x);
            }
        });
       // viewPager=(ViewPager)getView().findViewById(R.id.view_pager);
        /*ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);*/
    }

    private void cargarImagenesNegocio(String idnegocio) {
        JsonObjectRequest peticion=new JsonObjectRequest(Request.Method.GET,
                Constantes.HTTP_NEGOCIO +"?accion=2&idnegocio="+idnegocio,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarImagenesNegocio(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Error Volley: " + error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(getActivity()).
                addToRequestQueue(peticion);
    }

    private void procesarImagenesNegocio(JSONObject response) {
        String estado = null;
        try {
            estado = response.getString("estado");
            if (estado.equals("1")) { // peticion exitosa
                JSONArray array = response.getJSONArray("imagenes");
                mImages = new Bitmap[array.length()];
                Negocio_Model negocio = new Negocio_Model(array.length());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject negocioJSON = array.getJSONObject(i);
                    negocio.setDataImagenes(negocioJSON.getString("imagen"), i);
                    mImages[i] = negocio.getImagen(i);
                }
            }
            ImagePagerAdapter adapter=new ImagePagerAdapter(mImages);
            viewPager.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class ImagePagerAdapter extends PagerAdapter{

        private Bitmap[] mImages;

        public ImagePagerAdapter(Bitmap[] imagenes){
            this.mImages=imagenes;
        }


        /*private int[] mImages = new int[]{
                R.drawable.appmoviles_acerca,
                R.drawable.app_acercade_fondo
        };*/
        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //return false;
            //return view == ((ImageView) object);
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = getContext();
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelSize(
                    R.dimen.padding_medium);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(mImages[position]);
            //imageView.setImageResource(mImages[position]);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
            //return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
            //super.destroyItem(container, position, object);
        }
    }

    public static ArrayList<String> datosExport;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_negocio_detalle, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void mostrarDetalle(String nombre, String informacion, String direc, String telef, String horarios, String ImagenDetalle, final String valorCat, final String idnegocio,String link,String correo) {
        //TextView txtDetalle=(TextView)getView().findViewById(R.id.txtDetalle_FragmentDetalleNegocio);
        viewPager=(ViewPager)getView().findViewById(R.id.view_pager);
        cargarImagenesNegocio(idnegocio);
        this.dataExport=new ArrayList<>();

        // MANDAR LA INFORMACION DEL NEGOCIO ( el nombre no se incluye)
        dataExport.add(informacion);

        dataExport.add(telef); // telefono
        dataExport.add(link); // pagina web
        dataExport.add(correo); // email

        dataExport.add(direc); // direccion fisica


        TabLayout tabLayout = (TabLayout)getView().findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.information));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.telefono));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.horario));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.menu));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.ubicacion));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager  viewPager = (ViewPager) getView().findViewById(R.id.pager);
        final PaginaAdapter adapter = new PaginaAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),dataExport);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TextView lblNombre = (TextView) getView().findViewById(R.id.twNombre);
        lblNombre.setText(nombre);
        /*Button bUbicar=(Button)getView().findViewById(R.id.btnUbicacion);
        Button bMenus=(Button)getView().findViewById(R.id.btnMenusItems);
        Button bLlamar=(Button)getView().findViewById(R.id.btnLLamar);*/

        /*bMenus.setVisibility(View.VISIBLE);
        bUbicar.setVisibility(View.VISIBLE);
        bLlamar.setVisibility(View.VISIBLE);*/
        /*Bundle args=new Bundle();
        args.putString("nombreNegocio",negocio);
        frgInformacion.setArguments(args);*/
        //TextView v=(TextView)getView().findViewById(R.id.labelInfo);
        //v.setText(negocio);

        /*final TextView lblInfo = (TextView) getView().findViewById(R.id.twDesc);

        TextView lblDirec = (TextView) getView().findViewById(R.id.twDirec);
        TextView lblTelf = (TextView) getView().findViewById(R.id.twTelefono);
        TextView lblHorario = (TextView) getView().findViewById(R.id.twHorarios);
        TextView lblLink = (TextView) getView().findViewById(R.id.twLink);
        //ImageView imageViewDetalle = (ImageView) getView().findViewById(R.id.imgDetalleNegocio);

        //lblNombre.setText(negocio);
        //imageViewDetalle.setImageDrawable(getResources().getDrawable(idImagenDetalle));
        Negocio_Model auxNeg=new Negocio_Model(1);

        //auxNeg.setDataImagenes(ImagenDetalle,0);
        //imageViewDetalle.setImageBitmap(auxNeg.getImagen(0));

        lblInfo.setText(informacion);*/


    }

    private void llamar(final String nro) {
        new AlertDialog.Builder(getContext())
                .setTitle("LLamada MapTu")
                .setMessage("¿Desea realizar esta llamada?")
                .setIcon(R.mipmap.ic_phone_iphone_black_24dp)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            String x=nro;
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                String textoLlamada = x;
                                String nroTelefonico=textoLlamada.substring(textoLlamada.indexOf(':')+1);
                                Intent llamadaIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+nroTelefonico));
                                getActivity().startActivity(llamadaIntent);
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        //showToast("Este ejemplo no fue impresionante para usted. :(");
                        dialog.cancel();
                    }
                }).show();
        //llamadaIntent.

    }


    /*@Override
    public void onClick(View v) {
        Toast.makeText(getActivity().getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();
        // evento para ver los menu de precios de cada tipo de item
        if (Integer.parseInt(valor)==1){ // solo tenemos una sola imagen

        }
        Intent intentMenuBar=new Intent(getActivity(),Menu_ItemNegocio.class);
        intentMenuBar.putExtra("categoriaSelect",String.valueOf(valor));
        intentMenuBar.putExtra("idnegocioSelect",String.valueOf(idn));
        getActivity().startActivity(intentMenuBar);
    }*/
}
