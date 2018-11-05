package com.example.usuario.maptu.Contacto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.usuario.maptu.R;

public class Contacto extends AppCompatActivity {

    Button btnContacto;
    private ProgressDialog progress;
    EditText txtTo,txtFor,txtSubject,txtMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        txtTo=(EditText)findViewById(R.id.txtDe);
        txtFor=(EditText)findViewById(R.id.txtPara);
        txtSubject=(EditText)findViewById(R.id.txtAsunto);
        txtMessage=(EditText)findViewById(R.id.txtMensaje);
        btnContacto=(Button)findViewById(R.id.btnEnviar);
        Bundle bundle=getIntent().getExtras();
        txtTo.setText(bundle.getString("user_email"));
        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=new ProgressDialog(Contacto.this);
                progress.setCancelable(false);
                progress.setTitle("Contacto Maptu");
                progress.setMessage("Validando Email, Espere porfavor...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setProgress(0);
                progress.show();
                final int totalProgressTime = 20;
                progress.setMax(totalProgressTime);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {

                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{txtFor.getText().toString()});
                                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{""});
                                //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{"email3@ekiketa.es"});
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, txtSubject.getText().toString());
                                //Recordad que la barra invertida más "n" es un salto de linea "n" así, escribiremos el email con varios saltos de linea.
                                String textoApp = txtMessage.getText().toString(); //"Envio un email desde mi App de androidnnnCreado gracias a:nhttp://ekiketa.es";
                                emailIntent.putExtra(Intent.EXTRA_TEXT, textoApp);
                                emailIntent.setType("message/rfc822");
                                //Damos la opción al usuario que elija desde que app enviamos el email.
                                startActivity(Intent.createChooser(emailIntent, "Selecciona aplicación para envio de correo a la empresa"));

                                //Toast.makeText(getApplicationContext(), "CORREO ELECTRONICO ENVIADO CON EXITO, GRACIAS POR CONTACTARNOS", Toast.LENGTH_SHORT).show();
                                overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
                                progress.dismiss(); finish();
                            }
                        },3000);
            }

        });
    }
}
