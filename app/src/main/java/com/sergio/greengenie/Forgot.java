package com.sergio.greengenie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
final String TAG="";
TextInputEditText correo;
Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        correo=findViewById(R.id.txt_email_forgor);
        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnEmail();
            }
        });
    }

    private void returnEmail(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress =correo.getText().toString().trim();;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Forgot.this,"Email sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Intent intent=new Intent(getApplicationContext(),LoginPage.class);
        startActivity(intent);
    }
}