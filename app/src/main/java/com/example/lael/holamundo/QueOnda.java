package com.example.lael.holamundo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QueOnda extends AppCompatActivity {
    String url = "http://roho.fitness/user/getclientprofile";
    TextView TVUsername,TVpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_onda);
        TVUsername = (TextView) findViewById(R.id.TVUsername);
        TVpass = (TextView) findViewById(R.id.TVpass);
        gatos();
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
        Intent intent = new Intent(this, GymListRequest.class);
        startActivity(intent);
    }
}