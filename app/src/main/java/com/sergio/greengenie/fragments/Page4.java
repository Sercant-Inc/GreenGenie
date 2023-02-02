package com.sergio.greengenie.Fragments;

import android.os.Bundle;
import android.util.Log;
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
        edittexts[0] = view.findViewById(R.id.water_billData);
        edittexts[1]= view.findViewById(R.id.light_billData);
        edittexts[2]= view.findViewById(R.id.gas_billData);
        edittexts[3]= view.findViewById(R.id.petrol_billData);
        edittexts[4]= view.findViewById(R.id.water_data2);
        edittexts[5]= view.findViewById(R.id.light_data2);
        edittexts[6]= view.findViewById(R.id.gas_data2);
        edittexts[7]= view.findViewById(R.id.petrol_data2);
Button btn_newForm=view.findViewById(R.id.btn_newForm);

        if(bills.size()!=0){
        Graphic.chart(Page3.graphic);}
        for (int i=0;i< edittexts.length;i++){
            edittexts[i].setEnabled(false);
        }
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

        for (int i=0;i< edittexts.length;i++) {
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

        try {
            bills.add(new Bill(Integer.parseInt(water), Integer.parseInt(light), Integer.parseInt(gas), Integer.parseInt(petrol), Integer.parseInt(water2), Integer.parseInt(light2), Integer.parseInt(gas2), Integer.parseInt(petrol2)));
            Toast toast0 = Toast.makeText(getActivity(), "Form created", Toast.LENGTH_LONG);
            toast0.show();
            Graphic.chart(Page3.graphic);
            for (int i=0;i< edittexts.length;i++) {
                edittexts[i].setEnabled(false);
                edittexts[i].getText().clear();
            }
        } catch (Exception e) {
            Toast toast0 = Toast.makeText(getActivity(), "Error creating form", Toast.LENGTH_LONG);
            toast0.show();

        }


        //  }


    }
}
