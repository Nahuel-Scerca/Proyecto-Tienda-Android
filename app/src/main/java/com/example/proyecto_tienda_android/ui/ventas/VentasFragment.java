package com.example.proyecto_tienda_android.ui.ventas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_tienda_android.R;

public class VentasFragment extends Fragment {

    private VentasViewModel ventasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ventasViewModel =
                ViewModelProviders.of(this).get(VentasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ventas, container, false);

        return root;
    }
}