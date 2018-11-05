package com.example.usuario.maptu.Login;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.usuario.maptu.R;
import com.example.usuario.maptu.SQLlite.SQLControlador;
import com.example.usuario.maptu.Webservices.Constantes;
import com.example.usuario.maptu.Webservices.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Registrar_Usuario extends AppCompatActivity {

    //Spinner spnDiaFN,spnMesFN,spnYearFN;
    int dia,mes,ano;
    private EditText txtEmail,txtPassw,txtPasswRep,txtNombres,txtApellidos,txtTelefono;
    private Button btnRegistrar;
    private ProgressDialog progress;
    RadioButton rbtSexoM,rbtSexoF;
    private TextView txtFecha;
    SQLControlador dbconeccion;
    String fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar__usuario);

        // CARGAR LOS SPINNERS CON LAS FECHAS DE NACIMIENTO
        /*spnDiaFN=(Spinner) findViewById(R.id.spnDia);
        spnMesFN=(Spinner) findViewById(R.id.spnMes);
        spnYearFN=(Spinner) findViewById(R.id.spnAño);*/

        txtNombres=(EditText)findViewById(R.id.txtNombres);
        txtApellidos=(EditText)findViewById(R.id.txtApellidos);
        txtTelefono=(EditText)findViewById(R.id.txtTelefono);
        rbtSexoM=(RadioButton)findViewById(R.id.rbtnMasculino);
        rbtSexoF=(RadioButton)findViewById(R.id.rbtnFemenino);

        txtEmail=(EditText)findViewById(R.id.txtEmailReg);
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if (!hasFocus){
                    emailValido(txtEmail.getText().toString().trim());
                }
            }
            private void emailValido(String email) {
                if (!email.contains("@") || !email.contains(".")){
                    mensaje("Validacion Email","Email no tiene el formato correcto");
                } else {
                    isEmailUnico(email);
                }
            }
        });


        txtPassw=(EditText)findViewById(R.id.txtPasswordReg);
        txtPasswRep=(EditText)findViewById(R.id.txtPasswordRegRep);
        txtFecha=(TextView)findViewById(R.id.txtFecha);
        btnRegistrar=(Button) findViewById(R.id.btnRegistrarUser);
        //cargarOpcionesFechaNacimiento();

        // abrir conexion con la base de datos local
        /*dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();*/

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarCuenta();
            }
        });
    }


    public void getfecha(View view){
        Calendar c=Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.DATE);
        //c.setWeekDate(2017,2,Calendar.MONDAY);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(dayOfMonth<10 && monthOfYear<10){
                    fecha=year+"-0"+(monthOfYear+1)+"-0"+dayOfMonth;
                    //fecha="0"+dayOfMonth+"-0"+(monthOfYear+1)+"-"+year;
                }else if(dayOfMonth<10){
                    fecha=year+"-"+(monthOfYear+1)+"-0"+dayOfMonth;
                    //fecha="0"+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                }else if( monthOfYear<10){
                    fecha=year+"-0"+(monthOfYear+1)+"-"+dayOfMonth;
                    //fecha=dayOfMonth+"/0"+(monthOfYear+1)+"/"+year;
                }else{
                    fecha=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                    //fecha=dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                }
                txtFecha.setText(fecha);
              /*  if (year==2017&&monthOfYear+1==8&&dayOfMonth==17) {
                    Toast.makeText(CrearCita.this, "Aniversario Roni", Toast.LENGTH_SHORT).show();
                }*/
            }
        }
                ,dia,mes,ano);
        datePickerDialog.show();
        txtFecha.setEnabled(true);
    }

    /*private void cargarOpcionesFechaNacimiento(){
        LinkedList<String> lista=new LinkedList<>();
        for (int i=1;i<=31;i++){
            lista.add((i<10?"0"+ String.valueOf(i):String.valueOf(i)));
        }
        ArrayAdapter spinnerAdapter=new ArrayAdapter(this,R.layout.spinner_item_maptu,lista);
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDiaFN.setAdapter(spinnerAdapter);

        lista=new LinkedList<>();
        //LinkedList<String> lista2=new LinkedList<>();
        String[] meses={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        for (String mesActual:meses){
            lista.add(mesActual);
        }
        ArrayAdapter spinnerAdapter2=new ArrayAdapter(this,R.layout.spinner_item_maptu,lista);
        //spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMesFN.setAdapter(spinnerAdapter2);

        lista=new LinkedList<>();
        for (int year=1990;year<=2017;year++){
            lista.add(String.valueOf(year));
        }
        ArrayAdapter spinnerAdapter3=new ArrayAdapter(this,R.layout.spinner_item_maptu,lista);
        //spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYearFN.setAdapter(spinnerAdapter3);

    }*/

    private void registrarCuenta(){

        final String email=txtEmail.getText().toString().trim();
        final String passw=txtPassw.getText().toString().trim();
        final String passwRep=txtPasswRep.getText().toString().trim();
        final String nombres=txtNombres.getText().toString().trim();
        final String apellidos=txtApellidos.getText().toString().trim();
        final String telefono=txtTelefono.getText().toString().trim();
        final char sexo; // por default masculino
        if (rbtSexoM.isChecked()){
            sexo='M';
        } else if (rbtSexoF.isChecked()){
            sexo='F';
        } else {
            sexo='N';
            //Toast.makeText(getApplicationContext(),"PORFAVOR PRIMERO SELECCIONE SU SEXO PARA REGISTRAR SU CUENTA",Toast.LENGTH_SHORT).show();
            //return;
        }
        //String mes=spnMesFN.getSelectedItemPosition()+1<10?"0"+String.valueOf(spnMesFN.getSelectedItemPosition()+1):String.valueOf(spnMesFN.getSelectedItemPosition()+1);
        //final String fechaNac=spnYearFN.getSelectedItem().toString()+"-"+mes+"-"+spnDiaFN.getSelectedItem().toString();
        if (datosValidos(nombres,apellidos,email,passw,passwRep,telefono,sexo)){
            progress=new ProgressDialog(Registrar_Usuario.this);
            progress.setTitle("Registro de Cuenta MapTu");
            progress.setMessage("Registrando Nuevo Usuario...");
            progress.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
            progress.setProgress(0);
            progress.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(50);
                            validarRegistroUsuario(nombres,apellidos,sexo,email,passw,fecha,telefono);
                            progress.dismiss();
                        }
                    },3000);
        }

    }

    private void mensaje(String... parametros){
        AlertDialog.Builder alertEnProgreso = new AlertDialog.Builder(this);
        alertEnProgreso.setTitle(parametros[0]);
        alertEnProgreso.setMessage(parametros[1]);
        alertEnProgreso.setCancelable(true);
        alertEnProgreso.setIcon(R.mipmap.ic_person_add_black_24dp);
        alertEnProgreso.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //finish();
                    }
                });
        AlertDialog alert1 = alertEnProgreso.create();
        alert1.show();
    }

    private boolean datosValidos(String nombres, String apellidos,String email,String pass,String passRep, String telefono, char sexo) {
        String[] parametrosMsg=new String[2];
        boolean b=false;
        parametrosMsg[0]="Validacion de Datos Personales";
        if (nombres.isEmpty()){
            parametrosMsg[1]="Escriba su(s) nombre(s) porfavor";
        } else if (apellidos.isEmpty()){
            parametrosMsg[1]="Escriba su(s) apellido(s) porfavor";
        } else if (sexo=='N'){
            parametrosMsg[1]="Defina su sexo porfavor";
        /*} else if (email.isEmpty() || !email.contains("@") || !email.contains(".") ){
            parametrosMsg[1]="email invalido, ingrese uno que sea correcto porfavor";
        } else if (!isEmailUnico(email)){
            parametrosMsg[1]="El email ya esta siendo utilizado, prueba con otro correo electronico";*/
        } else if (!pass.equals(passRep)){
            parametrosMsg[1]="Contraseña invalida, verifica que los dos campos para escribir la contraseña coincidad";
        } else {
            return true;
        }
        mensaje(parametrosMsg);
        return b;
    }

    private void isEmailUnico(String email) {
        final ProgressDialog loading = ProgressDialog.show(this,"Verificando Email", "Please wait...",false,false);
        VolleySingleton.getInstance(this).addToRequestQueue(
                new StringRequest(
                        Request.Method.GET,
                        Constantes.HTTP_USER + "?accion=3&email=" + email,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String validoEmail=jsonObject.getString("valido");
                                    if (validoEmail.equals("0")){
                                        mensaje("Validacion Email","Email ya registrado en MAPTU, ingrese otro email de registro");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                )
        );
    }

    private void validarRegistroUsuario(String nombres,String apellidos,char sexo,String email,String pass,String fecha,String telefono) {
        //dbconeccion.insertarDatos(email,pass);
        VolleySingleton.getInstance(this).addToRequestQueue(
                new StringRequest(
                        Request.Method.GET,
                        Constantes.HTTP_USER+"?accion=2&nombres="+nombres+"&apellidos="+apellidos+"&sexo="+sexo+"&email="+email+"&pass="+pass+"&fecha="+fecha+"&telefono="+telefono,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                procesarRespuestaRegistroUser(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                )
        );

    }

    private void procesarRespuestaRegistroUser(String response) {
        try{
            JSONObject jsonObject=new JSONObject(response);
            String estado=jsonObject.getString("estado");
            if (estado.equals("1")){ // usuario insertado
                AlertDialog.Builder alertEnProgreso = new AlertDialog.Builder(this);
                alertEnProgreso.setTitle("Registro MAPTU");
                alertEnProgreso.setMessage("Registro de Cuenta Satisfactorio, verifique su email de validacion");
                alertEnProgreso.setCancelable(true);
                alertEnProgreso.setIcon(R.mipmap.ic_person_add_black_24dp);
                alertEnProgreso.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
                AlertDialog alert1 = alertEnProgreso.create();
                alert1.show();
            } else {
                Toast.makeText(getApplicationContext(),"VERIFIQUE QUE TODOS LOS CAMPOS ESTEN CORRECTOS",Toast.LENGTH_SHORT).show();
            }
        }catch  (JSONException e) {
            e.printStackTrace();
        } finally{
            //
        }
    }
}
