package com.example.proyecto_tienda_android.ui.pedidosadquiridosdisponibles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterPedidosAdquiridosDisponibles extends ArrayAdapter<Pedido> {

    private Context context;
    private List<Pedido> lista;
    private LayoutInflater li;

    public AdapterPedidosAdquiridosDisponibles(@NonNull Context context, int resource, @NonNull List<Pedido> objects , LayoutInflater li) {

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
            itemView=li.inflate(R.layout.item_pedidos_disponibles_adquiridos,parent,false);

        }
        Pedido pedido= lista.get(position);
        ImageView foto = itemView.findViewById(R.id.ivPerfil);

    /* Glide.with(context)
                .load("http://192.168.0.14:45455/Uploads/"+inmueble.getFoto().trim())
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(foto);*/



        TextView tvFechaPedido= itemView.findViewById(R.id.tvFechaPedido);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String FechaParseado = formato.format(pedido.getFecha());
        tvFechaPedido.setText("Fecha: "+FechaParseado);

        TextView tvPedido = itemView.findViewById(R.id.tvPedido);
        tvPedido.setText("Pedido Nº 000-00"+ pedido.getId());


        TextView tvDireccionPedido = itemView.findViewById(R.id.tvDireccionPedido);
        tvDireccionPedido.setText("Direccion : "+pedido.getCliente().getDireccion());

        TextView tvPrecioFinal = itemView.findViewById(R.id.tvPrecioFinal);
        tvPrecioFinal.setText("Precio : "+pedido.getPrecioFinal());


        TextView tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
        tvNombreCliente.setText("Cliente: "+pedido.getCliente().getNombre()+" "+pedido.getCliente().getApellido());


        return itemView;

    }
}
