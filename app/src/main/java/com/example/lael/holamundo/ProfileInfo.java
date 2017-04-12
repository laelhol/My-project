package com.example.lael.holamundo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileInfo extends AppCompatActivity {
    ApiInterface apiService;
    TextView ID;
    TextView name;
    TextView mail;
    TextView height;
    TextView birthday;
    ImageView picture;
    String nameString;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        ID = (TextView)findViewById(R.id.TVid);
        name = (TextView)findViewById(R.id.TVname3);
        mail = (TextView)findViewById(R.id.TVemail);
        height = (TextView)findViewById(R.id.TVheight);
        birthday = (TextView)findViewById(R.id.TVbirthday);
        picture = (ImageView)findViewById(R.id.IVprofile);
        picture.setImageResource(R.drawable.tmnt);
        password = getIntent().getStringExtra("password");
        UserInfo();
    }

    public void ChangePass(View view){
        Intent intent = new Intent(this, ChangePassword.class);
        intent.putExtra("nameString",nameString);
        intent.putExtra("password",password);
        startActivity(intent);
        //startActivity(new Intent(ProfileInfo.this,ChangePassword.class));
    }

    public void Payments(View view){
        Toast.makeText(getBaseContext(), "Pagos", Toast.LENGTH_LONG).show();
    }

    public void Charges(View view){
        Toast.makeText(getBaseContext(), "Cargos", Toast.LENGTH_LONG).show();
    }

    public void Suscriptions(View view){
        Toast.makeText(getBaseContext(), "Suscripciones", Toast.LENGTH_LONG).show();
    }

    public void edit(View view){
        Toast.makeText(getBaseContext(), "Editar", Toast.LENGTH_LONG).show();
    }

    public void wedit(View view){
        Toast.makeText(getBaseContext(), "Editar foto", Toast.LENGTH_LONG).show();
    }

    public void menuicon (View view){
        Toast.makeText(getBaseContext(), "(Deberia abrir un menu)", Toast.LENGTH_LONG).show();
    }

    public void UserInfo(){
        Call<Responses<UserData>> call = apiService.info();
        call.enqueue(new Callback<Responses<UserData>>() {
            @Override
            public void onResponse(Call<Responses<UserData>> call, Response<Responses<UserData>> response) {
                if (!response.body().getError()) {
                    ID.setText("ID: "+response.body().getData().getUser_id());
                    nameString = response.body().getData().getName()+" "+response.body().getData().getLast_name();
                    name.setText(nameString);
                    mail.setText(response.body().getData().getEmail());
                    height.setText(response.body().getData().getHeight()+" cm");
                    birthday.setText(response.body().getData().getBirthday());
                }
            }

            @Override
            public void onFailure(Call<Responses<UserData>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}