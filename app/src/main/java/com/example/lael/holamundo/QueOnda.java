package com.example.lael.holamundo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;

public class QueOnda extends AppCompatActivity {
    String url = "http://roho.fitness/user/getclientprofile";
    ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_onda);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        userInfo();
    }

    public void TryRequestActivity (View view){
        Intent intent = new Intent(this, TryRequestActivity.class);
        startActivity(intent);
    }

    public void gatos()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
   }

    public void GymListRequest (View view){
      //  Intent intent = new Intent(this, GymListRequest.class);
      //  startActivity(intent);

        startActivity(new Intent(QueOnda.this,GymListRetrofit.class));
    }

    public void userInfo(){
        Call <Responses<UserData>> call = apiService.info();
        call.enqueue(new Callback<Responses<UserData>>() {
            @Override
            public void onResponse(Call<Responses<UserData>> call, retrofit2.Response<Responses<UserData>> response) {
                if (!response.body().getError()) {
                    String userinfo = response.body().getData().getName()+" "+
                            response.body().getData().getLast_name()+", "+
                            response.body().getData().getBirthday();
                    Toast.makeText(getBaseContext(), userinfo, Toast.LENGTH_LONG).show();
                }
                    else
                    Log.d("pato1", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<Responses<UserData>> call, Throwable t) {

                Log.d("pato", t.getCause().toString());
                Log.d("pato", Arrays.toString(t.getStackTrace()));
                Log.d("pato", t.getMessage());

            }
        });

    }
}
