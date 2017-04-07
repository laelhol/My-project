package com.example.lael.holamundo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GymListRetrofit extends AppCompatActivity {
    ApiInterface apiService;
    GymAdapterRetrofit adapter;
    private GymPostRetrofit gym_data;
    List<GymPostRetrofit> GYMS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_list_recyclerview);
        retrofitrequest();
    }


    public void retrofitrequest() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.GymListRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<Responses<List<GymPostRetrofit>>> call = apiService.allGyms();
        call.enqueue(new Callback<Responses<List<GymPostRetrofit>>>() {
            @Override
            public void onResponse(Call<Responses<List<GymPostRetrofit>>> call, retrofit2.Response<Responses<List<GymPostRetrofit>>> response) {

                for (int i = 0; i < response.body().getData().size(); i++){
                    GymPostRetrofit temp = response.body().getData().get(i);
                    GymPostRetrofit gyms = new GymPostRetrofit();

                    gyms.setAddress(temp.getAddress());
                    gyms.setDescription(temp.getDescription());
                    gyms.setClose(temp.getClose());
                    gyms.setOpen(temp.getOpen());
                    gyms.setPhone(temp.getPhone());
                    gyms.setName(temp.getName());
                    gyms.setLogo(temp.getLogo());

                    GYMS.add(gyms);
                }
                adapter = new GymAdapterRetrofit(GYMS, getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Responses<List<GymPostRetrofit>>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error gym list", Toast.LENGTH_LONG).show();
            }
        });
    }
}
