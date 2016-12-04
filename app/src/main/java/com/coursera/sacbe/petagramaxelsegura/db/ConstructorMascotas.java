package com.coursera.sacbe.petagramaxelsegura.db;

import android.content.ContentValues;
import android.content.Context;

import com.coursera.sacbe.petagramaxelsegura.R;
import com.coursera.sacbe.petagramaxelsegura.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by Sacbe on 18/09/2016.
 */
public class ConstructorMascotas {
    private Context context;
    private static final int LIKE = 1;

    public ConstructorMascotas(Context context) {
        this.context = context;
    }

    public ArrayList<Mascota> obtenerDatos() {
        BaseDatos db = new BaseDatos(context);
        if(!db.tablaBaseDatosLlena(ConstantesBaseDatos.TABLE_MASCOTA))
            insertarMascotas(db);

        return db.obtenerTodosLasMascotas();
    }

    public ArrayList<Mascota> obtenerMascotasFavoritas() {
        BaseDatos db = new BaseDatos(context);

        return db.obtenerMascotasFavoritas();
    }

    public void insertarMascotas(BaseDatos db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Miky");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Husky");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.husky);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Terry");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Boston Terrier");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.bostonterrier);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Toth");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Braco");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.braco);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Oso");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Chow Chow");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.chowchow);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Seth");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Bull Terrier");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.bullterrier);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Lasha");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Foxhound");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.foxhound);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Anubis");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Golden Retriever");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.goldenretriever);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Boby");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Pastor Australiano");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.pastoraustraliano);
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Pelos");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_RAZA, "Crestado");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.perrocrestado);
        db.insertarMascota(contentValues);

    }

    public void darLikeMascota(Mascota mascota) {
        BaseDatos db = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_LIKES_MASCOTA_ID_MASCOTA, mascota.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_LIKES_MASCOTA_NUMERO_LIKES, LIKE);
        db.insertarLikeMascota(contentValues);
        db.close();
    }

    public int obtenerLikesMascota(Mascota mascota){
        BaseDatos db = new BaseDatos(context);
        return db.obtenerLikesMascota(mascota);
    }
}
