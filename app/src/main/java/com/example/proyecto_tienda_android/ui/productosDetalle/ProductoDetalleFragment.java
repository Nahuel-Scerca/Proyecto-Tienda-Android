package com.example.proyecto_tienda_android.ui.productosDetalle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import static android.app.Activity.RESULT_OK;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyecto_tienda_android.R;
import com.example.proyecto_tienda_android.modelo.Producto;
import com.example.proyecto_tienda_android.ui.productosDetalle.ProductoDetalleViewModel;

public class ProductoDetalleFragment extends Fragment {

    private ProductoDetalleViewModel vm;
    private Context context;
    private Button btModificar,btCrear,btSubirFoto;
    private EditText etCodigo,etNombre,etDescripcion,etPrecio,etCategoria,etStock,etTalle,etColor,etFoto;
    private ImageView imageView2;
    private Producto productoObservado;
    private  String fullPhotoUri;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(data == null) {
                Toast.makeText(getContext(), "PUES NO ", Toast.LENGTH_LONG).show();
                return;
            }
            Uri imagenUri = data.getData();
            fullPhotoUri = pathReal(imagenUri);
            etFoto.setText(fullPhotoUri);
        }

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.producto_detalle_fragment, container, false);
        context=root.getContext();
        inicializar(root);
        return root;
    }



    private void inicializar(View view) {


        //Edittext
        etCodigo = view.findViewById(R.id.etCodigo);
        etNombre= view.findViewById(R.id.etNombre);
        etDescripcion= view.findViewById(R.id.etDescripcion);
        etPrecio= view.findViewById(R.id.etPrecio);
        etCategoria= view.findViewById(R.id.etCategoria);
        etStock= view.findViewById(R.id.etStock);
        etTalle= view.findViewById(R.id.etTalle);
        etColor= view.findViewById(R.id.etColor);
        etFoto= view.findViewById(R.id.etFoto);

        etCodigo.setEnabled(false);
        etCategoria.setEnabled(false);

        //ImgView
        imageView2= view.findViewById(R.id.imageView2);

        //Btnes
        btModificar = view.findViewById(R.id.btModificar);
        btCrear= view.findViewById(R.id.btCrear);
        btSubirFoto= view.findViewById(R.id.btSubirFoto);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ProductoDetalleViewModel.class);

        btModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productoObservado.setNombre(etNombre.getText().toString());
                productoObservado.setDescripcion(etDescripcion.getText().toString());
                productoObservado.setPrecio(Double.parseDouble(etPrecio.getText().toString()));
                productoObservado.setCategoria(Integer.parseInt(etCategoria.getText().toString()));
                productoObservado.setStock(Integer.parseInt(etStock.getText().toString()));
                productoObservado.setTalle(etTalle.getText().toString());
                productoObservado.setColor(etColor.getText().toString());


                vm.putProducto(productoObservado);
                Toast.makeText(context,"Producto Actualizado",Toast.LENGTH_LONG).show();
            }
        });


        //BOTON CREAR
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Producto productoPost = new Producto();


                productoPost.setNombre(etNombre.getText().toString());
                productoPost.setDescripcion(etDescripcion.getText().toString());
                productoPost.setPrecio(Double.parseDouble(etPrecio.getText().toString()));
                productoPost.setCategoria(Integer.parseInt(etCategoria.getText().toString()));
                //productoPost.setCategoriaNombre("Generica");
                productoPost.setStock(Integer.parseInt(etStock.getText().toString()));
                productoPost.setTalle(etTalle.getText().toString());
                productoPost.setColor(etColor.getText().toString());
                productoPost.setFoto("sd");


                    vm.postProducto(productoPost,etFoto.getText().toString());




                Toast.makeText(context,"¡Producto Creado!",Toast.LENGTH_LONG).show();
            }
        });

        vm.getProducto().observe(getViewLifecycleOwner(), new Observer<Producto>() {
            @Override
            public void onChanged(Producto producto) {
                if(producto!=null){
                    etCodigo.setText(producto.getId()+"");
                    etNombre.setText(producto.getNombre());
                    etDescripcion.setText(producto.getDescripcion());
                    etPrecio.setText(""+producto.getPrecio());
                    etCategoria.setText(producto.getCategoria()+"");
                    etStock.setText(producto.getStock()+"");
                    etTalle.setText(producto.getTalle());
                    etColor.setText(producto.getColor());
                    etFoto.setText(producto.getFoto());

                    Glide.with(context)
                            .load(producto.getFoto())
                            .placeholder(R.drawable.ic_launcher_background)
                            .fitCenter()
                            .into(imageView2);

                    productoObservado=producto;
                }

            }
        });

        vm.getBtnsModificar().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                btModificar.setVisibility(integer);
            }
        });

        vm.getBtnsCrear().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                btCrear.setVisibility(integer);
                btSubirFoto.setVisibility(integer);
                etCategoria.setEnabled(true);
            }
        });

        btSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });


        vm.obtenerProducto(getArguments());



    }

    private String pathReal(Uri uri) {
        String [] projection = {MediaStore.Images.Media.DATA} ;
        CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

    }


