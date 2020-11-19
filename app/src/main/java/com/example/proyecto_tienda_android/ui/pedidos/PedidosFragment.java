package com.example.proyecto_tienda_android.ui.pedidos;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class PedidosFragment extends Fragment {

    private PedidosViewModel vm;
    private ViewPager2 viewPager2;
    private Context context;
    private int notificacionesDisponibles;
    private int notificacionesAdquiridos;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view){
        viewPager2 = view.findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PedidosAdapter(this));

        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PedidosViewModel.class);

        vm.getPedidosDisponibles().observe(getViewLifecycleOwner(), new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                notificacionesDisponibles=pedidos.size();
            }
        });

        vm.getPedidos().observe(getViewLifecycleOwner(), new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                notificacionesAdquiridos= pedidos.size();
                Log.d("notificacion",pedidos.size()+"");
            }
        });


        final TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator= new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText("Disponibles");
                        tab.setIcon(R.drawable.ic_disponibles);
                        BadgeDrawable badgeDrawableDis= tab.getOrCreateBadge();
                        badgeDrawableDis.setBackgroundColor(
                                ContextCompat.getColor(context,R.color.colorAccent)
                        );
                        badgeDrawableDis.setVisible(true);
                        badgeDrawableDis.setNumber(notificacionesDisponibles);
                        badgeDrawableDis.setMaxCharacterCount(3);
                        break;
                    }
                    case 1:{
                        tab.setText("Adquiridos");
                        tab.setIcon(R.drawable.ic_adquirido);
                        BadgeDrawable badgeDrawable= tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(context,R.color.colorAccent)
                        );
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(notificacionesAdquiridos);
                        badgeDrawable.setMaxCharacterCount(3);
                        break;
                    }
                }
            }
        }
        );
        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable= tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);
            }
        });

        vm.obtenerPedidosDisponibles();
        vm.obtenerPedidosAdquiridos();
    }
}