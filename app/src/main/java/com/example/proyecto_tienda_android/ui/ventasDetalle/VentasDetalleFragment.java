package com.example.proyecto_tienda_android.ui.ventasDetalle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;

import java.util.List;

public class VentasDetalleFragment extends Fragment {

    private PedidoDetalleViewModel vm;
    private ListView lvListaLineasVentas;
    private TextView tvTitulo,tvMontoTotal;
    private ArrayAdapter<PedidoProducto> adapter;
    private Context context;
    private Button btDetalleEnvio,btAdquirir;
    private Pedido pedidoMutable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ventas_detalle, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view) {

        lvListaLineasVentas = view.findViewById(R.id.lvListaLineasVentas);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvMontoTotal = view.findViewById(R.id.tvMontoTotal);
        btDetalleEnvio = view.findViewById(R.id.btDetalleEnvio);
        btAdquirir= view.findViewById(R.id.btAdquirir);



        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PedidoDetalleViewModel.class);

        //Funcionalidad de BOton adquirir Pedido
        vm.getBotonAdquirido().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                btAdquirir.setVisibility(integer);
            }
        });
        btAdquirir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vm.adquirirPedido(getArguments());
            }
        });



        vm.getMensajeAdquirido().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            }
        });

        //Funcionalidad de BOton Detalle Pedido
        vm.getBotonDetalleEnvio().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                btDetalleEnvio.setVisibility(integer);
            }
        });

        btDetalleEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putSerializable("pedido", pedidoMutable);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragment_detalle_envio, bundle);
            }
        });

        vm.getLineasPedidoProducto().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {
                adapter= new AdapterLineasPedido(context,R.layout.item_lineasventadetalle,list,getLayoutInflater());
                lvListaLineasVentas.setAdapter(adapter);


            }
        });
        vm.getPedido().observe(getViewLifecycleOwner(), new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                pedidoMutable= pedido;
                tvTitulo.setText("Pedido nª "+pedido.getId());
                tvMontoTotal.setText("Monto: "+pedido.getPrecioFinal());
            }
        });

        vm.obtenerLineasDePedido(getArguments());


    }
}