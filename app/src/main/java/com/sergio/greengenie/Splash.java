package com.sergio.greengenie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView genio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        genio=findViewById(R.id.geniosplash);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        genio.startAnimation(myanim);

    }
}