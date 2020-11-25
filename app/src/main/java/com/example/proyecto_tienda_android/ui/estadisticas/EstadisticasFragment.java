package com.example.proyecto_tienda_android.ui.estadisticas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Usuario;
import com.example.proyecto_tienda_android.modelo.UsuarioVentas;
import com.example.proyecto_tienda_android.ui.detalleEnvio.DetalleEnvioViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasFragment extends Fragment{

    private EstadisticasViewModel vm;
    private Context context;
    private PieChart pieChart;
    private TextView tvNombreEmpeado,tvCantidad;
    private ImageView ivPerfilEmpleado;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View view) {
        pieChart= view.findViewById(R.id.pieChart);
        tvNombreEmpeado= view.findViewById(R.id.tvNombreEmpeado);
        ivPerfilEmpleado= view.findViewById(R.id.ivPerfilEmpleado);
        tvCantidad= view.findViewById(R.id.tvCantidad);


        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(EstadisticasViewModel.class);

        vm.getListUsuariosApi().observe(getViewLifecycleOwner(), new Observer<List<UsuarioVentas>>() {
            @Override
            public void onChanged(List<UsuarioVentas> usuarioVentas) {

                //Mandamos a buscar el usuarioTop
                vm.traerUsuarioTop(usuarioVentas);

                //cargamos los datos en el grafico de torta
                vm.cargarDatos(usuarioVentas);
            }
        });
        vm.getUsuarioTop().observe(getViewLifecycleOwner(), new Observer<UsuarioVentas>() {
            @Override
            public void onChanged(UsuarioVentas uv) {

                tvNombreEmpeado.setText(uv.getUsuario().getNombre()+" "+uv.getUsuario().getApellido());
                tvCantidad.setText("Pedidos Entregados:"+uv.getCantidadVentas());
                Glide.with(context)
                        .load(uv.getUsuario().getAvatar())
                        .placeholder(R.drawable.ic_launcher_background)
                        .fitCenter()
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                        .into(ivPerfilEmpleado);
            }
        });

        vm.obtenerUsuarios();
        vm.getDatos().observe(getViewLifecycleOwner(), new Observer<ArrayList<PieEntry>>() {
            @Override
            public void onChanged(ArrayList<PieEntry> pieEntries) {


                PieDataSet pieDataSet = new PieDataSet(pieEntries,"Estadisticas");
                pieDataSet.setSliceSpace(3f);
                pieDataSet.setSelectionShift(5f);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(16f);

                PieData pieData = new PieData(pieDataSet);
                pieChart.setCenterTextColor(Color.YELLOW);
                pieChart.setUsePercentValues(true);
                pieChart.setData(pieData);
                pieChart.getDescription().setText("Ventas Mensuales");
                pieChart.getDescription().setTextSize(18f);
                pieChart.animateY(2000);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }
        });



    }
}
