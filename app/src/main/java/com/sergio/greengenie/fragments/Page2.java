package com.sergio.greengenie.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
    EditText txt_profileName,txt_profileEmail;
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorageRef;
    private FirebaseUser user;
    Button btnedit;
    Spinner spinner;
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
        txt_profileName=view.findViewById(R.id.txt_profileName);
        txt_profileEmail=view.findViewById(R.id.txt_profileEmail);
        spinner= view.findViewById(R.id.spinner);
        spinner.setSelection(1);
        spinner.setEnabled(false);
        txt_profileName.setEnabled(false);
        txt_profileEmail.setEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String nom=user.getDisplayName();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        txt_profileName.setText(nom);
        txt_profileEmail.setText(user.getEmail());

        // Get references to the ImageView and TextView
        Button btnlogout = view.findViewById(R.id.btnlogout);
        btnedit= view.findViewById(R.id.btnedit);
        estadoBoton= true;
        foto_gallery = (ImageView)view.findViewById(R.id.profileImage);

        getProfileImage();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString("profileImage", "");

        if (user.getPhotoUrl()!=null) {
            Glide.with(this).load(user.getPhotoUrl()).into(foto_gallery);
            //Glide.with(this).load(imageUrl).circleCrop().into(foto_gallery);
            selected_image=true;
        }else {
                Glide.with(this).load(R.drawable.geniosinfondo_page2).into(foto_gallery);
        } // Set an OnClickListener on the ImageView

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            //boolean = false;
            public void onClick(View v) {
                    if(estadoBoton){
                        btnedit.setText("SAVE");
                        txt_profileName.setEnabled(true);
                        spinner.setEnabled(true);
                        estadoBoton= false;
                    }
                    else{
                        btnedit.setText("EDIT");
                        txt_profileName.setEnabled(false);
                        spinner.setEnabled(false);
                        estadoBoton=true;
                    }

                }

        });




        foto_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnedit.getText().equals("SAVE")){
                    openGallery();
                }

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
            Uri imageUri = data.getData();
            //foto_gallery.setImageURI(imageUri);
            Glide.with(this).load(imageUri).into(foto_gallery);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference profileImageRef = storageRef.child("documents/users/profile_images/" + user.getUid() + "/profile_img.jpg");
            UploadTask uploadTask = profileImageRef.putFile(imageUri);
            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return profileImageRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    // Guardar la referencia a la imagen en el objeto firebaseUser
                    updateUserProfileWithImageUri(downloadUri);
                    Glide.with(this).load(downloadUri).into(foto_gallery);
                } else {
                    // Mostrar un mensaje de error si no se puede subir la imagen a Firebase
                    Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
                }
            });


            selected_image=true;
        }

    }
    private void getProfileImage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getPhotoUrl() !=null) {
            String imageUrl = user.getPhotoUrl().toString();
            Context context = getContext();
            SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profileImage", imageUrl);
            editor.apply();
        }
    }
    private void updateUserProfileWithImageUri(@Nullable Uri imageUri) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(imageUri).build();

        firebaseAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Profile photo updated.");
                Toast.makeText(getActivity(), "Profile photo updated", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Failed to update user profile.", task.getException());
                Toast.makeText(getActivity(), "Failed to update user profile", Toast.LENGTH_SHORT).show();
            }
        });
    }






}
