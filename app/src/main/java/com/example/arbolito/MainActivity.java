package com.example.arbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public EditText carnetIdentidad;
    public String urlAPI=new configuracion().urlServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carnetIdentidad=findViewById(R.id.LoginUserCi);
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
































