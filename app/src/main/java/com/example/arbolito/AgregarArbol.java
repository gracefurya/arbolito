package com.example.arbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_arbol);
        spinnerDistritos=findViewById(R.id.spinner);
        spinnerZonasVerdes=findViewById(R.id.spinner2);
        image=findViewById(R.id.imageView);
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

    public void agregarArbolitoImagen(View view){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione una Imagen"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            System.out.println("direccion: "+path.getPath());
            image.setImageURI(path);
            subirImagen(path);
        }
    }

    public void subirImagen(Uri path){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        arbolitoAPI aa=retrofit.create(arbolitoAPI.class);

        File file = new File("external/images/media/Phalaenopsis_JPEG.png");
        System.out.println(file.getName());
        RequestBody requestFile =
                RequestBody.create( MediaType.parse(getContentResolver().getType(path)),
                        file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
        String descriptionString = "hello, this is description speaking";
        RequestBody description =RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        Call<ResponseBody> call=aa.updateProfile(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("naaa");
            }
        });

    }

}