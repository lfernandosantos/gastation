package lf.com.br.gasstations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.gcm.Task;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lf.com.br.gasstations.model.Busca;
import lf.com.br.gasstations.model.Data;
import lf.com.br.gasstations.model.GetPosto;
import lf.com.br.gasstations.model.Posto;
import lf.com.br.gasstations.model.PostoCatalog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private Button btnMaps;
    private ListView listaViewPostos;
    private List<Posto> postoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnMaps = (Button) findViewById(R.id.btnMaps);
        listaViewPostos = (ListView) findViewById(R.id.listView);

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("posto", (Serializable) postoList);
                startActivity(intent);
            }
        });
        postoList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostoService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();


        Log.i("JSON", "REFRESH OK!");
        String latitude = "-22.7675157";
        String longitude = "-43.4258355";

        PostoService jsonPostos = retrofit.create(PostoService.class);

        Call<PostoCatalog> listaRequest = jsonPostos.listaPostos(latitude,longitude);
        listaRequest.enqueue(new Callback<PostoCatalog>() {
            @Override
            public void onResponse(Call<PostoCatalog> call, Response<PostoCatalog> response) {
                if (!response.isSuccessful()){
                    Log.i("TAG","ERRO: "+response.code());

                }else {
                    PostoCatalog catalog = response.body();

                    Data data = catalog.data;

                    Log.i("JSON", "RESULT: "+String.valueOf(data.status));

                    for (Posto getPosto : data.postos){
                        Log.i("POSTO", "Nome: "+getPosto.nome);
                        Log.i("POSTO", "Nome: "+getPosto.latitude);
                        Log.i("POSTO", "Nome: "+getPosto.longitude);
                        Log.i("ESPACO", "--------------------");

                        postoList.add(getPosto);
                    }
                }
            }

            @Override
            public void onFailure(Call<PostoCatalog> call, Throwable t) {

            }
        });

        try {
            Thread.sleep(10000);
            ArrayAdapter<Posto> adapter = new ArrayAdapter<Posto>(this, android.R.layout.simple_list_item_1, postoList);
            listaViewPostos.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
