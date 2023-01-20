package com.sergio.greengenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

   }
    public void openRegister(View v){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }
    public void openMain(View v){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }
    public void openPolicy(View v){

        Intent intent = new Intent(this, PolicyActivity.class);
        startActivity(intent);
    }
    public void openForgot(View v){

        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }
}