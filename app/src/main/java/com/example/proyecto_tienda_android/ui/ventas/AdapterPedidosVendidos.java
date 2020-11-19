package com.example.proyecto_tienda_android.ui.ventas;

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

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterPedidosVendidos extends ArrayAdapter<Pedido> {

    private Context context;
    private List<Pedido> lista;
    private LayoutInflater li;

    public AdapterPedidosVendidos(@NonNull Context context, int resource, @NonNull List<Pedido> objects , LayoutInflater li) {

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
            itemView=li.inflate(R.layout.item_ventas,parent,false);

        }
        Pedido pedido= lista.get(position);
        ImageView foto = itemView.findViewById(R.id.ivPerfil);

    /* Glide.with(context)
                .load("http://192.168.0.14:45455/Uploads/"+inmueble.getFoto().trim())
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(foto);*/


        TextView fecha= itemView.findViewById(R.id.tvFecha);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String FechaParseado = formato.format(pedido.getFecha());
        fecha.setText("Fecha: "+FechaParseado);

        TextView vendedor = itemView.findViewById(R.id.tvVendedor);
        vendedor.setText("Vendedor: "+ pedido.getUsuarioACargo());


        TextView montoFinal = itemView.findViewById(R.id.tvMontoFinal);
        montoFinal.setText("Monto Total : "+pedido.getPrecioFinal());



        return itemView;

    }
}
