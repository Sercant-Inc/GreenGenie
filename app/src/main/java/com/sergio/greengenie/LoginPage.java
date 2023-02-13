package com.sergio.greengenie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity {
    private static final int RC_SIGN_IN = 2;
    Button b_google;
    Button login;
    TextInputEditText passwd;
    TextInputLayout tiuser;
    TextInputLayout tipasswd;
    TextInputEditText email;
    private FirebaseAuth mAuth;
    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;
    private String TAG;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView forgor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        b_google=findViewById(R.id.buttongoogle);
        mAuth = FirebaseAuth.getInstance();
        login=findViewById(R.id.login);
        passwd=findViewById(R.id.text_password);
        email=findViewById(R.id.txt_email);
        forgor=findViewById(R.id.txtforgot_password);
        tiuser=findViewById(R.id.username);
        tipasswd=findViewById(R.id.passwd);


        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("36455779828-o34ac5130bus2vjiq8c3sf2329egskv5.apps.googleusercontent.com")
                .requestEmail()
                .build();

        gsc=GoogleSignIn.getClient(this,gso);

        b_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toGoogle();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }


        });
        forgor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToForgor();
            }
        });

    }


    private void logIn() {
        String vemail=email.getText().toString().trim();
        String vpassword=passwd.getText().toString().trim();
        if(TextUtils.isEmpty(vpassword) ){
            tiuser.setError("Password can not be empty");
            passwd.requestFocus();
        }
        if(TextUtils.isEmpty(vemail) ){
            tiuser.setError("Email can not be empty");
            email.requestFocus();
        }
        else if(Patterns.EMAIL_ADDRESS.matcher(vemail).matches()){
            mAuth.signInWithEmailAndPassword(vemail, vpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                openMain();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            email.setError("Email is not in the right format");
            email.requestFocus();
        }


    }

    public void toGoogle(){
        Intent intg=gsc.getSignInIntent();
        startActivityForResult(intg,100);

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

        if(requestCode==100){
            try {
                GoogleSignInAccount cuenta=task.getResult(ApiException.class);
                firebaseGoogleAuth(cuenta);
            } catch (ApiException e) {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void firebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential credencial=GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user= mAuth.getCurrentUser();
                    openMain();
                }
            }
        });
    }
    private void HomeActivity() {
        finish();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    private void ToForgor(){
        Intent intent=new Intent(getApplicationContext(),ForgotActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

    public void openRegister(View v){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }
    public void openMain(){
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