package com.example.proyecto_tienda_android.ui.ventas;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class VentasFragment extends Fragment {

    private VentasViewModel vm;
    private Context context;
    private ListView lvLista;
    private ArrayAdapter<Pedido> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ventas, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view){
        lvLista = view.findViewById(R.id.lvLista);


        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(VentasViewModel.class);

        vm.getPedidos().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {
                adapter= new AdapterPedidosVendidos(context,R.layout.item_ventas,list,getLayoutInflater());
                lvLista.setAdapter(adapter);

                lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Bundle bundle = new Bundle();
                        Pedido pedido = adapter.getItem(i);

                        bundle.putSerializable("pedido", pedido);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragment_ventas_detalle, bundle);
                    }
                });

            }
        });

        vm.obtenerPedidosVendidos();


    }
}