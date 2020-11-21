package com.example.proyecto_tienda_android.ui.productos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.Producto;
import com.example.proyecto_tienda_android.ui.pedidos.PedidosAdapter;
import com.example.proyecto_tienda_android.ui.pedidos.PedidosViewModel;
import com.example.proyecto_tienda_android.ui.ventas.AdapterPedidosVendidos;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductosFragment extends Fragment {

    private ProductosViewModel vm;
    private Context context;
    private SearchView buscador;
    private ArrayAdapter<Producto> adapter;
    private ListView  lvListaProductos;
    private FloatingActionButton btCrearProducto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view) {
        buscador = view.findViewById(R.id.buscador);
        btCrearProducto= view.findViewById(R.id.btCrearProducto);
        lvListaProductos= view.findViewById(R.id.lvListaProductos);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ProductosViewModel.class);
        vm.getProductosList().observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                adapter= new AdapterProducto(context,R.layout.item_producto,productos,getLayoutInflater());
                lvListaProductos.setAdapter(adapter);

                lvListaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Bundle bundle = new Bundle();
                        Producto producto = adapter.getItem(i);

                        bundle.putSerializable("producto", producto);
                        bundle.putBoolean("btnsModificar",true);

                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.productoDetalleFragment, bundle);
                    }
                });
            }
        });

        vm.obtenerProductos();

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                vm.setProductoBuscar(s);
                vm.obtenerProductosBuscador();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        btCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putBoolean("btnsCrear",true);

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.productoDetalleFragment, bundle);


            }
        });
    }

    }