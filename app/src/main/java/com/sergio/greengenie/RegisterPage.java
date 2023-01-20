package com.sergio.greengenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterPage extends AppCompatActivity {
    Button button1;
    TextInputEditText nombre;

    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText c_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        button1=findViewById(R.id.btnRegister);
        nombre=findViewById(R.id.txtname);
        email=findViewById(R.id.txtemail);
        password=findViewById(R.id.txtpassword);
        c_password=findViewById(R.id.txtpassword_confirm);
        Intent intent2 = getIntent();

    }
    public void openMain(View v){
        Intent intent = new Intent(this,MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}