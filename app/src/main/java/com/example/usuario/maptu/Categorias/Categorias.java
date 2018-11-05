package com.example.usuario.maptu.Categorias;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.usuario.maptu.Negocio.NegocioHeader;
import com.example.usuario.maptu.R;
import com.example.usuario.maptu.Model.Categoria_Model;
import com.example.usuario.maptu.Webservices.Constantes;
import com.example.usuario.maptu.Webservices.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class Categorias extends AppCompatActivity {

    ListView listaCategorias;
    CategoriasAdapter adaptadorCategorias;
    MediaPlayer mediaPlayer;
    private SwipeRefreshLayout swipeRefreshLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mediaPlayer = MediaPlayer.create(this, R.raw.videorecord);
        listaCategorias = (ListView) findViewById(R.id.list1);

        cargarCategorias();
        //adaptadorCategorias=new CategoriasAdapter(this,new ArrayList<Categoria_Model>());
        //adaptadorCategorias=new CategoriasAdapter(this,nombreCat,idImagenes,descripCat,a1,a2,a3,idCat);
        listaCategorias.setClickable(true);
        //buscador=(SearchView)findViewById(R.id.buscarCat);
        // hola mundo555
        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mediaPlayer.start();
                Intent intentNegocioHeader = new Intent(getApplicationContext(), NegocioHeader.class);
                intentNegocioHeader.putExtra("indexCategoriaSelect", String.valueOf(position + 1)); // id categoria
                startActivity(intentNegocioHeader);
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cargarCategorias();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 3000);
                    }
                });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    public void startProgressBar(String titulo, String message) {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setTitle(titulo);
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgress(0);
        progress.show();
        progress.setIcon(android.R.drawable.ic_menu_gallery);
        final int totalProgressTime = 20;
        progress.setMax(totalProgressTime);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        /*Toast.makeText(context.getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);*/
                        progress.dismiss(); //finish();
                    }
                }, 2000);
    }


    private void cargarCategorias() {
        final ProgressDialog loading = ProgressDialog.show(this,"Cargando Categorias", "Please wait...",false,false);
        VolleySingleton.getInstance(this).
                addToRequestQueue(new JsonObjectRequest(Request.Method.GET,
                        Constantes.GET_CATEGORIAS,
                        new JSONObject(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                loading.dismiss();
                                procesarListadoCategorias(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Error Volley: " + error.getMessage());
                            }
                        }
                ));
    }

    private void procesarListadoCategorias(JSONObject response) {
        ArrayList<Categoria_Model> lista = new ArrayList<>();
        try {
            String estado = response.getString("estado");
            if (estado.equals("1")) { // peticion exitosa
                JSONArray array = response.getJSONArray("categorias");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject catActual = array.getJSONObject(i);
                    Categoria_Model categoria_model = new Categoria_Model();
                    categoria_model.setID(catActual.getInt("Id"));
                    categoria_model.setNombre(catActual.getString("nombre"));
                    categoria_model.setDescripcion(catActual.getString("descripcion"));
                    categoria_model.setDataImagen(catActual.getString("imagen"));
                    lista.add(categoria_model);
                }
                adaptadorCategorias = new CategoriasAdapter(this, lista);
                listaCategorias.setAdapter(adaptadorCategorias);
                //Toast.makeText(getApplicationContext(),array.getJSONObject(1).getString("nombre").toString(),Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categorias, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.search);
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
                    adaptadorCategorias.filter("");
                    listaCategorias.clearTextFilter();
                } else {
                    adaptadorCategorias.filter(newText);
                }
                return true;
            }
        });
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    // ADAPTAR LAS IMAGENES PARA EL LISTVIEW DE CATEGORIAS
    private class CategoriasAdapter extends BaseAdapter{

        private ArrayList<Categoria_Model> categorias;
        private ArrayList<Categoria_Model> categoriasFilter;
        private Context context;
        LayoutInflater inflater;

        public CategoriasAdapter(Context context, ArrayList<Categoria_Model> categoriaModels) {
            this.context = context;
            this.categorias = categoriaModels;
            this.categoriasFilter = new ArrayList<>();
            this.categoriasFilter.addAll(categorias);
        }

        @Override
        public int getCount() {
            return categorias.size();
        }

        @Override
        public Object getItem(int position) {
            return categorias.get(position);
        }

        @Override
        public long getItemId(int position) {
            return categorias.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_categorias, parent, false);
            }
            TextView txtTitle = (TextView) convertView.findViewById(R.id.tituloCategoria);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
            TextView txtDescripcion = (TextView) convertView.findViewById(R.id.descCategoria);
            TextView txtDirec = (TextView) convertView.findViewById(R.id.aux1);
            TextView txtTelef = (TextView) convertView.findViewById(R.id.aux2);
            TextView txtHorario = (TextView) convertView.findViewById(R.id.aux3);
            TextView txtId = (TextView) convertView.findViewById(R.id.auxid);
            //Capture position and set to the TextViews
            /*txtTitle.setText(Datos.get(position).getTitulo());
            imgImg.setImageResource(Datos.get(position).getImg());*/
            txtTitle.setText(categorias.get(position).getNombre());
            imageView.setImageBitmap(categorias.get(position).getImagen());
            txtDescripcion.setText(categorias.get(position).getDescripcion());
            txtDirec.setText("");
            txtTelef.setText("");
            txtHorario.setText("");
            txtId.setText(String.valueOf(categorias.get(position).getID()));
            return convertView;
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            categorias.clear();
            if (charText.length() == 0) {
                categorias.addAll(categoriasFilter);
            } else {
                for (Categoria_Model categoriaActual : categoriasFilter) {
                    if (categoriaActual.getNombre().toLowerCase(Locale.getDefault()).startsWith(charText)) {
                        categorias.add(categoriaActual);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}


        /*private final Activity context;
        private final String[] ItemName;
        private final Bitmap[] Imagenes;
        private final String[] descripcion;
        private final String[] direccion;
        private final String[] telefono;
        private final String[] horarios;
        private final int[] id;*/

        //private CategoriaFilter categoriaFilter;

        /*public CategoriasAdapter(Activity context, String[] itemName,Bitmap[] imagenes,String[] descripcion,String[]direccion,String[]telefono,String[]horarios,int[] id) {
            super(context, R.layout.listview_categorias,itemName);
            this.context = context;
            this.ItemName = itemName;
            this.Imagenes = imagenes;
            this.descripcion=descripcion;
            this.direccion=direccion;
            this.telefono=telefono;
            this.horarios=horarios;
            this.id=id;
            getFilter();
        }

        public View getView(int posicion,View view, ViewGroup parent){

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.listview_categorias,null,true);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.tituloCategoria);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView txtDescripcion = (TextView) rowView.findViewById(R.id.descCategoria);
            TextView txtDirec = (TextView) rowView.findViewById(R.id.aux1);
            TextView txtTelef = (TextView) rowView.findViewById(R.id.aux2);
            TextView txtHorario = (TextView) rowView.findViewById(R.id.aux3);
            TextView txtId=(TextView)rowView.findViewById(R.id.auxid);

            txtTitle.setText(ItemName[posicion]);
            imageView.setImageBitmap(Imagenes[posicion]);
            txtDescripcion.setText(descripcion[posicion]);
            //imageView.setImageResource(Imagenes[posicion]);
            //txtDescripcion.setText("Description "+ItemName[posicion]);
            txtDirec.setText(direccion[posicion]);
            txtTelef.setText(telefono[posicion]);
            txtHorario.setText(horarios[posicion]);

            txtId.setText(String.valueOf(id[posicion]));

            return rowView;
        }

    }
}*/
