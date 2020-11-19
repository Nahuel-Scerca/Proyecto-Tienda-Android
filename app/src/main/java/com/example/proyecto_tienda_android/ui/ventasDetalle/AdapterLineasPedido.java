package com.example.proyecto_tienda_android.ui.ventasDetalle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterLineasPedido extends ArrayAdapter<PedidoProducto> {

    private Context context;
    private List<PedidoProducto> lista;
    private LayoutInflater li;

    public AdapterLineasPedido(@NonNull Context context, int resource, @NonNull List<PedidoProducto> objects , LayoutInflater li) {

        super(context, resource, objects);
        this.context = context;
        this.lista= objects;
        this.li=li;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView= convertView;
        if(itemView==null){
            itemView=li.inflate(R.layout.item_lineasventadetalle,parent,false);

        }
        PedidoProducto pedidoProducto= lista.get(position);
        ImageView foto = itemView.findViewById(R.id.ivFoto);

        Glide.with(context)
                .load(pedidoProducto.getProducto().getFoto())
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(foto);

        TextView cantidad = itemView.findViewById(R.id.tvCantidad);
        cantidad.setText("Cantidad: "+pedidoProducto.getCantidad()+"");

        TextView descripcion = itemView.findViewById(R.id.tvDescripcion);
        descripcion.setText("Talle: "+pedidoProducto.getProducto().getTalle()+"\n"+"Color"+pedidoProducto.getProducto().getColor());

        TextView nombreProducto = itemView.findViewById(R.id.tvNombre);
        nombreProducto.setText(pedidoProducto.getProducto().getDescripcion());


        TextView montoTotal = itemView.findViewById(R.id.tvMontoTotal);
        double precio= pedidoProducto.getProducto().getPrecio()*pedidoProducto.getCantidad();
        montoTotal.setText("Monto: "+precio);



        return itemView;

    }
}
