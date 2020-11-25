package com.example.proyecto_tienda_android.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto_tienda_android.R;

public class LoginActivity extends AppCompatActivity {
    private EditText correo;
    private EditText contraseña;
    private Button entrar;
    private LoginActivityViewModel vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializar();
    }

    public void inicializar(){
        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
        vm.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();
            }
        });

        correo= findViewById(R.id.etCorreo);
        contraseña = findViewById(R.id.etContraseña);
        entrar= findViewById(R.id.btEntrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.autenticacion(correo.getText().toString(),contraseña.getText().toString());
                vm.cargarUsuario();
            }
        });



    }
}
