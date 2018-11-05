package com.example.usuario.maptu.Negocio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.usuario.maptu.R;
import com.example.usuario.maptu.Model.Negocio_Model;
import com.example.usuario.maptu.Model.Ubicacion_Model;
import com.example.usuario.maptu.Webservices.Constantes;
import com.example.usuario.maptu.Webservices.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by USUARIO on 11/06/2017.
 */

public class FragmentNegocio extends Fragment {

    private NegocioListener listener;

    private ListView lvNegocios;
    private int categoriaSelectIndex;

    private SwipeRefreshLayout swNegocios;
    private Negocio_Model[] vNegocio=null;
    private ArrayList<Negocio_Model> listNegocio;
    private AdaptadorNegocio adaptadorNegocio;

    private EditText textoSearch;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_negocios, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.search2);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        /*try{

        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    //Toast.makeText(getActivity().getApplicationContext(),"sas",Toast.LENGTH_SHORT).show();
                    adaptadorNegocio.filter("");
                     lvNegocios.clearTextFilter();
                } else {
                    adaptadorNegocio.filter(newText);
                    //Toast.makeText(getActivity().getApplicationContext(),"sas",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        //return true;
        //return super.onCreateOptionsMenu(menu);
    }

    private void cargarDatosNegocios(int idCategoria,final int tipo){
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Cargando Negocios", "Please wait...",false,false);
        JsonObjectRequest peticion=new JsonObjectRequest(Request.Method.GET,
                Constantes.HTTP_NEGOCIO +"?accion=1&idcategoria="+String.valueOf(idCategoria)+"&tipo="+String.valueOf(tipo),
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        procesarListadoNegocios(response,tipo);
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


     /**
      * select n.*,ifnull(t.Nro,'s/n') as telef
      from negocio n
      left join telefono t on t.IDNegocio=n.ID

      -- select @v=count(*) from usuario n,telefono t where t.IDUsuario=n.ID and n.ID=12;
      -- select @v
      -- select if(@v>0,'hay','no hay')
      * */

    public void procesarListadoNegocios(JSONObject response,int tipo) {
        //vNegocio=null;
        listNegocio=new ArrayList<>();
        try {
            String estado=response.getString("estado");
            if (estado.equals("1")){ // peticion exitosa
                JSONArray array=response.getJSONArray("negocios");
                //vNegocio=new Negocio_Model[array.length()];
                for(int i=0;i<array.length();i++){
                    JSONObject negocioActual=array.getJSONObject(i);
                    Negocio_Model negocio_model=new Negocio_Model(array.length());
                    negocio_model.setId(negocioActual.getInt("Id"));
                    negocio_model.setNombre(negocioActual.getString("nombre"));
                    negocio_model.setDescripcion(negocioActual.getString("descripcion"));
                    negocio_model.setDireccion(negocioActual.getString("direccion"));
                    negocio_model.setHorario(negocioActual.getString("horario"));
                    negocio_model.setLink(negocioActual.getString("link"));
                    negocio_model.setTelefono(negocioActual.getString("telefono"));
                    //negocio_model.setCorreoElectronico(negocioActual.getString("correo"));
                    negocio_model.setDataImagenes(negocioActual.getString("imagen"),0);
                    Ubicacion_Model ubicacionActual=null;
                    if (tipo==1){ // cargar las ubicaciones
                        ubicacionActual=new Ubicacion_Model(negocioActual.getDouble("latitud"),negocioActual.getDouble("longitud"));
                    }
                    negocio_model.setUbicacionLugar(ubicacionActual);
                    negocio_model.setCorreoElectronico(negocioActual.getString("email"));
                    //Ubicacion_Model ubicacionActual=new Ubicacion_Model(negocioActual.getDouble("latitud"),negocioActual.getDouble("longitud"));
                    //negocio_model.setUbicacionLugar(null);
                    //vNegocio[i]=negocio_model;
                    listNegocio.add(negocio_model);
                }
                adaptadorNegocio=new AdaptadorNegocio(this);
                lvNegocios.setAdapter(adaptadorNegocio);
                //Toast.makeText(context2.getApplicationContext(),"ya esta",Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),array.getJSONObject(1).getString("nombre").toString(),Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Context context2;
    @Override
    public void onAttach(Context context) {
        context2=context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        return  inflater.inflate(R.layout.fragment_listado_negocios,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //CollapsingToolbarLayout toolbarLayout=(CollapsingToolbarLayout)getView().findViewById(R.id.toolbar_layout2);
        //toolbarLayout.setTitle(getNombreCategoria(categoriaSelectIndex));
        lvNegocios = (ListView) getView().findViewById(R.id.lstNegocios);
        lvNegocios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onNegocioSeleccionado((Negocio_Model) lvNegocios.getAdapter().getItem(position));
                }
            }
        });
        if (categoriaSelectIndex<=5){ // negocios con ubicacion
            cargarDatosNegocios(categoriaSelectIndex,1);
        } else { // negocios sin ubicacion
            cargarDatosNegocios(categoriaSelectIndex,2);
        }

        //lvNegocios.setAdapter(adaptadorNegocio);
        swNegocios = (SwipeRefreshLayout)getView().findViewById(R.id.swipeNegocios);
        swNegocios.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (categoriaSelectIndex<=5){
                                    cargarDatosNegocios(categoriaSelectIndex,1);
                                } else {
                                    cargarDatosNegocios(categoriaSelectIndex,2);
                                }
                                swNegocios.setRefreshing(false);
                            }
                        }, 3000);
                    }
                });
        swNegocios.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }





    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface NegocioListener{
        void onNegocioSeleccionado(Negocio_Model negocio);
    }

    public void setNegocioListener(NegocioListener negocioListener,int posSelect){
        this.listener=negocioListener;
        this.categoriaSelectIndex=posSelect;

    }


    private class AdaptadorNegocio extends BaseAdapter/*implements Filterable*/ {//ArrayAdapter<Negocio_Model>{

        // UTILIZAR EL XML de la lvNegocios de categorias
        Activity context;
        private ArrayList<Negocio_Model> negocios;
        private ArrayList<Negocio_Model> negociosFilter;
        LayoutInflater inflater;

        public AdaptadorNegocio(Fragment context) {
            //super(context.getActivity(),R.layout.listview_categorias,negocios);
            this.context=context.getActivity();
            this.negocios=listNegocio;
            this.negociosFilter=new ArrayList<>();
            this.negociosFilter.addAll(negocios);
        }

        @Override
        public int getCount() {
            return negocios.size();
        }

        @Override
        public Object getItem(int position) {
            return negocios.get(position);
        }

        @Override
        public long getItemId(int position) {
            return negocios.get(position).getId();
        }

        public View getView(int position, View item, ViewGroup parent){
            /*LayoutInflater */inflater = context.getLayoutInflater();
            //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(item == null){
                item = inflater.inflate(R.layout.listview_categorias, parent, false);
            }
           // View item = inflater.inflate(R.layout.listview_categorias, null);

            TextView txtNombre = (TextView)item.findViewById(R.id.tituloCategoria);
            TextView txtDescripcion = (TextView)item.findViewById(R.id.descCategoria);
            ImageView imageView = (ImageView) item.findViewById(R.id.icon);
            TextView txtDirec = (TextView) item.findViewById(R.id.aux1);
            TextView txtTelef = (TextView) item.findViewById(R.id.aux2);
            TextView txtHorario = (TextView) item.findViewById(R.id.aux3);
            TextView txtID=(TextView)item.findViewById(R.id.auxid);

            txtNombre.setText(negocios.get(position).getNombre());
            txtDescripcion.setText(null);
            //txtDescripcion.setText(negocios.get(position).getDescripcion());
            imageView.setImageBitmap(negocios.get(position).getImagen(0));
            txtDirec.setText(negocios.get(position).getDireccion());
            txtTelef.setText(negocios.get(position).getTelefono());
            txtHorario.setText(negocios.get(position).getHorario());
            /*txtNombre.setText(vNegocio[position].getNombre());

            TextView txtDescripcion = (TextView)item.findViewById(R.id.descCategoria);
            txtDescripcion.setText(null);//vNegocio[position].getDescripcion());

            ImageView imageView = (ImageView) item.findViewById(R.id.icon);
            //imageView.setImageResource(vNegocio[position].get);
            imageView.setImageBitmap(vNegocio[position].getImagen(0));
            TextView txtDirec = (TextView) item.findViewById(R.id.aux1);
            TextView txtTelef = (TextView) item.findViewById(R.id.aux2);
            TextView txtHorario = (TextView) item.findViewById(R.id.aux3);
            TextView txtID=(TextView)item.findViewById(R.id.auxid);

            txtDirec.setText(vNegocio[position].getDireccion());
            txtTelef.setText(vNegocio[position].getTelefono());
            txtHorario.setText(vNegocio[position].getHorario());
            */
            return(item);
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            negocios.clear();
            if (charText.length() == 0) {
                negocios.addAll(negociosFilter);
            } else {
                for (Negocio_Model negocioActual : negociosFilter) {
                    if (negocioActual.getNombre().toLowerCase(Locale.getDefault()).startsWith(charText)) {
                        negocios.add(negocioActual);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

}
