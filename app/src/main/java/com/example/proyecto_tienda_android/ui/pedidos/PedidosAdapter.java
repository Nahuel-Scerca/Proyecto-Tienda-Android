package com.example.proyecto_tienda_android.ui.pedidos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyecto_tienda_android.ui.pedidosadquiridosdisponibles.pedidosAdquiridosFragment;
import com.example.proyecto_tienda_android.ui.pedidosadquiridosdisponibles.pedidosDisponiblesFragment;

public class PedidosAdapter extends FragmentStateAdapter{


    public PedidosAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return  new pedidosDisponiblesFragment();
            default:
                return   new pedidosAdquiridosFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
