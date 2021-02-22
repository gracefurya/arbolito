package com.example.arbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarArbol extends AppCompatActivity {

    private String URLApi=new configuracion().urlServidor;

    private Spinner spinnerDistritos;
    private Spinner spinnerZonasVerdes;
    private Distrito[] distritos;
    private Distrito distritoSeleccionado;
    private ZonaVerde[] zonasverdes;
    private ZonaVerde zonaverdeSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_arbol);
        spinnerDistritos=findViewById(R.id.spinner);
        spinnerZonasVerdes=findViewById(R.id.spinner2);
        LlenarDistrito();
    }

    public void LlenarDistrito(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        arbolitoAPI aa=retrofit.create(arbolitoAPI.class);
        Call<ResponseDistrito> call=aa.obtenerDistritos();
        call.enqueue(new Callback<ResponseDistrito>() {
            @Override
            public void onResponse(Call<ResponseDistrito> call, Response<ResponseDistrito> response) {
                if(response.isSuccessful()) {
                    distritos = response.body().getData();

                    llenandoLista();
                }
            }

            @Override
            public void onFailure(Call<ResponseDistrito> call, Throwable t) {
                System.err.println("busca en otro lado we, aqui no hay");
            }
        });
    }

    public void llenandoLista(){
        ArrayList<String> arrayList=new ArrayList<>();

        for (int i = 0; i < distritos.length; i++) {
            System.out.println(" ::: " + distritos[i].getNombre());
            arrayList.add(distritos[i].getNombre());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistritos.setAdapter(arrayAdapter);
        spinnerDistritos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String itemSeleccionado=parent.getItemAtPosition(position).toString();
                distritoSeleccionado=distritos[position];
                llenarZonaVerde(distritoSeleccionado.getIddistrito());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void llenarZonaVerde(int iddistrito){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        arbolitoAPI aa=retrofit.create(arbolitoAPI.class);
        Call<ResponseZonaVerde> call=aa.obtenerZonasVerdesByIDDistrito(iddistrito);
        call.enqueue(new Callback<ResponseZonaVerde>() {
            @Override
            public void onResponse(Call<ResponseZonaVerde> call, Response<ResponseZonaVerde> response) {
                if(response.isSuccessful()){
                    zonasverdes=response.body().getData();
                    llenarSpinerDistrito();
                }
            }

            @Override
            public void onFailure(Call<ResponseZonaVerde> call, Throwable t) {
                System.out.println("Apagalo otto!!!");
            }
        });
    }

    public void llenarSpinerDistrito(){
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<zonasverdes.length;i++){
            arrayList.add(zonasverdes[i].getNombre());
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZonasVerdes.setAdapter(arrayAdapter);
        spinnerZonasVerdes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String itemSeleccionado = parent.getItemAtPosition(position).toString();
                zonaverdeSeleccionada=zonasverdes[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}