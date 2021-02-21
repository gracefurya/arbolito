package com.example.arbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class pantallaMenuUsuario extends AppCompatActivity {

    private persona p = new persona();

    private TextView pantUser;
    private TextView pantCi;
    private TextView pantCelular;
    private TextView pantTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_menu_usuario);

        pantUser = findViewById(R.id.pantUsuario);
        pantCi = findViewById(R.id.pantMostrarCi);
        pantCelular = findViewById(R.id.pantMostrarCelular);
        pantTelefono = findViewById(R.id.pantMostrarTelefono);


        p.setNombre(getIntent().getStringExtra("nombreU"));
        p.setPaterno(getIntent().getStringExtra("paternoU"));
        p.setMaterno(getIntent().getStringExtra("maternoU"));
        p.setCarnetidentidad(getIntent().getStringExtra("ci"));
        p.setTelefono(getIntent().getStringExtra("telefono"));
        p.setCelular(getIntent().getStringExtra("celular"));
        mostrarDatos();
    }

    public void mostrarDatos() {
        pantUser.setText(p.getNombre() + " " + p.getPaterno() + " " + p.getMaterno());
        pantCi.setText("Ci: "+p.getCarnetidentidad());
        pantCelular.setText("Celular: "+p.getCelular());
        pantTelefono.setText("Telefono: "+p.getTelefono());
    }

}