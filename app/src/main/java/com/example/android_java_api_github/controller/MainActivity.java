package com.example.android_java_api_github.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_java_api_github.ItemAdapter;
import com.example.android_java_api_github.R;
import com.example.android_java_api_github.api.Client;
import com.example.android_java_api_github.api.Service;
import com.example.android_java_api_github.model.Item;
import com.example.android_java_api_github.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView Disconnected;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.BLUE);
        swipeContainer.setOnRefreshListener((new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(MainActivity.this, "Lista Atualizada", Toast.LENGTH_SHORT).show();
            }

        }));
    }

    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Buscando Usuários");
        pd.setCancelable(false);
        pd.show();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON() {
        Disconnected = findViewById(R.id.disconnected);
        try {
            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<ItemResponse> call = apiService.getItems();
            call.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                    List<Item> items = response.body().getItems();
                    recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Log.d("Erro", t.getMessage());
                    Toast.makeText(MainActivity.this, "Erro ao recuperar dados",
                            Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility((View.VISIBLE));
                    pd.hide();

                }
            });


        } catch (Exception e) {
            Log.d("Erro", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
