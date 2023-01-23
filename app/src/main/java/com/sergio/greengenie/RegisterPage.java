package com.sergio.greengenie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {
    private String TAG;
    Button button1;
    TextInputEditText nombre;

    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText c_password;
    FirebaseAuth mAuth;

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
        mAuth=FirebaseAuth.getInstance();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();


            }

        });
    }
    public void openMain(){
        Intent intent = new Intent(this,MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void createUser(){
        String vemail=email.getText().toString().trim();
        String vpassword=password.getText().toString().trim();
        String vc_password=c_password.getText().toString().trim();
        String vnombre=nombre.getText().toString().trim();

        if(TextUtils.isEmpty(vnombre)){
            nombre.setError("Name can not be empty");
            nombre.requestFocus();
        }
        else if(TextUtils.isEmpty(vemail)){
            email.setError("Email can not be empty");
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(vpassword)){
            password.setError("Password can not be empty");
            password.requestFocus();
        }else if(!vpassword.equals(vc_password)){
            password.setError("The passwords do not match");
            password.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(vemail,vpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        openMain();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterPage.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}