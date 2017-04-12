package com.example.lael.holamundo;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Array;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    ApiInterface apiService;
    EditText oldpass;
    EditText newPass;
    EditText newPass2;
    String strnewpass,strnewpass2,oldpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_change_password);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        oldpass = (EditText) findViewById(R.id.TVuser);
        newPass = (EditText) findViewById(R.id.ETpass);
        newPass2 = (EditText) findViewById(R.id.ETpass2);
    }

    public void changePass(View view) {
        oldpassword = oldpass.getText().toString();
        strnewpass = newPass.getText().toString();
        strnewpass2 = newPass2.getText().toString();

        if (strnewpass.equals(strnewpass2)) {
            Call<Responses<Array>> call = apiService.changepass(strnewpass, strnewpass2, oldpassword);
            call.enqueue(new Callback<Responses<Array>>() {
                @Override
                public void onResponse(Call<Responses<Array>> call, Response<Responses<Array>> response) {
                    if (!response.body().getError())
                        success();
                }

                @Override
                public void onFailure(Call<Responses<Array>> call, Throwable t) {
                    connectionError();
                }
            });
        }
        else{error();}
    }

    public void success(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog
                .setTitleText("Contraseña cambiada exitosamente")
                .setContentText("Se ha actualizado su contraseña")
                .show();
    }

    public void error(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog
                .setTitleText("Hubo un error")
                .setContentText("Por favor verificar la informacion")
                .show();
    }

    public void connectionError(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog
                .setTitleText("Hubo un error")
                .setContentText("Por favor intentelo de nuevo")
                .show();
    }

    public void backButton(View view){
        finish();
    }
}