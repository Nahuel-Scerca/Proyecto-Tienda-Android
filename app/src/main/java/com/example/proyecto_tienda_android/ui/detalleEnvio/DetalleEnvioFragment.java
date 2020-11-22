package com.example.proyecto_tienda_android.ui.detalleEnvio;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Pedido;
import com.example.proyecto_tienda_android.modelo.PedidoProducto;
import com.example.proyecto_tienda_android.ui.ventasDetalle.PedidoDetalleViewModel;

public class DetalleEnvioFragment extends Fragment {

    private DetalleEnvioViewModel vm;
    private Context context;
    private TextView tvPedido,tvNombreCliente,tvDireccion;
    private RadioButton rbEnPreparacion,rbEnCamino,rbEntregado;
    private RadioGroup radioGroup;
    private SeekBar seekBar;
    private Button btGuardar;
    private Pedido pedidoPut;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalle_envio, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view) {
        tvPedido= view.findViewById(R.id.tvPedido);
        tvNombreCliente= view.findViewById(R.id.tvNombreCliente);
        tvDireccion= view.findViewById(R.id.tvDireccion);
        rbEnPreparacion= view.findViewById(R.id.rbEnPreparacion);
        rbEnCamino= view.findViewById(R.id.rbEnCamino);
        radioGroup= view.findViewById(R.id.radioGroup);
        rbEntregado= view.findViewById(R.id.rbEntregado);
        seekBar= view.findViewById(R.id.seekBar);
        btGuardar= view.findViewById(R.id.btGuardar);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetalleEnvioViewModel.class);
        vm.getPedido().observe(getViewLifecycleOwner(), new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                pedidoPut= pedido;
                tvPedido.setText("Pedido nº 000-"+pedido.getId());
                tvNombreCliente.setText("Cliente: "+pedido.getCliente().getNombre()+" "+pedido.getCliente().getApellido()+" Dni "+pedido.getCliente().getDni());
                tvDireccion.setText("Destino: "+pedido.getCliente().getDireccion() +" Telefono:"+pedido.getCliente().getTelefono());
                switch (pedido.getEstado()){
                    case 1:{ seekBar.setProgress(0);
                    rbEnPreparacion.setChecked(true);
                        break;
                    }
                    case 2:{
                        seekBar.setProgress(5);
                        rbEnCamino.setChecked(true);
                        break;
                    }
                    case 3:{
                        seekBar.setProgress(10);
                        rbEntregado.setChecked(true);
                        break;
                    }
                    default:seekBar.setProgress(3);
                }
            }
        });

        seekBar.setActivated(false);

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idEstado;

                if(rbEnPreparacion.isChecked()){
                    idEstado=1;
                    seekBar.setProgress(0);
                }else if(rbEnCamino.isChecked()){
                    idEstado=2;
                    seekBar.setProgress(5);
                }else if(rbEntregado.isChecked()){
                    idEstado=3;
                    seekBar.setProgress(10);
                }
                else{
                    idEstado=1;
                }

                vm.actualizarEstado(pedidoPut,idEstado);
            }
        });

        vm.obtenerPedido(getArguments());
    }
    }
