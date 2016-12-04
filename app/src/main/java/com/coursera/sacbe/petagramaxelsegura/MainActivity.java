package com.coursera.sacbe.petagramaxelsegura;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.adapter.PageAdapter;
import com.coursera.sacbe.petagramaxelsegura.fragment.PerfilFragment;
import com.coursera.sacbe.petagramaxelsegura.fragment.RecyclerViewFragment;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.adapter.RestApiAdapter;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.UsuarioInstagram;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int viewPage = 0;
    private String idInstagramFrom;
    //private String idUsuarioInstagram;
    private String nombrePerfilActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle parametros = getIntent().getExtras();

        if(parametros != null){
            viewPage = parametros.getInt(getResources().getString(R.string.pViewPage));
            Log.d("Entro parametros: ", Integer.toString(viewPage));
        }else{
            Log.d("NO Entro parametros: ", Integer.toString(viewPage));
        }

        toolbar = (Toolbar) findViewById(R.id.miActionBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        Toolbar miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionBar);

        setUpViewPager();

        if(toolbar != null){

            setSupportActionBar(toolbar);
        }

        estableceCuentaInstagramDefault();

    }

    public void estableceCuentaInstagramDefault(){

        SharedPreferences miPreferenciaCompartida = getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        String nombrePerfilActual = miPreferenciaCompartida.getString(getResources().getString(R.string.pNombrePerfil), "");

        if(nombrePerfilActual==""){

            SharedPreferences.Editor editor = miPreferenciaCompartida.edit();
            editor.putString(getResources().getString(R.string.pNombrePerfil), getResources().getString(R.string.cuentaInstagramDefault));
            editor.putString(getResources().getString(R.string.pIdInstagramFrom),getResources().getString(R.string.IdInstagramDefault));
            editor.commit();
        }

    }

    private void  setUpViewPager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),agregarFragment()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_pet_contacts);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pet_footprint);
        viewPager.setCurrentItem(viewPage);

    }

    private ArrayList<Fragment> agregarFragment(){
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new RecyclerViewFragment());
        fragments.add(new PerfilFragment());
        return fragments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_petagram, menu);
        return true;
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_petagram, menu);
        return true;
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mAbout:
                Toast.makeText(this, getResources().getString(R.string.item_about), Toast.LENGTH_SHORT).show();
                Intent iAbout = new Intent(this, AboutActivity.class);
                startActivity(iAbout);
                break;
            case R.id.mContact:
                Toast.makeText(this, getResources().getString(R.string.item_contact), Toast.LENGTH_SHORT).show();
                Intent iContact = new Intent(this, ContactActivity.class);
                startActivity(iContact);
                break;
            case R.id.mConfig:
                Toast.makeText(this, getResources().getString(R.string.item_config), Toast.LENGTH_SHORT).show();
                Intent iConfig = new Intent(this, ConfigurarCuenta.class);
                startActivity(iConfig);
                break;
            case R.id.mRecibirNotificaciones:
                Toast.makeText(this, getResources().getString(R.string.item_recibir_notificaciones), Toast.LENGTH_SHORT).show();
                String idDispositivo = FirebaseInstanceId.getInstance().getToken();
                obtenerCuentaConfigurada();

                enviarTokenRegistro(idDispositivo,nombrePerfilActual, idInstagramFrom);
                //Intent iConfig = new Intent(this, ConfigurarCuenta.class);
                //startActivity(iConfig);
                Toast.makeText(this, R.string.usuario_registrado + " " + nombrePerfilActual, Toast.LENGTH_SHORT).show();
                break;
            case R.id.mFavoritos:
                Toast.makeText(this, getResources().getString(R.string.item_fav), Toast.LENGTH_SHORT).show();
                Intent iFav = new Intent(this, MascotaFavorita.class);
                startActivity(iFav);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void enviarTokenRegistro(String idDispositivo, String idUsuarioInstagram, String idInstagramFrom){

        Log.d("ID_DISPOSITIVO", idDispositivo);
        Log.d("ID_USUARIO_INSTAGRAM", idUsuarioInstagram);

        RestApiAdapter restApiAdapter = new RestApiAdapter();

        IEndPointsApi endpoints = restApiAdapter.establecerConexionRestApiHeroku();
        Call<UsuarioInstagram> usuarioResponseCall = endpoints.registrarUsuarioID(idDispositivo,idUsuarioInstagram, idInstagramFrom);

        usuarioResponseCall.enqueue(new Callback<UsuarioInstagram>() {
            @Override
            public void onResponse(Call<UsuarioInstagram> call, Response<UsuarioInstagram> response) {
                UsuarioInstagram usuarioResponse = response.body();
                Log.d("ID", usuarioResponse.getId());
                Log.d("ID_DISPOSITIVO_FROM", usuarioResponse.getIdDispositivoFrom());
                Log.d("ID_USUARIO_INSTAGRAM", usuarioResponse.getIdUsuarioInstagramFrom());
            }

            @Override
            public void onFailure(Call<UsuarioInstagram> call, Throwable t) {

            }
        });


    }

    private void obtenerCuentaConfigurada(){

        SharedPreferences miPreferenciaCompartida = getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        nombrePerfilActual = miPreferenciaCompartida.getString(getResources().getString(R.string.pNombrePerfil), "");
        idInstagramFrom = miPreferenciaCompartida.getString(getResources().getString(R.string.pIdInstagramFrom), "");

    }

}
