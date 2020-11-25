
package com.example.proyecto_tienda_android.ui.perfil;

        import android.app.Application;
        import android.content.Context;
        import android.content.SharedPreferences;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;

        import com.example.proyecto_tienda_android.modelo.Usuario;

public class PerfilViewModel  extends AndroidViewModel {

    private Context context;
    MutableLiveData<Usuario> user;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<Usuario> getUser() {
        if(user==null){
            user= new MutableLiveData<>();
        }

        return user;
    }

    public void obtenerPerfil(){

        final SharedPreferences sh = context.getSharedPreferences("datos", 0);
        String nombre = sh.getString("nombre", "---");
        String apellido = sh.getString("apellido", "---");
        String email = sh.getString("email", "---");
        String avatar = sh.getString("avatar", "---");
        int rol = sh.getInt("rol", -1);
        String rolNombre = sh.getString("rolNombre", "---");

        Usuario u= new Usuario();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);
        u.setAvatar(avatar);
        u.setRol(rol);
        u.setRolNombre(rolNombre);

        user.postValue(u);



    }

}
