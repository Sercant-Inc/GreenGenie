package com.sergio.greengenie.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sergio.greengenie.LoginPage;
import com.sergio.greengenie.MainActivity;
import com.sergio.greengenie.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page2 extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE = 1;
    private static boolean selected_image = false;
    boolean estadoBoton;
    private String mParam1;
    private String mParam2;
    Uri imageUri;

    Button btnedit;
    ImageView foto_gallery;
    public Page2() {
        // Required empty public constructor
    }

    public static Page2 newInstance(String param1, String param2) {
        Page2 fragment = new Page2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        estadoBoton=true;


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2, container, false);

        // Get references to the ImageView and TextView
        Button btnlogout = view.findViewById(R.id.btnlogout);
        btnedit= view.findViewById(R.id.btnedit);
        estadoBoton= true;
        foto_gallery = (ImageView)view.findViewById(R.id.profileImage);
        if (!selected_image){
        Glide.with(this).load(R.drawable.geniosinfondo_page2).circleCrop().into(foto_gallery);}
        else{
            Glide.with(this).load(imageUri).circleCrop().into(foto_gallery);
        }
        // Set an OnClickListener on the ImageView
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            //boolean = false;
            public void onClick(View v) {
                    if(estadoBoton){
                        btnedit.setText("SAVE");
                        estadoBoton= false;
                    }
                    else{
                        btnedit.setText("EDIT");
                        estadoBoton=true;
                    }

                }

        });



        foto_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }


        });









        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of the TextView
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);




            }
        });

        return view;


    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            //foto_gallery.setImageURI(imageUri);
            Glide.with(this).load(imageUri).circleCrop().into(foto_gallery);
            selected_image=true;
        }
    }







}
