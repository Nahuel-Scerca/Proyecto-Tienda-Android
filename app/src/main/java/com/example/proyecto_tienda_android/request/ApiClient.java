package com.example.proyecto_tienda_android.request;



import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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



        //LineasPedido
        @GET("PedidoProductos/{pedidoId}")
        Call<List<PedidoProducto>> obtenerLineasPedido(@Header("Authorization") String autorizacion, @Path("pedidoId") int id );


    }


}

