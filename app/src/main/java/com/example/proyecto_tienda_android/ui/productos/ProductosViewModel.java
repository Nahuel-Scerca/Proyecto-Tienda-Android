package com.example.proyecto_tienda_android.ui.productos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.Producto;
import com.example.proyecto_tienda_android.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosViewModel extends AndroidViewModel {

    private MutableLiveData<String> productoBuscar;
    private MutableLiveData<List<Producto>> productosList;
    private Context context;

    public ProductosViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<String> getProductoBuscar() {
        if(productoBuscar==null){
            productoBuscar=new MutableLiveData<>();
        }
        return productoBuscar;
    }

    public void setProductoBuscar(String nombre) {
        if(productoBuscar==null){
            productoBuscar=new MutableLiveData<>();
        }
        this.productoBuscar.setValue(nombre);
    }

    public LiveData<List<Producto>> getProductosList() {
        if(productosList==null){
            productosList=new MutableLiveData<>();
        }
        return productosList;
    }

    public void obtenerProductos(){

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        final Call<List<Producto>> arrayProductos = ApiClient.getMyApiInterface().obtenerProductos(token);

        arrayProductos.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                Log.d("entresucc","NOentrealSUCCES");
                if(response.isSuccessful()) {
                    Log.d("entresucc","entrealSUCCES");
                    productosList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.d("entresucc",t.getMessage());
                Toast.makeText(context,"No se puedo obtener los productos",Toast.LENGTH_LONG).show();
            }
        });

    }


    public void obtenerProductosBuscador(){

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        final Call<List<Producto>> arrayProductos = ApiClient.getMyApiInterface().obtenerProductosBuscador(token,productoBuscar.getValue());

        arrayProductos.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {


                if(response.isSuccessful()) {

                    productosList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

                Toast.makeText(context,"No se puedo obtener los productos",Toast.LENGTH_LONG).show();
            }
        });

    }
}