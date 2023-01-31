package com.sergio.greengenie.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sergio.greengenie.Bill;
import com.sergio.greengenie.Graphic;
import com.sergio.greengenie.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page4 extends Fragment {
    EditText water_billData, light_billData, gas_billData, petrol_billData;
    EditText water_data2, light_data2, gas_data2, petrol_data2;
    EditText[] edittexts ={ water_billData, light_billData, gas_billData, petrol_billData,water_data2, light_data2, gas_data2, petrol_data2};
    public static ArrayList<Bill> bills = new ArrayList<Bill>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        Button btn_done = view.findViewById(R.id.btn_done);
        water_billData = view.findViewById(R.id.water_billData);
        light_billData = view.findViewById(R.id.light_billData);
        gas_billData = view.findViewById(R.id.gas_billData);
        petrol_billData = view.findViewById(R.id.petrol_billData);
        water_data2 = view.findViewById(R.id.water_data2);
        light_data2 = view.findViewById(R.id.light_data2);
        gas_data2 = view.findViewById(R.id.gas_data2);
        petrol_data2 = view.findViewById(R.id.petrol_data2);
Button btn_newForm=view.findViewById(R.id.btn_newForm);

        water_billData.setEnabled(false);
        light_billData.setEnabled(false);
        gas_billData.setEnabled(false);
        petrol_billData.setEnabled(false);
        water_data2.setEnabled(false);
        light_data2.setEnabled(false);
        gas_data2.setEnabled(false);
        petrol_data2.setEnabled(false);
//        for (EditText e:edittexts) {
//            e.setEnabled(false);
//
//        }
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of the TextView
                createBill(view);
            }
        });
        btn_newForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of the TextView

//        for (EditText e:edittexts) {
//            e.setEnabled(true);
//
//        }
                water_billData.setEnabled(true);
                light_billData.setEnabled(true);
                gas_billData.setEnabled(true);
                petrol_billData.setEnabled(true);
                water_data2.setEnabled(true);
                light_data2.setEnabled(true);
                gas_data2.setEnabled(true);
                petrol_data2.setEnabled(true);

            }
        });


        return view;
    }

    public void createBill(View view) {
        String water = water_billData.getText().toString().trim();
        String light = light_billData.getText().toString().trim();
        String gas = gas_billData.getText().toString().trim();
        String petrol = petrol_billData.getText().toString().trim();
        String water2 = water_data2.getText().toString().trim();
        String light2 = light_data2.getText().toString().trim();
        String gas2 = gas_data2.getText().toString().trim();
        String petrol2 = petrol_data2.getText().toString().trim();
      /*  String vpassword=passwd.getText().toString().trim();
        if(TextUtils.isEmpty(vpassword) ){
            passwd.setError("Password can not be empty");
            passwd.requestFocus();
        }*/
    /*   if(TextUtils.isEmpty(water) ){
            water_billData.setError("Water can not be empty");
        }
       else if(TextUtils.isEmpty(light) ){
            light_billData.setError("Water can not be empty");
        }
        else if(TextUtils.isEmpty(gas) ){
            gas_billData.setError("Water can not be empty");
        }
        else if(TextUtils.isEmpty(petrol) ){
            petrol_billData.setError("Water can not be empty");
        }
       else if(TextUtils.isEmpty(water2) ){
            water_data2.setError("Water can not be empty");
        }
        else if(TextUtils.isEmpty(light2) ){
            light_data2.setError("Water can not be empty");
        }
        else if(TextUtils.isEmpty(gas2) ){
            gas_data2.setError("Water can not be empty");
        }
        else if(TextUtils.isEmpty(petrol2) ){
            petrol_data2.setError("Water can not be empty");
        }
        else {*/
        try {
            bills.add(new Bill(Integer.parseInt(water), Integer.parseInt(light), Integer.parseInt(gas), Integer.parseInt(petrol), Integer.parseInt(water2), Integer.parseInt(light2), Integer.parseInt(gas2), Integer.parseInt(petrol2)));
            Toast toast0 = Toast.makeText(getActivity(), "Form created", Toast.LENGTH_LONG);
            toast0.show();
            Graphic.chart(Page3.graphic);

//        for (EditText e:edittexts) {
//            e.setEnabled(false);
//            e.getText().clear();
//        }
            water_billData.setEnabled(false);
            light_billData.setEnabled(false);
            gas_billData.setEnabled(false);
            petrol_billData.setEnabled(false);
            water_data2.setEnabled(false);
            light_data2.setEnabled(false);
            gas_data2.setEnabled(false);
            petrol_data2.setEnabled(false);

            water_billData.getText().clear();
            light_billData.getText().clear();
            gas_billData.getText().clear();
            petrol_billData.getText().clear();
            water_data2.getText().clear();
            light_data2.getText().clear();
            gas_data2.getText().clear();
            petrol_data2.getText().clear();
        } catch (Exception e) {
            Toast toast0 = Toast.makeText(getActivity(), "Error creating form", Toast.LENGTH_LONG);
            toast0.show();
        }


        //  }


    }
}