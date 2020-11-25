package com.example.proyecto_tienda_android.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Usuario;
import com.example.proyecto_tienda_android.ui.pedidos.PedidosAdapter;
import com.example.proyecto_tienda_android.ui.pedidos.PedidosViewModel;

public class PerfilFragment extends Fragment {

    private Context context;
    private PerfilViewModel vm;

    private TextView tvNombre,tvEmail,tvRolNombre;
    private ImageView ivImagen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view){

        tvNombre = view.findViewById(R.id.tvNombre);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvRolNombre=view.findViewById(R.id.tvRolNombre);
        ivImagen = view.findViewById(R.id.ivImagen);


        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PerfilViewModel.class);

        vm.getUser().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                tvNombre.setText(usuario.getNombre()+" "+usuario.getApellido());
                tvEmail.setText(usuario.getEmail());
                tvRolNombre.setText(usuario.getRolNombre());

                Glide.with(context)
                        .load(usuario.getAvatar())
                        .placeholder(R.drawable.ic_launcher_background)
                        .fitCenter()
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                        .into(ivImagen);

            }
        });

        vm.obtenerPerfil();
    }
}
