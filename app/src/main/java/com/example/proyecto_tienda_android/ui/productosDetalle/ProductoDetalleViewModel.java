package com.example.proyecto_tienda_android.ui.productosDetalle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.mbms.FileInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.Producto;
import com.example.proyecto_tienda_android.request.ApiClient;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoDetalleViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Producto> producto;
    private MutableLiveData<Integer> btnsCrear;
    private MutableLiveData<Integer> btnsModificar;

    public ProductoDetalleViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<Producto> getProducto() {
        if(producto== null){
            producto= new MutableLiveData<>();
        }
        return producto;
    }

    public LiveData<Integer> getBtnsCrear() {
        if(btnsCrear==null){
            btnsCrear=new MutableLiveData<>();
        }
        return btnsCrear;
    }

    public LiveData<Integer> getBtnsModificar() {
        if(btnsModificar==null){
            btnsModificar=new MutableLiveData<>();
        }
        return btnsModificar;
    }

    public void obtenerProducto(Bundle bundle){

        final Producto productoBundle = (Producto) bundle.getSerializable("producto");

        //verificamos que botones habilidar y comunicamos al fragment
        btnsCrear(bundle);
        btnsModificar(bundle);

        if(productoBundle!=null){

            final SharedPreferences sh= context.getSharedPreferences("datos",0);
            String token=sh.getString("token","---");


            final Call<Producto> productoApi = ApiClient.getMyApiInterface().obtenerProductoUnico(token,productoBundle.getId());

            productoApi.enqueue(new Callback<Producto>() {
                @Override
                public void onResponse(Call<Producto> call, Response<Producto> response) {
                    if(response.isSuccessful()) {
                        producto.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Producto> call, Throwable t) {
                    Toast.makeText(context,"No se puedo obtener el producto",Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    public void postProducto(Producto productoPost , final String fullPhotoUri){

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        //EN visual tenia un metodo que si int categoria = 1  -> categoria nomobre = Calzado . Lo simule aqui
      /*  switch (productoPost.getCategoria()){
            case 1: productoPost.setCategoriaNombre("Calzado");
                break;
            case 2: productoPost.setCategoriaNombre("Remera");
                break;
            case 3: productoPost.setCategoriaNombre("Pantalon");
                break;
            case 4: productoPost.setCategoriaNombre("Pollera");
                break;
            default:productoPost.setCategoriaNombre("Generica");
        }*/

        Call<Producto> productoPostCall = ApiClient.getMyApiInterface().postProducto(token,productoPost);

        productoPostCall.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()) {
                    producto.postValue(response.body());
                    Log.d("subirProducto","Pude Subir Producto nre");
                    subirFoto(fullPhotoUri,response.body());

                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(context,"No se pudo crear el producto",Toast.LENGTH_LONG).show();
            }
        });

    }

    //SUBIR FOTO
    public void subirFoto(final String pathFoto, final Producto prodPut) {
        final String pathfinal="producto" + prodPut.getId();

        final SharedPreferences sh = context.getSharedPreferences("datos", 0);
        String token = sh.getString("token", "---");

        File archivoFoto = new File(pathFoto);
        RequestBody filePart = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                archivoFoto);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", "producto" + prodPut.getId()+".jpg", filePart);


        try {
            Call<ResponseBody> callFoto = ApiClient.getMyApiInterface().postFotoProducto(token, file);

            callFoto.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(context, "Imagen cargada", Toast.LENGTH_LONG).show();

                    if(response.isSuccessful()){
                        Log.d("subirProducto","Pude Subir la foto");
                        prodPut.setFoto("http://192.168.0.14:45455/Uploads/"+pathfinal+".jpg");
                        putProducto(prodPut);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("subirProducto",t.getMessage());
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception ex){
            Log.d("ErrorSubir",ex.getMessage());
        }

    }


    public void putProducto(Producto productoObservado){

        final SharedPreferences sh= context.getSharedPreferences("datos",0);
        String token=sh.getString("token","---");


        final Call<Producto> productoApi = ApiClient.getMyApiInterface().actualizarProducto(token,productoObservado);

        productoApi.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()) {
                    Log.d("subirProducto", "Putie todooooooo");
                    producto.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(context,"No se pudo modificar el producto",Toast.LENGTH_LONG).show();
            }
        });

    }


    public void btnsModificar(Bundle bundle){
        Boolean Modificar = bundle.getBoolean("btnsModificar");

        if(Modificar){
            btnsModificar.postValue(0);

        }
        else{
            btnsModificar.postValue(8);
        }
    }

    public void btnsCrear(Bundle bundle){
        Boolean Crear = bundle.getBoolean("btnsCrear");

        if(Crear){
            btnsCrear.postValue(0);

        }
        else{
            btnsCrear.postValue(8);
        }
    }
}