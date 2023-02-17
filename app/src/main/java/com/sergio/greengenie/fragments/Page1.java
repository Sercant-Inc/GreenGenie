package com.sergio.greengenie.Fragments;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergio.greengenie.R;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sergio.greengenie.PolicyActivity;
import com.sergio.greengenie.R;


    public class Page1 extends Fragment {


        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        ImageView imageGenie;

        public Page1() {
            // Required empty public constructor
        }

        public static Page1 newInstance(String param1, String param2) {

            Page1 fragment = new Page1();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);

            }



        }
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_page1, container, false);
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            // Get references to the ImageView and TextView
            ImageView imageView3 = view.findViewById(R.id.imageView3);
            final TextView textView = view.findViewById(R.id.textView);
            Resources res= getResources();
            String[] genieTips;
            genieTips= res.getStringArray(R.array.tips);
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.tip);
            // Set an OnClickListener on the ImageView
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change the text of the TextView
                    TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
                    shake.setDuration(500);
                    shake.setInterpolator(new CycleInterpolator(5));
                    imageView3.startAnimation(shake);
                    mediaPlayer.start();
                    vibrator.vibrate(500);
                    int random=(int)(Math.random()* genieTips.length);
                    textView.setText(genieTips[random]);

                }
            });

            return view;
        }
    }

