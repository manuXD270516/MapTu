package com.example.usuario.maptu.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.usuario.maptu.AcercaDe.AcercaDe;
import com.example.usuario.maptu.Global;
import com.example.usuario.maptu.Inicio.Index;
import com.example.usuario.maptu.R;
import com.example.usuario.maptu.SQLlite.SQLControlador;
import com.example.usuario.maptu.Webservices.Constantes;
import com.example.usuario.maptu.Webservices.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity /*implements LoaderCallbacks<Cursor> */{

    SQLControlador dbconeccion;
    boolean valido;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView txtUser;
    private EditText txtPassword;
    private View mProgressView;
    private View mLoginFormView;
    TextView labelRegistrarse;
    private Switch swSave;
    private ProgressDialog progress;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // abrir conexion con la base de datos local
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();

        SharedPreferences preferences=getSharedPreferences("preferencias_maptu", Context.MODE_PRIVATE);
        boolean iniciarApp=preferences.getBoolean("save_sesion",false);
        if (iniciarApp==true){ // se guardo el inicio de sesion , iniciar directamente con el index.java
            Global.intentInicio="index";
            startActivityForResult(new Intent(LoginActivity.this,Index.class),1);
            overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
            finish();
        } else {
            Global.intentInicio="login";
        }
        // Set up the login form.
        txtUser = (AutoCompleteTextView) findViewById(R.id.txtUser);
        //populateAutoComplete();
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        txtPassword = (EditText) findViewById(R.id.txtPassword);
        /*txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //loggin();
                    return true;
                }
                return false;
            }
        });*/
        swSave=(Switch) findViewById(R.id.swSaveUser);
        //SharedPreferences preferences=getSharedPreferences("preferencias_maptu", Context.MODE_PRIVATE);
        txtUser.setText(preferences.getString("user_login",""));
        txtPassword.setText(preferences.getString("password_login",""));
        swSave.setChecked(preferences.getBoolean("save_login",false));
        mediaPlayer=MediaPlayer.create(this,R.raw.videorecord);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                //loggin();
                validarUsuario(txtUser.getText().toString().trim(),txtPassword.getText().toString().trim());
                //loggin();
            }
        });
        labelRegistrarse=(TextView) findViewById(R.id.link_signup);
        labelRegistrarse.setText(Html.fromHtml("<u>"+labelRegistrarse.getText().toString()+"</u>"));

    }

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            //finish();
            super.onBackPressed();
            //finishAffinity();
            //return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
        mediaPlayer.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0 || resultCode==1 ) {
            //super.finish();
            //finishAffinity();
            super.onBackPressed();
        }
    }

    public void actividadRegistrarCuenta(View V){
        mediaPlayer.start();
        Intent x=new Intent(LoginActivity.this,Registrar_Usuario.class);
        startActivity(x);
        overridePendingTransition(R.anim.left_out,R.anim.left_in);
        //startActivityForResult(x,0);
    }
    /*private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }*/

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(txtUser, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    public void irAcercaEmpresa(View v){
        mediaPlayer.start();
        Intent x=new Intent(LoginActivity.this,AcercaDe.class);
        startActivity(x);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
    }
    private void loggin() {
        progress=new ProgressDialog(LoginActivity.this);
        progress.setCancelable(false);
        progress.setTitle("Login MapTu");
        progress.setMessage("Autenticando Usuario Espere porfavor");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgress(0);
        progress.setIcon(R.drawable.ic_people_black_24dp);
        progress.show();
        final int totalProgressTime = 20;
        progress.setMax(totalProgressTime);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (valido==true){//validarUsuario(txtUser.getText().toString().trim(),txtPassword.getText().toString().trim())){
                            Intent x=new Intent(LoginActivity.this,Index.class);
                            x.putExtra("email_user",txtUser.getText().toString().trim());
                            //Intent x=new Intent(LoginActivity.this,MaptuPresentacion.class);
                            startActivityForResult(x,0);
                            overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
                        } else {
                            Toast.makeText(getApplicationContext(),"Usuario Incorrecto... Intente nuevamente porfavor",Toast.LENGTH_LONG).show();
                            txtPassword.setText(null);
                            txtUser.setText(null);
                        }
                        progress.dismiss();
                    }
                },3000);
    }

    public void validarUsuario(final String email, final String pass){
        /*HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("accion","1");
        map.put("email", email);
        map.put("pass", pass);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d("TAG", jobject.toString());*/
        // Actualizar datos en el servidor
        VolleySingleton.getInstance(this).addToRequestQueue(
               new StringRequest(
                       Request.Method.GET,
                       Constantes.HTTP_USER+"?accion=1&email="+email+"&pass="+pass,
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                                procesarRespuestaLogin(response);
                           }
                       },
                       new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                           }
                       }
               ){

               }
        );
        /*if (valido==true){
            // el usuario si es el correcto
            SharedPreferences preferences=getSharedPreferences("preferencias_maptu",Context.MODE_PRIVATE);
            SharedPreferences.Editor editorPreferencias =preferences.edit();
            if (swSave.isChecked()){
                editorPreferencias.putString("user_login",txtUser.getText().toString());
                editorPreferencias.putString("password_login",txtPassword.getText().toString());
                editorPreferencias.putBoolean("save_login",true);
            } else {
                //Global.auxUserIni=txtUser.getText().toString();
                editorPreferencias.putString("user_login","");
                editorPreferencias.putString("password_login","");
                editorPreferencias.putBoolean("save_login",false);
            }
            editorPreferencias.putString("user_fix",txtUser.getText().toString());
            editorPreferencias.commit();
            /*Intent x=new Intent(LoginActivity.this,Index.class);
            //Intent x=new Intent(LoginActivity.this,MaptuPresentacion.class);
            startActivityForResult(x,0);*
            return true;

        } else {

            Toast.makeText(getApplicationContext(),"Usuario Incorrecto... Intente nuevamente porfavor",Toast.LENGTH_LONG).show();
            txtPassword.setText(null);
            txtUser.setText(null);
            return false;
        }*/
    }

    private void procesarRespuestaLogin(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String estado=jsonObject.getString("estado");
            if (estado.equals("1")){ // usuario valido
                valido=true;
                SharedPreferences preferences=getSharedPreferences("preferencias_maptu",Context.MODE_PRIVATE);
                SharedPreferences.Editor editorPreferencias =preferences.edit();
                if (swSave.isChecked()){
                    editorPreferencias.putString("user_login",txtUser.getText().toString());
                    editorPreferencias.putString("password_login",txtPassword.getText().toString());
                    editorPreferencias.putBoolean("save_login",true);
                } else {
                    //Global.auxUserIni=txtUser.getText().toString();
                    editorPreferencias.putString("user_login","");
                    editorPreferencias.putString("password_login","");
                    editorPreferencias.putBoolean("save_login",false);
                }
                editorPreferencias.putString("user_fix",txtUser.getText().toString());
                editorPreferencias.commit();
            } else { // usuario invalido
                valido=false;
            }
            loggin();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();// e.printStackTrace();
            valido=false;
        }
    }

    /*private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic

        //return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }*/

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }*/

    /*@Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }*/

    /*@Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        txtUser.setAdapter(adapter);
    }*/


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    /*public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }*/
}

