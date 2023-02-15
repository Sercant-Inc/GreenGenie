package com.sergio.greengenie.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sergio.greengenie.Bill;
import com.sergio.greengenie.Graphic;

import com.sergio.greengenie.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Page4 extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Graphic graphic = new Graphic();
    Button btn_done, btn_edit, btn_cancel, btn_newForm;
    EditText water_billData, light_billData, gas_billData, petrol_billData, water_data2, light_data2, gas_data2, petrol_data2, house_billData, home_billData;
    EditText[] edittexts = {water_billData, light_billData, gas_billData, petrol_billData, water_data2, light_data2, gas_data2, petrol_data2, house_billData, home_billData};
    Spinner formSpinner;
    LinearLayout linearLayout;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    ArrayAdapter<CharSequence> adapter;
    //public static ArrayList<Bill> bills = new ArrayList<Bill>();
    boolean newform = true;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "TAG";
    private ArrayList<Bill> bills = new ArrayList<Bill>();
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

        View view = inflater.inflate(R.layout.fragment_page4, container, false);
        linearLayout = view.findViewById(R.id.linearLayout);

        formSpinner = (Spinner) view.findViewById(R.id.spinnerForm);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        formSpinner.setAdapter(adapter);
        formSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                try {

                    loadbill(bills.get(position));
                } catch (java.lang.IndexOutOfBoundsException e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadfirebase();
        btn_done = view.findViewById(R.id.btn_done);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_cancel = view.findViewById(R.id.btn_cancel);
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
                if (newform) {
                    createBill();
                    adapter.add(months[bills.size() - 1]);
                    adapter.notifyDataSetChanged();
                    formSpinner.setSelection(bills.size() - 1);
                    newform=false;
                }else{
                    createBill();
                    //editBill(bills.get(formSpinner.getSelectedItemPosition()));

                }
            }
        });
//        btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Change the text of the TextView
//
//                fieldenabled();
//            }
//        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of the TextView
                for (int i = 0; i < edittexts.length; i++) {
                    edittexts[i].setEnabled(false);
                    edittexts[i].getText().clear();
                }
                fielddisbled();
            }
        });
        btn_newForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldenabled();

                for (int i = 0; i < edittexts.length; i++) {
                    edittexts[i].getText().clear();
                    edittexts[i].setEnabled(true);
                }
            }
        });


        return view;
    }

    private void loadbill(Bill bill) {
        edittexts[0].setText(bill.getWater() + "");
        edittexts[1].setText(bill.getLight() + "");
        edittexts[2].setText(bill.getGas() + "");
        edittexts[3].setText(bill.getPetrol() + "");
        edittexts[4].setText(bill.getWater2() + "");
        edittexts[5].setText(bill.getLight2() + "");
        edittexts[6].setText(bill.getGas2() + "");
        edittexts[7].setText(bill.getPetrol2() + "");
        edittexts[8].setText(bill.getHouse() + "");
        edittexts[9].setText(bill.getHome() + "");

    }


    public void createBill() {

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
            if(newform) {
                addtofirebase(new Bill(Float.parseFloat(water), Float.parseFloat(light), Float.parseFloat(gas), Float.parseFloat(petrol), Float.parseFloat(water2), Float.parseFloat(light2), Float.parseFloat(gas2), Float.parseFloat(petrol2), Integer.parseInt(house), Float.parseFloat(home), FirebaseAuth.getInstance().getCurrentUser().getUid()));
                // firebase(new Bill((float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (int)Math.random()*20, (float)Math.random()*20), Float.parseFloat(home), FirebaseAuth.getInstance().getCurrentUser().getUid());
            }else{
               // updateBill();
            }
          Toast toast0 = Toast.makeText(getActivity(), getString(R.string.createform), Toast.LENGTH_LONG);
            toast0.show();
            // graphic.chart(db);
            for (int i = 0; i < edittexts.length; i++) {
                edittexts[i].setEnabled(false);
                edittexts[i].getText().clear();
            }
            fielddisbled();

        } catch (Exception e) {
            Toast toast0 = Toast.makeText(getActivity(), getString(R.string.formerror), Toast.LENGTH_LONG);
            toast0.show();
        }
    }

    public void fieldenabled() {
        btn_done.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.GONE);
        btn_newForm.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
    }

    public void fielddisbled() {
        btn_done.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        btn_edit.setVisibility(View.VISIBLE);
        btn_newForm.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    public void addtofirebase(Bill bill) {
        db.collection("bills").add(bill)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());

                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error adding document", e);
                });
        bills.add(bill);
        graphic.chart(bills);
    }

    public void loadfirebase() {
        bills.clear();
        db.collection("bills")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Bill bill = document.toObject(Bill.class);
                                bills.add(bill);
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                if (task.getResult().size() == bills.size()) {
                                    graphic.chart(bills);
                                    for (int x = 0; x <= bills.size() - 1; x++) {
                                        try {
                                            adapter.remove(adapter.getItem(x));
                                        } catch (java.lang.IndexOutOfBoundsException e) {

                                        }
                                        adapter.insert(months[x], x);
                                        formSpinner.setSelection(bills.size() - 1);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
}
