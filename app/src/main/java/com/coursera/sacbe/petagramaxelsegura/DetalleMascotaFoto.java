package com.coursera.sacbe.petagramaxelsegura;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by axel on 30/09/2016.
 */
public class DetalleMascotaFoto extends AppCompatActivity {

    private ImageView imgFotoDetalle;
    private TextView tvLikesDetalle;
    private Toolbar miActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mascota_foto);
        Bundle parametros = getIntent().getExtras();
        //String nombre = parametros.getString(getResources().getString(R.string.pNombre));
        //String raza = parametros.getString(getResources().getString(R.string.pRaza));
        int likes = parametros.getInt(getResources().getString(R.string.pLikes));
        String urlFoto = parametros.getString(getResources().getString(R.string.pUrl));

        miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //tituloPantalla = (TextView) findViewById(R.id.tvTitulo);
        //tituloPantalla.setText(getResources().getString(R.string.titulo_detalle));


        tvLikesDetalle = (TextView) findViewById(R.id.tvLikesDetalle);
        tvLikesDetalle.setText(String.valueOf(likes));
        imgFotoDetalle = (ImageView) findViewById(R.id.imgFotoDetalle) ;
        Picasso.with(this).load(urlFoto).placeholder(R.drawable.unam_pumas).into(imgFotoDetalle);
    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event){

        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(DetalleMascotaFoto.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

}
