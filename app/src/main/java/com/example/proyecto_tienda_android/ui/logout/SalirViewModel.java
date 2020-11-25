package com.example.proyecto_tienda_android.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SalirViewModel extends AndroidViewModel {

    private Context context;
    public SalirViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }
    // TODO: Implement the ViewModel

    public void logout(){
        SharedPreferences sh= context.getSharedPreferences("datos",0);
        SharedPreferences.Editor editor= sh.edit();
        editor.putString("token","----");
        editor.putInt("id",-1);
        editor.putString("nombre","--");
        editor.putString("apellido","--");
        editor.putString("email","--");
        editor.putString("avatar","--");
        editor.putString("rol","--");
        editor.putString("rolNombre","--");
        editor.commit();

        String t=sh.getString("token","---");

        Log.d("salir", t);
    }
}