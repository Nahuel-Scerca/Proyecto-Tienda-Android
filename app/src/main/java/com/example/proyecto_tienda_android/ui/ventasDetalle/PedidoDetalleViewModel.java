package com.example.proyecto_tienda_android.ui.ventasDetalle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;
import com.example.proyecto_tienda_android.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoDetalleViewModel extends AndroidViewModel {

    private MutableLiveData<List<PedidoProducto>> lineasPedidoProducto;
    private MutableLiveData<Pedido> pedido;
    private Context context;
    private MutableLiveData<Integer> botonAdquirido;
    private MutableLiveData<Integer> botonDetalleEnvio;
    private MutableLiveData<String> mensajeAdquirido;

    public PedidoDetalleViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<String> getMensajeAdquirido() {
        if(mensajeAdquirido==null){
            mensajeAdquirido= new MutableLiveData<>();
        }
        return mensajeAdquirido;
    }

    public LiveData<Integer> getBotonDetalleEnvio() {
        if(botonDetalleEnvio==null){
            botonDetalleEnvio= new MutableLiveData<>();
        }

        return botonDetalleEnvio;
    }

    public LiveData<Integer> getBotonAdquirido() {
        if(botonAdquirido==null){
            botonAdquirido= new MutableLiveData<>();
        }

        return botonAdquirido;
    }

    public LiveData<Pedido> getPedido() {
        if(pedido==null){
            pedido= new MutableLiveData<>();
        }
        return pedido;
    }

    public LiveData<List<PedidoProducto>> getLineasPedidoProducto() {
        if(lineasPedidoProducto==null){
            lineasPedidoProducto= new MutableLiveData<>();
        }
        return lineasPedidoProducto;
    }

    public void obtenerLineasDePedido(Bundle bundle){
        final Pedido pedidoBundle = (Pedido) bundle.getSerializable("pedido");

        //seteamos si vamos desde la vista adquirir para que muestre los botones
        botonAdquirido(bundle);
        botonDetalle(bundle);

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        Call<List<PedidoProducto>> arrayLineasPedido = ApiClient.getMyApiInterface().obtenerLineasPedido(token,pedidoBundle.getId());
        arrayLineasPedido.enqueue(new Callback<List<PedidoProducto>>() {
            @Override
            public void onResponse(Call<List<PedidoProducto>> call, Response<List<PedidoProducto>> response) {


                if(response.isSuccessful()) {

                    lineasPedidoProducto.postValue(response.body());
                    pedido.postValue(pedidoBundle);
                }
            }

            @Override
            public void onFailure(Call<List<PedidoProducto>> call, Throwable t) {

                Toast.makeText(context,"No se puedo obtener las lineas",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void adquirirPedido(Bundle bundle){
        final Pedido pedidoBundle = (Pedido) bundle.getSerializable("pedido");

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        Call<String> okAdquirirPedido = ApiClient.getMyApiInterface().adquirirPedido(token,pedidoBundle.getId());
        okAdquirirPedido.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {

                    mensajeAdquirido.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context,"No se puedo adquirir el Pedido",Toast.LENGTH_LONG).show();
            }
        });
    }




    public void botonAdquirido(Bundle bundle){

         Boolean AdquiridoBundle = bundle.getBoolean("botonAdquirido");


        if(AdquiridoBundle){
            botonAdquirido.setValue(0);
        }
         else{

             botonAdquirido.setValue(8);
         }
    }
    public void botonDetalle(Bundle bundle){
        Boolean DetalleEnvioBundle = bundle.getBoolean("botonDetalleEnvio");

        if(DetalleEnvioBundle){
            botonDetalleEnvio.setValue(0);

        }
        else{
            botonDetalleEnvio.setValue(8);
        }
    }


}