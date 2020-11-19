package com.example.proyecto_tienda_android.ui.ventas;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VentasViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pedido>> pedidos;
    private Context context;

    public VentasViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<List<Pedido>> getPedidos() {
        if(pedidos==null){
            pedidos = new MutableLiveData<>();
        }
        return pedidos;
    }

    public void obtenerPedidosVendidos(){

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        final Call<List<Pedido>> arrayPedidos = ApiClient.getMyApiInterface().obtenerPedidosVendidos(token);

        arrayPedidos.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {


                if(response.isSuccessful()) {

                    pedidos.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {

                Toast.makeText(context,"No se puedo obtener los inmuebles",Toast.LENGTH_LONG).show();
            }
        });

    }



}