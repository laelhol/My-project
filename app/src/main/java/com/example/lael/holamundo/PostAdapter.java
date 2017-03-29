package com.example.lael.holamundo;

/**
 * Created by Lael on 15/03/2017.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends ArrayAdapter{

    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    private static final String URL = "http://roho.fitness/getGyms/1/28", URLRoho="http://roho.fitness";
    private static final String TAG = "PostAdapter";
    List<GymPostActivity> items;

    public PostAdapter(Context context){
        super(context,0);

        //crear nuevo queue
        requestQueue=Volley.newRequestQueue(context);

        //peticion JsonObject
        jsArrayRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error de respuesta"+error.getMessage());
            }
        });
        requestQueue.add(jsArrayRequest);
    }

    @Override
    public int getCount() {
        return items !=null ? items.size():0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //referencia del view procesado
        View listItemView;

        //comprobar si el view no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.activity_gym_post, parent, false
        ): convertView;

        //obtener item actual
        GymPostActivity item = items.get(position);

        //obtener view
        TextView TVname2 = (TextView) listItemView.findViewById(R.id.TVname2);
        TextView TVdescription2 = (TextView) listItemView.findViewById(R.id.TVdescription2);
        TextView TVaddress2 = (TextView) listItemView.findViewById(R.id.TVaddress2);
        TextView TVphone2 = (TextView) listItemView.findViewById(R.id.TVphone2);
        TextView TVopen2 = (TextView) listItemView.findViewById(R.id.TVopen2);
        TextView TVclose2 = (TextView) listItemView.findViewById(R.id.TVclose2);
        final ImageView IVicon2 = (ImageView) listItemView.findViewById(R.id.IVicon2);

        //Actualizar views
        TVname2.setText(item.getName());
        TVdescription2.setText(item.getDescription());
        TVaddress2.setText(item.getAddress());
        TVphone2.setText(item.getPhone());
        TVopen2.setText(item.getOpen());
        TVclose2.setText(item.getClose());

        //peticion de imagen
        ImageRequest request = new ImageRequest(URLRoho + item.getLogo(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        IVicon2.setImageBitmap(bitmap);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        IVicon2.setImageResource(R.drawable.icono);
                        Log.d(TAG, "Error en imagen"+error.getMessage());
                    }
                });
        requestQueue.add(request);

        return listItemView;

    }

    public List<GymPostActivity> parseJson(JSONObject jsonObject){
        List<GymPostActivity> posts = new ArrayList<>();
        JSONArray jsonArray=null;

        try {
            jsonArray = jsonObject.getJSONArray("data");
            for (int i=0; i<jsonArray.length();i++)
            {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    GymPostActivity post = new GymPostActivity(
                           objeto.getString("name"),
                           objeto.getString("description"),
                           objeto.getString("address"),
                           objeto.getString("phone"),
                           objeto.getString("open"),
                           objeto.getString("close"),
                           objeto.getString("logo")
                    );
                    posts.add(post);
                } catch (JSONException e){
                    Log.e(TAG, "Error de parsing"+e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }
}