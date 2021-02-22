package com.example.arbolito;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface arbolitoAPI {
    @GET("api/v1/persona/{idpersona}")
    public Call<persona> find(@Path("idpersona") int idpersona);

    @GET("api/v1/persona/ci/{cipersona}")
    public Call<persona> buscarCi(@Path("cipersona") String cipersona);

    @POST("api/v1/persona")
    public Call<Void> registrarPersona(@Body persona p);

    @GET("api/v1/distrito")
    public Call<ResponseDistrito> obtenerDistritos();

    @GET("/api/v1/zonaverde/distrito/{iddistrito}")
    public Call<ResponseZonaVerde> obtenerZonasVerdesByIDDistrito(@Path("iddistrito") int iddistrito);
}
