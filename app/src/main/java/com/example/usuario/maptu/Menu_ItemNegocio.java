package com.example.usuario.maptu;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Menu_ItemNegocio extends AppCompatActivity {

    ListView listaMenu;
    AdaptadorItemNegocio adaptadorItemNegocio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__item_negocio);

        listaMenu=(ListView)findViewById(R.id.lstMenuItem);
        int indiceSelect=Integer.parseInt(getIntent().getStringExtra("categoriaSelect"));
        int idnegocio=Integer.parseInt(getIntent().getStringExtra("idnegocioSelect"));

        //cargarMenusItem(indiceSelect,idnegocio-1);


    }

    /*private String[] cargarTITLE(int cant){
        String[] aux=new String[cant];
        for (int i=0;i<aux.length;i++){
            aux[i]="";
        }
        return aux;
    }
    private void cargarMenusItem(int categoriaSelect,int idnegocio) {
        ItemMenu[] x=null; String[] aux=null;
        if (categoriaSelect==1){
            x=cargarMenuBares();
            //new String[]{"","","","","","",""};
        } else {
            switch (categoriaSelect){// asignar las imagenes de cada categoria restante
                case 4:
                    x=cargarMenuLugaresTuristicos(idnegocio);
                    break;
            }
        }
        aux=cargarTITLE(x.length);
        adaptadorItemNegocio=new AdaptadorItemNegocio(this,aux,x);
        listaMenu.setAdapter(adaptadorItemNegocio);
    }

    private ItemMenu[] cargarMenuBares() {
        ItemMenu[] menu=new ItemMenu[]{
                new ItemMenu(R.drawable.pr1),
                new ItemMenu(R.drawable.pr2),
                new ItemMenu(R.drawable.pr3),
                new ItemMenu(R.drawable.pr4),
                new ItemMenu(R.drawable.pr5),
                new ItemMenu(R.drawable.pr6),
                new ItemMenu(R.drawable.pr7)
        };
        return menu;
    }

    private ItemMenu[] cargarMenuLugaresTuristicos(int idlugar){
        ItemMenu[] menu=new ItemMenu[1];
        switch (idlugar){
            case 0:
                menu[0]=new ItemMenu(R.drawable.ps1);
                break;
            case 1:
                menu[0]=new ItemMenu(R.drawable.ps2);
                break;
            case 2:
                menu[0]=new ItemMenu(R.drawable.ps3);
                break;
            case 3:
                menu[0]=new ItemMenu(R.drawable.ps4);
                break;
            case 4:
                menu[0]=new ItemMenu(R.drawable.ps5);
                break;
            case 5:
                menu[0]=new ItemMenu(R.drawable.ps6);
                break;
            case 6:
                menu[0]=new ItemMenu(R.drawable.ps7);
                break;
            case 7:
                menu[0]=new ItemMenu(R.drawable.ps8);
                break;
            default: // cualquier otro caso
                menu[0]=new ItemMenu(R.drawable.ps10);
                break;
        }
        return menu;
    }*/
    private class AdaptadorItemNegocio extends ArrayAdapter<String> {

        // UTILIZAR EL XML de la lista de menus y precios ....
        private final Activity context;
        private final String[] idString;
        private final ItemMenu[] idImagenesItem;

        public AdaptadorItemNegocio(Activity context,String[] idString, ItemMenu[] idImagenesItem) {
            super(context,R.layout.listview_menu_precios,idString);
            this.context=context;
            this.idString=idString;
            this.idImagenesItem=idImagenesItem;
        }

        public View getView(int posicion,View view, ViewGroup parent){

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.listview_menu_precios,null,true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon_menus_precios);
            imageView.setImageResource(idImagenesItem[posicion].getIdImagen());

            return rowView;
        }
    }



    public class ItemMenu{

        public int idImagen;

        public ItemMenu(){
            ////////////
        }

        public ItemMenu(int idImagen){
            this.idImagen=idImagen;
        }

        public int getIdImagen() {
            return idImagen;
        }

        public void setIdImagen(int idImagen) {
            this.idImagen = idImagen;
        }



    }




}
