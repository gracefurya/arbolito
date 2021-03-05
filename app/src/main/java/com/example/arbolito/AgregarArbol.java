package com.example.arbolito;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

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
    private EditText pantallaTexto;
    private EditText pantallaTipo;
    private int idPersona;

    private ImageView image;
    private Uri uriImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_arbol);
        spinnerDistritos=findViewById(R.id.spinner);
        spinnerZonasVerdes=findViewById(R.id.spinner2);
        pantallaTexto=findViewById(R.id.editTextTextPersonName);
        pantallaTipo=findViewById(R.id.editTextTextPersonName2);
        idPersona=getIntent().getIntExtra("idpersona",0);
        image=findViewById(R.id.imageView);
        LlenarDistrito();
        System.out.println(idPersona);

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

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"seleccione una imagen"),10);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path =uri.getPath();
                    System.out.println("diirrrrrrrÑ " + path);
                }
                break;
        }
    }*/


     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == RESULT_OK) {
             Uri path = data.getData();
             System.out.println("direccion: " + path.getPath());
             uriImagen=path;
             image.setImageURI(path);
             //subirImagen(path);
         }
     }

    public String getAbsolutePath(Uri uri) {
        if (Build.VERSION.SDK_INT >= 19) {
            String id = "";
            if (uri.getLastPathSegment().split(":").length > 1)
                id = uri.getLastPathSegment().split(":")[1];
            else if (uri.getLastPathSegment().split(":").length > 0)
                id = uri.getLastPathSegment().split(":")[0];
            if (id.length() > 0) {
                final String[] imageColumns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
                Uri tempUri = uri;
                Cursor imageCursor = getContentResolver().query(tempUri, imageColumns, MediaStore.Images.Media._ID + "=" + id, null, null);
                if (imageCursor.moveToFirst()) {
                    return imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.ORIENTATION};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else
                return null;
        }

    }

    public void subirImagen(Uri path,Arbol arbol){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        arbolitoAPI aa=retrofit.create(arbolitoAPI.class);

        System.out.println(getAbsolutePath(path));
        File file = new File(getAbsolutePath(path));
        System.out.println(":::: "+file.exists());
        RequestBody requestFile =
                RequestBody.create( MediaType.parse("image/*"),
                        file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
        String descriptionString = "hello, this is description speaking";
        RequestBody description =RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        Call<ResponseBody> call=aa.updateProfile(body,requestFile,arbol);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("Se envio correctamente");
                mostrarTexto("Se guardo correctamente");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mostrarTexto("Error al conectar con el servidor");
                System.out.println("naaa");
            }
        });

    }

    public void subirArbol(Arbol arbol){

    }

    public void obtenerDatosYsubir(View view){
         try {
             String nombre = pantallaTexto.getText().toString();
             String tipo = pantallaTipo.getText().toString();
             Arbol arbol = new Arbol();
             arbol.setIdpersona(idPersona);
             arbol.setNombre(nombre);
             arbol.setIdzonaverde(zonasverdes[spinnerZonasVerdes.getSelectedItemPosition()].getIdzonaverde());
             arbol.setTipo(tipo);
             subirImagen(uriImagen, arbol);
         }catch (Exception e){
             System.out.println("Error :: "+e);
         }
    }

    public void mostrarTexto(String m){
         Toast mensaje= Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG);
         mensaje.setGravity(Gravity.CENTER,0,0);
         mensaje.show();
    }

}