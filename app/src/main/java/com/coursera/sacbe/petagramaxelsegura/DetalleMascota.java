package com.coursera.sacbe.petagramaxelsegura;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sacbe on 03/09/2016.
 */
public class DetalleMascota extends AppCompatActivity {

    private ImageView imgvFoto;
    private TextView tvNombre;
    private TextView tvRaza;
    private TextView tvRaiting;
    private Toolbar miActionBar;
    private TextView tituloPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mascota);
        Bundle parametros = getIntent().getExtras();
        String nombre = parametros.getString(getResources().getString(R.string.pNombre));
        String raza = parametros.getString(getResources().getString(R.string.pRaza));
        String raiting = parametros.getString(getResources().getString(R.string.pRating));
        int foto = parametros.getInt(getResources().getString(R.string.pFoto));

        miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //tituloPantalla = (TextView) findViewById(R.id.tvTitulo);
        //tituloPantalla.setText(getResources().getString(R.string.titulo_detalle));

        imgvFoto = (ImageView) findViewById(R.id.imgvFoto) ;
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvRaza = (TextView) findViewById(R.id.tvRaza);
        tvRaiting = (TextView) findViewById(R.id.tvRating);

        imgvFoto.setImageResource(foto);
        tvNombre.setText(nombre);
        tvRaza.setText(raza);
        tvRaiting.setText(raiting);
    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event){

        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(DetalleMascota.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

}