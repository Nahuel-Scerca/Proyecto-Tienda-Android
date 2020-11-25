package com.example.proyecto_tienda_android.ui.estadisticas;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.Usuario;
import com.example.proyecto_tienda_android.modelo.UsuarioVentas;
import com.example.proyecto_tienda_android.request.ApiClient;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstadisticasViewModel extends AndroidViewModel {

    Context context;
    MutableLiveData<ArrayList<PieEntry>> datos;
    MutableLiveData<List<UsuarioVentas>> listUsuariosApi;
    MutableLiveData<UsuarioVentas> usuarioTop;


    public EstadisticasViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<UsuarioVentas> getUsuarioTop() {
        if(usuarioTop==null){
            usuarioTop= new MutableLiveData<>();
        }
        return usuarioTop;
    }

    public LiveData<ArrayList<PieEntry>> getDatos() {
        if (datos == null) {
            datos = new MutableLiveData<>();
        }
        return datos;
    }

    public LiveData<List<UsuarioVentas>> getListUsuariosApi() {
        if (listUsuariosApi == null) {
            listUsuariosApi = new MutableLiveData<>();
        }
        return listUsuariosApi;
    }

    public void obtenerUsuarios() {

        final SharedPreferences sh = context.getSharedPreferences("datos", 0);
        String token = sh.getString("token", "---");


        Call<List<UsuarioVentas>> listUsuarios = ApiClient.getMyApiInterface().obtenerVentasUsuarios(token);

        listUsuarios.enqueue(new Callback<List<UsuarioVentas>>() {
            @Override
            public void onResponse(Call<List<UsuarioVentas>> call, Response<List<UsuarioVentas>> response) {
                if (response.isSuccessful()) {
                    listUsuariosApi.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioVentas>> call, Throwable t) {
                Toast.makeText(context, "No se pudo obtener los usuarios y sus ventas", Toast.LENGTH_LONG).show();
            }
        });
    }



    public void cargarDatos(List<UsuarioVentas> listaU) {
        ArrayList<PieEntry> estadisticas = new ArrayList<>();


        for (UsuarioVentas userVentas : listaU) {

            estadisticas.add(new PieEntry(userVentas.getCantidadVentas(), userVentas.getUsuario().getNombre() + " " + userVentas.getUsuario().getApellido()));
        }

        datos.postValue(estadisticas);
    }

    public void traerUsuarioTop(List<UsuarioVentas> listaU){
        UsuarioVentas userFinal= new UsuarioVentas();

        int ventasMaximas=0;
        String venderdor=" ";
        String foto=" ";

        for (UsuarioVentas users : listaU) {
            if(users.getCantidadVentas()>ventasMaximas){
                ventasMaximas=users.getCantidadVentas();
                userFinal=users;
            }
        }


        usuarioTop.postValue(userFinal);
    }
}
