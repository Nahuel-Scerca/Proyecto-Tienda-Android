package com.example.proyecto_tienda_android.ui.productos;

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
import com.example.proyecto_tienda_android.modelo.PedidoProducto;
import com.example.proyecto_tienda_android.modelo.Producto;

import java.util.List;

public class AdapterProducto extends ArrayAdapter<Producto> {

    private Context context;
    private List<Producto> lista;
    private LayoutInflater li;

    public AdapterProducto(@NonNull Context context, int resource, @NonNull List<Producto> objects , LayoutInflater li) {

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
            itemView=li.inflate(R.layout.item_producto,parent,false);

        }
        Producto Producto= lista.get(position);
        ImageView foto = itemView.findViewById(R.id.ivFotoProducto);

        Glide.with(context)
                .load(Producto.getFoto())
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(foto);

        TextView talle = itemView.findViewById(R.id.tvTalleProducto);
        talle.setText("Talles: "+Producto.getTalle());

        TextView descripcion = itemView.findViewById(R.id.tvDescripcionProducto);
        descripcion.setText("Color"+Producto.getColor());

        TextView nombreProducto = itemView.findViewById(R.id.tvNombreProducto);
        nombreProducto.setText(Producto.getNombre());


        TextView monto = itemView.findViewById(R.id.tvMontoProducto);

        monto.setText("Monto: "+Producto.getPrecio());



        return itemView;

    }
}
