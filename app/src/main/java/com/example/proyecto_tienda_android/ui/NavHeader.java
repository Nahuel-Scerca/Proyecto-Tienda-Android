package com.example.proyecto_tienda_android.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;
import com.example.proyecto_tienda_android.modelo.Usuario;
import com.example.proyecto_tienda_android.ui.perfil.PerfilViewModel;
import com.example.proyecto_tienda_android.ui.ventasDetalle.PedidoDetalleViewModel;

public class NavHeader extends Fragment {

    private PerfilViewModel vm;
    private TextView tvNombre,tvEmail;
    private ImageView ivPerfil;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.nav_header_main, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view) {

        tvNombre = view.findViewById(R.id.tvNombre);
        tvEmail = view.findViewById(R.id.tvEmail);
        ivPerfil = view.findViewById(R.id.ivPerfil);


        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PerfilViewModel.class);

        vm.getUser().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                tvNombre.setText(usuario.getNombre());
                tvEmail.setText(usuario.getEmail());

            }
        });

        vm.obtenerPerfil();
    }
}
