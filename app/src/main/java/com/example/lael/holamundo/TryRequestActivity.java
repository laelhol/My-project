package com.example.lael.holamundo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import com.android.volley.Request.Method;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TryRequestActivity extends AppCompatActivity {
    TextView Respuesta, Description, Name, Address, Phone, Fare, Webpage, Facebook, Open, Close, Gymid;
    Button RSbutton, RJObutton, Cbutton;
    ImageView logo;
    String url = "http://roho.fitness/getFreeDayGyms/1/5", urlRoho="http://roho.fitness", imageUrl, FImageUrl;
    private static String TAG = TryRequestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_request);
        RSbutton = (Button)findViewById(R.id.Brequest);
        RJObutton = (Button)findViewById(R.id.BRJsonOb);
        Cbutton = (Button)findViewById(R.id.Bclear);
        Respuesta = (TextView)findViewById(R.id.TVresponse);
        Description = (TextView)findViewById(R.id.TVdescription);
        Name = (TextView)findViewById(R.id.TVname);
        Address = (TextView)findViewById(R.id.TVaddress);
        Phone = (TextView)findViewById(R.id.TVphone);
        Fare = (TextView)findViewById(R.id.TVfare);
        Webpage = (TextView)findViewById(R.id.TVwebpage);
        Facebook = (TextView)findViewById(R.id.TVfacebook);
        Open = (TextView)findViewById(R.id.TVopen);
        Close = (TextView)findViewById(R.id.TVclose);
        Gymid = (TextView)findViewById(R.id.TVgymid);
        logo = (ImageView)findViewById(R.id.IVicon);
        RSbutton.setOnClickListener(new View.OnClickListener()
        { @Override
            public void onClick(View v){
 //Resquest string
            //Inicializa queue al dar click en boton request
            final RequestQueue requestQueue = Volley.newRequestQueue(TryRequestActivity.this);
            //Realiza peticion string de la url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse (String response){
                            Respuesta.setText(response);
                            requestQueue.stop();
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Respuesta.setText("No se armo");
                    error.printStackTrace();
                    requestQueue.stop();
                }
            });
            requestQueue.add(stringRequest);
        }
        });
//Clear button
        Cbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Respuesta.setText(" ");
                Description.setText(" ");
                Name.setText(" ");
                Address.setText(" ");
                Phone.setText(" ");
                Fare.setText(" ");
                Webpage.setText(" ");
                Facebook.setText(" ");
                Open.setText(" ");
                Close.setText(" ");
                Gymid.setText(" ");
                logo.setVisibility(View.INVISIBLE);
            }
        });
//Request JSON object button
        RJObutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjectRequest();
                }
            });
    }

    private void makeJsonObjectRequest(){
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                Log.d(TAG, response.toString());
                try {
                    //parsing json object
                    JSONArray data = response.getJSONArray("data");
                    /**for (int i = 0; i<data.length(); i++){
                        JSONObject tmp = data.getJSONObject(i);
                        Description.setText(tmp.getString("description"));
                        Name.setText(tmp.getString("name"));
                        Address.setText(tmp.getString("address"));
                        Phone.setText(tmp.getString("phone"));
                        Fare.setText(tmp.getString("fare"));
                        Webpage.setText(tmp.getString("web_page"));
                        Facebook.setText(tmp.getString("facebook"));
                        Open.setText(tmp.getString("open"));
                        Close.setText(tmp.getString("close"));
                        Gymid.setText(tmp.getString("gym_id"));
                        imageUrl=tmp.getString("logo");
                        FImageUrl=urlRoho+imageUrl;
                        makeImageRequest();
                        logo.setVisibility(View.VISIBLE);
                    }*/

                    JSONObject tmp = data.getJSONObject(0);

                    Description.setText(tmp.getString("description"));
                    Name.setText(tmp.getString("name"));
                    Address.setText(tmp.getString("address"));
                    Phone.setText(tmp.getString("phone"));
                    Fare.setText(tmp.getString("fare"));
                    Webpage.setText(tmp.getString("web_page"));
                    Facebook.setText(tmp.getString("facebook"));
                    Open.setText(tmp.getString("open"));
                    Close.setText(tmp.getString("close"));
                    Gymid.setText(tmp.getString("gym_id"));
                    imageUrl=tmp.getString("logo");
                    FImageUrl=urlRoho+imageUrl;
                    makeImageRequest();
                    logo.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("lael",e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "No se arma" + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }

    private void makeImageRequest(){
        ImageRequest imageRequest = new ImageRequest(FImageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        logo.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TryRequestActivity.this, "No se armó la imagen", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(imageRequest);
    }
}