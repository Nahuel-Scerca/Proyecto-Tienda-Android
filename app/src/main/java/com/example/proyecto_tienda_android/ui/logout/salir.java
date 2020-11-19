package com.example.proyecto_tienda_android.ui.logout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.proyecto_tienda_android.R;

public class salir extends Fragment {

    private SalirViewModel mViewModel;

    public static salir newInstance() {
        return new salir();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cerrarSesión();
        return inflater.inflate(R.layout.salir_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SalirViewModel.class);
        // TODO: Use the ViewModel
    }
    public void cerrarSesión() {
        new AlertDialog.Builder(getContext())
                .setTitle("Cierre de sesión")
                .setMessage("Está seguro de que desea cerrar la sesión?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mViewModel.logout();

                        System.exit(0);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_productos);
                    }
                }).show();
    }

}