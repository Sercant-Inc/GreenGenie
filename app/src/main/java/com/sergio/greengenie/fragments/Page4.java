package com.sergio.greengenie.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.sergio.greengenie.Bill;
import com.sergio.greengenie.Graphic;
import com.sergio.greengenie.Fragments.Page3;

import com.sergio.greengenie.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Page4 extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Graphic graphic=new Graphic();
    Button btn_done, btn_delete, btn_edit, btn_cancel, btn_newForm;
    EditText water_billData, light_billData, gas_billData, petrol_billData, water_data2, light_data2, gas_data2, petrol_data2, house_billData, home_billData;
    EditText[] edittexts = {water_billData, light_billData, gas_billData, petrol_billData, water_data2, light_data2, gas_data2, petrol_data2, house_billData, home_billData};

    //public static ArrayList<Bill> bills = new ArrayList<Bill>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "TAG";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page4.
     */
    // TODO: Rename and change types and number of parameters
    public static Page4 newInstance(String param1, String param2) {
        Page4 fragment = new Page4();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        graphic.firebase(db);

        View view = inflater.inflate(R.layout.fragment_page4, container, false);
        btn_done = view.findViewById(R.id.btn_done);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_done.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);


        edittexts[0] = view.findViewById(R.id.water_billData);
        edittexts[1] = view.findViewById(R.id.light_billData);
        edittexts[2] = view.findViewById(R.id.gas_billData);
        edittexts[3] = view.findViewById(R.id.petrol_billData);
        edittexts[4] = view.findViewById(R.id.water_data2);
        edittexts[5] = view.findViewById(R.id.light_data2);
        edittexts[6] = view.findViewById(R.id.gas_data2);
        edittexts[7] = view.findViewById(R.id.petrol_data2);
        edittexts[8] = view.findViewById(R.id.house_billData);
        edittexts[9] = view.findViewById(R.id.home_billData);
        btn_newForm = view.findViewById(R.id.btn_newForm);


        for (int i = 0; i < edittexts.length; i++) {
            edittexts[i].setEnabled(false);
        }
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of the TextView
                createBill(view);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of the TextView
                for (int i = 0; i < edittexts.length; i++) {
                    edittexts[i].setEnabled(false);
                    edittexts[i].getText().clear();
                }
                visibility2();
            }
        });
        btn_newForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of the TextView
                visibility1();
                for (int i = 0; i < edittexts.length; i++) {
                    edittexts[i].setEnabled(true);

                }


            }
        });


        return view;
    }

    public void createBill(View view) {
//        for (int i=0;i< edittexts.length;i++) {
//            edittexts[i].getText().toString().trim();
//        }
        String water = edittexts[0].getText().toString().trim();
        String light = edittexts[1].getText().toString().trim();
        String gas = edittexts[2].getText().toString().trim();
        String petrol = edittexts[3].getText().toString().trim();
        String water2 = edittexts[4].getText().toString().trim();
        String light2 = edittexts[5].getText().toString().trim();
        String gas2 = edittexts[6].getText().toString().trim();
        String petrol2 = edittexts[7].getText().toString().trim();
        String house = edittexts[8].getText().toString().trim();
        String home = edittexts[9].getText().toString().trim();
        try {
             firebase(new Bill(Float.parseFloat(water), Float.parseFloat(light), Float.parseFloat(gas), Float.parseFloat(petrol), Float.parseFloat(water2), Float.parseFloat(light2), Float.parseFloat(gas2), Float.parseFloat(petrol2), Integer.parseInt(house), Float.parseFloat(home)));
           // firebase(new Bill((float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (int)Math.random()*20, (float)Math.random()*20));
            Toast toast0 = Toast.makeText(getActivity(), getString(R.string.createform), Toast.LENGTH_LONG);
            toast0.show();
           // graphic.chart(db);

            for (int i = 0; i < edittexts.length; i++) {
                edittexts[i].setEnabled(false);
                edittexts[i].getText().clear();
            }
            visibility2();

        } catch (Exception e) {
            Toast toast0 = Toast.makeText(getActivity(), getString(R.string.formerror), Toast.LENGTH_LONG);
            toast0.show();

        }


    }

    public void visibility1() {
        btn_done.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        btn_newForm.setVisibility(View.GONE);
    }

    public void visibility2() {
        btn_done.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        btn_edit.setVisibility(View.VISIBLE);
        btn_delete.setVisibility(View.VISIBLE);
        btn_newForm.setVisibility(View.VISIBLE);
    }

    public void firebase(Bill bill) {
        db.collection("bills").add(bill)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());

                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error adding document", e);
                });
        graphic.addBill(bill);
    }

}
