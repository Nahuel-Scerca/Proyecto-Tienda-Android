package com.example.proyecto_tienda_android.ui.detalleEnvio;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;
import com.example.proyecto_tienda_android.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleEnvioViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Pedido> pedido;

    public DetalleEnvioViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<Pedido> getPedido() {
        if(pedido==null){
            pedido=new MutableLiveData<>();
        }
        return pedido;
    }


    public void obtenerPedido(Bundle bundle){

        final Pedido pedidoBundle = (Pedido) bundle.getSerializable("pedido");
        Log.d("asignado",pedidoBundle.getAsingnado()+" ESTE VALOR");

        pedido.setValue(pedidoBundle);

    }

    public void actualizarEstado(Pedido pedidoPut, int idEstado){

        pedidoPut.setEstado(idEstado);
        pedidoPut.setAsingnado(1);

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        final Call<Pedido> estadoPut = ApiClient.getMyApiInterface().actualizarEstado(token,pedidoPut);

        estadoPut.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()) {

                    pedido.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Toast.makeText(context,"No se pudo actualizar el envio",Toast.LENGTH_LONG).show();
            }
        });

    }


}
