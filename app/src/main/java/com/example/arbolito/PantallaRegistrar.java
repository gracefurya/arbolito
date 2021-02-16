package com.example.arbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PantallaRegistrar extends AppCompatActivity {

    private EditText pantNombre;
    private EditText pantPaterno;
    private EditText pantMaterno;
    private EditText pantCi;
    private EditText pantTelefono;
    private EditText pantCelular;

    private String URLApi=new configuracion().urlServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar);
        pantNombre=findViewById(R.id.pantNombre);
        pantPaterno=findViewById(R.id.pantPaterno);
        pantMaterno=findViewById(R.id.pantMaterno);
        pantCi=findViewById(R.id.pantCi);
        pantTelefono=findViewById(R.id.pantTelefono);
        pantCelular=findViewById(R.id.pantCelular);

    }

    public void RegistrarUsuario(View view){
        persona p=new persona();
        p.setNombre(pantNombre.getText().toString());
        p.setPaterno(pantPaterno.getText().toString());
        p.setMaterno(pantMaterno.getText().toString());
        p.setCarnetidentidad(pantCi.getText().toString());
        p.setTelefono(pantTelefono.getText().toString());
        p.setCelular(pantCelular.getText().toString());
        EnviarDatos(p);
    }

    public void EnviarDatos(persona p){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        arbolitoAPI aa=retrofit.create(arbolitoAPI.class);

        Call<Void> call=aa.registrarPersona(p);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    System.out.println("se registro normal carnal");
                    mostrarMensaje("Se registro correctamente");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.err.println("hoy no toca");
                mostrarMensaje("Error a la hora de registrar");
            }
        });
    }
    public void mostrarMensaje(String mensaje){
        Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show();
    }
}








































