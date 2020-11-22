package com.example.proyecto_tienda_android.request;



import android.telephony.mbms.FileInfo;

import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;
import com.example.proyecto_tienda_android.modelo.Producto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {

    private static final String PATH ="http://192.168.0.14:45455/api/";
    private static MyApiInterface myApiInterface;

    public static  MyApiInterface getMyApiInterface(){
        Gson gson= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        myApiInterface= retrofit.create(MyApiInterface.class);

        return myApiInterface;

    }


    public interface MyApiInterface {


        //Propietarios
        @FormUrlEncoded
        @POST("Usuarios/Login")
        Call<String> obtenerToken(@Field("Usuario") String usuario,@Field("Clave") String clave);



        //Pedidos
        @GET("Pedidos/Vendidos")
        Call<List<Pedido>> obtenerPedidosVendidos(@Header("Authorization") String autorizacion);

        @GET("Pedidos/Disponibles")
        Call<List<Pedido>> obtenerPedidosDisponibles(@Header("Authorization") String autorizacion);

        @GET("Pedidos/Adquiridos")
        Call<List<Pedido>> obtenerPedidosAdquiridos(@Header("Authorization") String autorizacion);

        @PUT("Pedidos/AdquirirPedido/{id}")
        Call<String> adquirirPedido(@Header("Authorization") String autorizacion,@Path("id") int id);

        @PUT("Pedidos/ActualizarEstado")
        Call<Pedido> actualizarEstado(@Header("Authorization") String autorizacion,@Body Pedido pedido);



        //LineasPedido
        @GET("PedidoProductos/{pedidoId}")
        Call<List<PedidoProducto>> obtenerLineasPedido(@Header("Authorization") String autorizacion, @Path("pedidoId") int id );


        //Producto
        @GET("Productos")
        Call<List<Producto>> obtenerProductos(@Header("Authorization") String autorizacion);

        @GET("Productos/Unico/{id}")
        Call<Producto> obtenerProductoUnico(@Header("Authorization") String autorizacion, @Path("id") int id );

        @GET("Productos/{buscar}")
        Call<List<Producto>> obtenerProductosBuscador(@Header("Authorization") String autorizacion, @Path("buscar") String buscar);

        @PUT("Productos")
        Call<Producto> actualizarProducto(@Header("Authorization") String autorizacion,@Body Producto producto);

        @Multipart
        @POST("Productos/Foto")
        Call<ResponseBody> postFotoProducto(@Header("Authorization") String autorizacion, @Part MultipartBody.Part imagen);

        @POST("Productos/Post")
        Call<Producto> postProducto(@Header("Authorization") String autorizacion,@Body Producto producto);

    }


}

