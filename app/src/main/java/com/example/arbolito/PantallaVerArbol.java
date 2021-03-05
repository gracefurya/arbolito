package com.example.arbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PantallaVerArbol extends AppCompatActivity {

    private String URLApi=new configuracion().urlServidor;
    private int idpersona;
    LinearLayout la;

    private Arbol arboles[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ver_arbol);
        la=findViewById(R.id.listaImagenes);
        idpersona=getIntent().getIntExtra("idpersona",0);
        if (idpersona!=0) {
            obtenerArboles();
        }else{
            mostrarMensaje("Error al obtener datos");
        }

    }

    public void mostrarArboles(){
        for(int i=0;i<arboles.length;i++){
            ImageView iv=new ImageView(this);
            Picasso.get()
                    .load(URLApi+"/recursos/"+arboles[i].getIdarbol()+".jpg")
                    .into(iv);
            la.addView(iv);
            TextView nombreT=new TextView(this);
            nombreT.setText("Nombre: "+arboles[i].getNombre());
            la.addView(nombreT);
            TextView tipo=new TextView(this);
            tipo.setText("Tipo: "+arboles[i].getTipo());
            la.addView(tipo);
        }
    }

    public void obtenerArboles(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        arbolitoAPI aa=retrofit.create(arbolitoAPI.class);

        Call<ResponseArbol> call=aa.obtenerArbolesByPersonaId(idpersona);
        call.enqueue(new Callback<ResponseArbol>() {
            @Override
            public void onResponse(Call<ResponseArbol> call, Response<ResponseArbol> response) {
                if(response.isSuccessful()){
                    arboles=response.body().getData();
                    mostrarArboles();
                }
            }

            @Override
            public void onFailure(Call<ResponseArbol> call, Throwable t) {
                mostrarMensaje("error al conectar con el servidor");
            }
        });
    }


    public void mostrarMensaje(String mensaje){
        Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show();
    }
}