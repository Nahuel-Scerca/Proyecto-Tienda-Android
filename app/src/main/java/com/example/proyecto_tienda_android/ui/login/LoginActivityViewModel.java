package com.example.proyecto_tienda_android.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyecto_tienda_android.MainActivity;
import com.example.proyecto_tienda_android.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> error;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<String> getError() {
        if(error == null){
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void autenticacion(final String u, String c){
        Call<String> datos = ApiClient.getMyApiInterface().obtenerToken(u,c);
        datos.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    //guardar el token
                    SharedPreferences sh= context.getSharedPreferences("datos",0);
                    SharedPreferences.Editor editor= sh.edit();
                    editor.putString("token","Bearer "+response.body());
                    editor.putString("email", u);
                    editor.commit();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("login",true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
                else {
                    error.setValue("Usuario/Contraseña Incorrecta");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.setValue("Error al conectar con el Servicio");
                Log.d("Login",t.getMessage());
            }
        });


    }

}
