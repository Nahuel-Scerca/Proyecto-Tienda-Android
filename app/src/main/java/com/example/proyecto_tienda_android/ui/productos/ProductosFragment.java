package com.example.proyecto_tienda_android.ui.productos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_tienda_android.R;

public class ProductosFragment extends Fragment {

    private ProductosViewModel productosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productosViewModel =
                ViewModelProviders.of(this).get(ProductosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_productos, container, false);


        return root;
    }
}