package com.coursera.sacbe.petagramaxelsegura;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.coursera.sacbe.petagramaxelsegura.adapter.PageAdapter;
import com.coursera.sacbe.petagramaxelsegura.fragment.PerfilFragment;
import com.coursera.sacbe.petagramaxelsegura.fragment.RecyclerViewFragment;

import java.util.ArrayList;

public class UsuarioInstagramFrom extends AppCompatActivity {

    private ViewPager viewPager;
    private String idInstagramFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_instagram);

        Bundle parametros = getIntent().getExtras();

        if(parametros != null){
            idInstagramFrom = parametros.getString(getResources().getString(R.string.pIdInstagramFrom));
            Log.d("Entro parametros: ", idInstagramFrom);
        }else{
            Log.d("NO Entro parametros: ", idInstagramFrom);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPagerUsrIns);

        Toolbar miActionBar = (Toolbar) findViewById(R.id.miActionBarUsrIns);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpViewPager();
    }

    private void  setUpViewPager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),agregarFragment()));
    }

    private ArrayList<Fragment> agregarFragment(){
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        RecyclerViewFragment frgUsuarioInstagram = new RecyclerViewFragment();

        Bundle args = new Bundle();
        args.putString(getResources().getString(R.string.pIdInstagramFrom), idInstagramFrom);
        frgUsuarioInstagram.setArguments(args);

        fragments.add(frgUsuarioInstagram);
        return fragments;
    }

}
