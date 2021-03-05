package com.example.arbolito;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public EditText carnetIdentidad;
    public String urlAPI=new configuracion().urlServidor;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirPermiso();
        carnetIdentidad=findViewById(R.id.LoginUserCi);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void pedirPermiso(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            System.out.println("Todo bien con los permisos");
        }else{
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                mostrarMensaje("Se necesita permiso para subir imagenes");
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MODE_WORLD_READABLE);
        }
    }

    public void mostrarMensaje(String mensaje){
        Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show();
    }

    public void obtenerDatosCi(View view){
        String ci=carnetIdentidad.getText().toString();
        System.out.println(ci);
        BuscarPorCi(ci);
    }

    public void BuscarPorCi(String ci){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        arbolitoAPI aa=retrofit.create(arbolitoAPI.class);
        Call<persona> call=aa.buscarCi(ci);
        call.enqueue(new Callback<persona>() {
            @Override
            public void onResponse(Call<persona> call, Response<persona> response) {
                if(response.isSuccessful()) {
                    persona p = response.body().getData();

                    System.out.println("nombre: " + p.getNombre());
                    Intent intent=new Intent(MainActivity.this,pantallaMenuUsuario.class);
                    intent.putExtra("nombreU",p.getNombre());
                    intent.putExtra("paternoU",p.getPaterno());
                    intent.putExtra("maternoU",p.getMaterno());
                    intent.putExtra("ci",p.getCarnetidentidad());
                    intent.putExtra("telefono",p.getTelefono());
                    intent.putExtra("celular",p.getCelular());
                    intent.putExtra("idpersona",p.getIdpersona());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<persona> call, Throwable t) {
                System.err.println("error we");
            }
        });

    }

    public void irARegistrar(View view){
        Intent intent=new Intent(MainActivity.this,PantallaRegistrar.class);
        startActivity(intent);
    }
}
































