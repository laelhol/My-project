package com.example.lael.holamundo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lael on 21/03/2017.
 */

public class Login extends AppCompatActivity{
    public static final String key_username = "username";
    public static final String key_password = "password";
    String url = "http://roho.fitness/user/login";
    EditText etusername = (EditText) findViewById(R.id.ETUsuario);
    EditText etpassword = (EditText) findViewById(R.id.ETPassword);


    public void LoginActivity(){
        final String username = etusername.getText().toString().trim();
        final String password = etpassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("false"))
                        {
                            succesfulLogin();
                        } else {
                            Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(key_username, username);
                params.put(key_password, password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void succesfulLogin(){
        Intent intent = new Intent(this, QueOnda.class);
        startActivity(intent);
    }


}
