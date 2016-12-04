package com.coursera.sacbe.petagramaxelsegura;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.mail.SendMail;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.adapter.RestApiAdapter;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurarCuenta extends AppCompatActivity {

    private TextInputEditText ietNombrePerfil;
    private TextInputLayout tilNombrePerfil;
    private Button btnGuardar;
    private String userId;
    private String nombrePerfil;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);
        this.context = this.getBaseContext();
        Toolbar miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilNombrePerfil = (TextInputLayout) findViewById(R.id.tilNombrePerfil);
        ietNombrePerfil = (TextInputEditText) findViewById(R.id.ietNombrePerfil);
        ietNombrePerfil.addTextChangedListener(new MyTextWatcher(ietNombrePerfil));
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        mostrarPreferencia();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFormulario();
            }
        });

    }

    private void submitFormulario() {
        if (!validaNombrePerfil()) {
            return;
        }

        nombrePerfil = ietNombrePerfil.getText().toString();

        //Buscamos el id de Instagram con el nombre
        obtenerUserId(nombrePerfil);


    }

    public void mostrarPreferencia(){

        SharedPreferences miPreferenciaCompartida = getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        String nombrePerfilActual = miPreferenciaCompartida.getString(getResources().getString(R.string.pNombrePerfil), "");
        TextView tvPreferenciaCompartida = (TextView) findViewById(R.id.tvCuentaPerfil);
        tvPreferenciaCompartida.setText(nombrePerfilActual);

    }

    private boolean validaNombrePerfil() {
        if (ietNombrePerfil.getText().toString().trim().isEmpty()) {
            tilNombrePerfil.setError(getString(R.string.err_msg_nombrePerfil));
            requestFocus(ietNombrePerfil);
            return false;
        } else {
            tilNombrePerfil.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.ietNombreCompleto:
                    validaNombrePerfil();
                    break;
            }
        }
    }

    public void obtenerUserId(String userName) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonUsuarioInfo = restApiAdapter.construyeGsonDeserializadorUserInformation();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonUsuarioInfo);


        //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

        Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getUserByName(userName);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                if(mascotaResponse.getUserInformation().getId() != null) {
                    userId = mascotaResponse.getUserInformation().getId();
                    SharedPreferences miPreferenciaCompartida = getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = miPreferenciaCompartida.edit();
                    editor.putString(getResources().getString(R.string.pNombrePerfil), nombrePerfil);
                    editor.putString(getResources().getString(R.string.pIdInstagramFrom), userId);

                    editor.commit();

                    Toast.makeText(ConfigurarCuenta.this, "Se ha guardado la cuenta de Instagram", Toast.LENGTH_SHORT).show();
                    ietNombrePerfil.setText("");

                    mostrarPreferencia();
                }
                else{

                    Toast.makeText(context, "El usuario de instagram no existe, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Algo pasó en la conexión o el usuario de instagram no existe, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e("Falló la conexión" , t.toString());
            }
        });
    }

}
