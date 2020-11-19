package com.example.proyecto_tienda_android.ui.pedidosadquiridosdisponibles;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.ui.pedidos.PedidosViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pedidosDisponiblesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pedidosDisponiblesFragment extends Fragment {

    private Context context;
    private PedidosViewModel vm;
    private ListView lvListaPedidosDisponibles;
    ArrayAdapter<Pedido> adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pedidosDisponiblesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pedidosDisponiblesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static pedidosDisponiblesFragment newInstance(String param1, String param2) {
        pedidosDisponiblesFragment fragment = new pedidosDisponiblesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pedidos_disponibles, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view){
        lvListaPedidosDisponibles = view.findViewById(R.id.lvListaPedidosDisponibles);


        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PedidosViewModel.class);


        vm.getPedidosDisponibles().observe(getViewLifecycleOwner(), new Observer<List>() {
            @Override
            public void onChanged(List list) {
                adapter= new AdapterPedidosAdquiridosDisponibles(context,R.layout.item_pedidos_disponibles_adquiridos,list,getLayoutInflater());
                lvListaPedidosDisponibles.setAdapter(adapter);

                lvListaPedidosDisponibles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Bundle bundle = new Bundle();
                        Pedido pedido = adapter.getItem(i);

                        bundle.putSerializable("pedido", pedido);
                        bundle.putBoolean("botonAdquirido",true);


                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragment_ventas_detalle, bundle);
                    }
                });


            }
        });

        vm.obtenerPedidosDisponibles();


    }
}