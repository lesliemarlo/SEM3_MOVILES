package com.example.semana02;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.semana02.entity.User;
import com.example.semana02.service.ServiceUser;
import com.example.semana02.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import adapter.UserAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //ListView y Adapter
    ListView lstUser;
    ArrayList<User> listaUser = new ArrayList<User>();
    UserAdapter userAdapter;


//--

    Button btnFiltrar;
    //--
   //LinearLayoutCompat caja;


    //conecta al servicio REST
    ServiceUser serviceUser;

    private List<User> listaTotalUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //---
        lstUser = findViewById(R.id.lstUsuarios);//se crea objeto visual
        userAdapter = new UserAdapter(this, R.layout.user_item, listaUser);//se crea e adaptador donde pasamos data y diseño
        lstUser.setAdapter(userAdapter);//empatmos ambos
        //--

        //Relaciona las variables con las variables de la GUI
        btnFiltrar = findViewById(R.id.btnFiltrar);

        //caja = findViewById(R.id.caja);

        //Conecta al servicio REST
        serviceUser = ConnectionRest.getConnecion().create(ServiceUser.class);

     //



        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boton filtrar
                cargaUsuarios();

            }
        });




    }

    void cargaUsuarios() {
        Call<List<User>> call = serviceUser.listausuarios();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                   if (response.isSuccessful()){
                       listaTotalUsuarios = response.body();
                       listaUser.clear();
                       listaUser.addAll(listaTotalUsuarios);
                       userAdapter.notifyDataSetChanged();
                   }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    //tarea

}