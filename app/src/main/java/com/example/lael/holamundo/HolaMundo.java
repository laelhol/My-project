package com.example.lael.holamundo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class HolaMundo extends AppCompatActivity {
    EditText etusername;
    EditText etpassword;
    Button baceptar;
    String url = "http://roho.fitness/user/login";
    String strusuario;
    String strpassword;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hola_mundo);
        etusername = (EditText) findViewById(R.id.ETUsuario);
        etpassword = (EditText) findViewById(R.id.ETPassword);
        baceptar = (Button) findViewById(R.id.BAceptar);
        CookieManager mCookieManager = new CookieManager();
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(mCookieManager);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
    }

    public void QueOnda(View view) {
        strusuario = etusername.getText().toString();
        strpassword = etpassword.getText().toString();
        loginRetrofit();
    }

    private void userLogin() {
        java.net.CookieManager CookieManager = new java.net.CookieManager();
        CookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(CookieManager);

        StringRequest stringRequest = new StringRequest(Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("gatit0",""+response);

                    JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    Intent intent = new Intent(getBaseContext(), QueOnda.class);
                    if (!error){
                        startActivity(intent);
                    }else{
                        Toast.makeText(getBaseContext(), "Error 2"+" "+response.toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), "Error"+" "+response.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Error string request",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map <String, String> params = new HashMap<String, String>();
                params.put("username",strusuario);
                params.put("password",strpassword);
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
      requestQueue1.add(stringRequest);
        Log.d("gatito",""+CookieManager.getCookieStore().getCookies());
    }

    private void loginRetrofit()
    {
        Call<Responses<Integer>> call = apiService.login(strusuario,strpassword);
        call.enqueue(new Callback<Responses<Integer>>() {
            @Override
            public void onResponse(Call<Responses<Integer>> call, retrofit2.Response<Responses<Integer>> response) {
                if (!response.body().getError()){
                    Intent intent = new Intent(getBaseContext(), QueOnda.class);
                    intent.putExtra("password",strpassword);
                    startActivity(intent);
                    //startActivity(new Intent(HolaMundo.this,QueOnda.class));
                }else{
                    Toast.makeText(getBaseContext(), "Error 2"+" "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Responses<Integer>> call, Throwable t) {
                error();
            }
        });
    }

    public void error ()
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog
               .setTitleText("No se arma")
               .setContentText("Checa bien la info morr@")
               .show();
    }
}