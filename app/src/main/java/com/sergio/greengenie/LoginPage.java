package com.sergio.greengenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {
    Button button2;
    Button button1;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        textView2=findViewById(R.id.textView2);
        
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }

        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPolicy();
            }
        });
    }
    public void openRegister(){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }
    public void openMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }
    public void openPolicy(){

        Intent intent = new Intent(this, PolicyActivity.class);
        startActivity(intent);
    }
}