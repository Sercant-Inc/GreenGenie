package com.sergio.greengenie.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sergio.greengenie.Bill;
import com.sergio.greengenie.Graphic;
import com.sergio.greengenie.UI.Main.PageViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.sergio.greengenie.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page3 extends Fragment {
    BarChart barChart;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Graphic graphic = new Graphic();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PageViewModel mViewModel;
    TextView[] BillView = new TextView[8];
    EditText[] GoalEdit = new EditText[4];
    TextView[] GoalView = new TextView[4];
    boolean pressedButton = false;

    public Page3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page3.
     */
    // TODO: Rename and change types and number of parameters
    public static Page3 newInstance(String param1, String param2) {
        Page3 fragment = new Page3();
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
        mViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page3, container, false);
        Log.d("My App", "Page3created");
        for (int x = 0; x < BillView.length; x++) {
            BillView[x] = new TextView(getContext());
        }
        for (int x = 0; x < GoalEdit.length; x++) {
            GoalEdit[x] = new EditText(getContext());
        }
        BillView[0] = view.findViewById(R.id.water_data1);
        BillView[1] = view.findViewById(R.id.water_data2);
        BillView[2] = view.findViewById(R.id.light_data1);
        BillView[3] = view.findViewById(R.id.light_data2);
        BillView[4] = view.findViewById(R.id.gas_data1);
        BillView[5] = view.findViewById(R.id.gas_data2);
        BillView[6] = view.findViewById(R.id.petrol_data1);
        BillView[7] = view.findViewById(R.id.petrol_data2);


        GoalEdit[0] = view.findViewById(R.id.water_dataobj1);
        GoalEdit[1] = view.findViewById(R.id.light_dataobj1);
        GoalEdit[2] = view.findViewById(R.id.gas_dataobj1);
        GoalEdit[3] = view.findViewById(R.id.petrol_dataobj1);


        GoalView[0] = view.findViewById(R.id.water_dataobj2);
        GoalView[1] = view.findViewById(R.id.light_dataobj2);
        GoalView[2] = view.findViewById(R.id.gas_dataobj2);
        GoalView[3] = view.findViewById(R.id.petrol_dataobj2);
        barChart = (BarChart) view.findViewById(R.id.graphic);

        mViewModel.getBills().observe(getViewLifecycleOwner(), bills -> {
            try {


                Log.d("My App", "size:" + (mViewModel.getBills().getValue().size()));
                graphic.chart(barChart, mViewModel.getBills().getValue());
                Bill bill = mViewModel.getBills().getValue().get(mViewModel.getBills().getValue().size() - 1);
                BillView[0].setText(bill.getWater() + "m³");
                BillView[1].setText(bill.getWater2() + "");
                BillView[2].setText(bill.getLight() + "kWh");
                BillView[3].setText(bill.getLight2() + "");
                BillView[4].setText(bill.getGas() + "kWh");
                BillView[5].setText(bill.getGas2() + "");
                BillView[6].setText(bill.getPetrol() + "L");
                BillView[7].setText(bill.getPetrol2() + "");
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                //   Log.d("My App","size:"+(mViewModel.getBills().getValue().size() - 1));
            }

        });
        mViewModel.getGoal().observe(getViewLifecycleOwner(), bill -> {

            try {
                float waterMult = Float.parseFloat(BillView[1].getText().toString().trim());
                float lightMult = Float.parseFloat(BillView[3].getText().toString().trim());
                float gasMult = Float.parseFloat(BillView[5].getText().toString().trim());
                float petrolMult = Float.parseFloat(BillView[7].getText().toString().trim());
                Log.d("My App", "Water Mult " + waterMult);
                GoalView[0].setText((waterMult - (bill.getWater()/ 100 * waterMult)) + "€");
                GoalView[1].setText(lightMult - bill.getLight() / 100* lightMult + "€");
                GoalView[2].setText(gasMult - bill.getGas()/ 100 * gasMult + "€");
                GoalView[3].setText(petrolMult - bill.getPetrol() / 100* petrolMult + "€");
                GoalEdit[0].setText(bill.getWater()+"");
                GoalEdit[1].setText(bill.getLight()+"");
                GoalEdit[2].setText(bill.getGas()+"");
                GoalEdit[3].setText(bill.getPetrol()+"");


            } catch (Exception e) {
                Log.d("My App", "Water Mult " + BillView[0].getText());
            }
        });
        Button btn_fix = view.findViewById(R.id.btn_fix);


        btn_fix.setOnClickListener(v -> {
            Log.d("My App", pressedButton + "fixbutton");
            if (pressedButton) {
                try {

                    float water = Float.parseFloat(GoalEdit[0].getText().toString().trim()) ;
                    float light = Float.parseFloat(GoalEdit[1].getText().toString().trim()) ;
                    float gas = Float.parseFloat(GoalEdit[2].getText().toString().trim()) ;
                    float petrol = Float.parseFloat(GoalEdit[3].getText().toString().trim()) ;


                    Bill bill = new Bill(water, light, gas, petrol, FirebaseAuth.getInstance().getCurrentUser().getUid(), mViewModel.getGoalindex());


                    if (mViewModel.getGoal().getValue() != null) {
                        Log.d("My App", "update firebase" + mViewModel.getGoal().getValue().getIndex());
                        mViewModel.updateFirebase(bill);
                    } else {
                        mViewModel.addtofirebase(bill);
                    }

                    for (EditText e : GoalEdit) {
                        e.setEnabled(false);
                    }

                    pressedButton = false;


                    Toast.makeText(getActivity(), "Goal changed", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error changing goal", Toast.LENGTH_LONG).show();
                }
                btn_fix.setText("FIX OBJECTIVE");
            } else {
                for (EditText e : GoalEdit) {
                    e.setEnabled(true);
                }
                pressedButton = true;
                btn_fix.setText("SAVE");
            }
            //pressedButton = !pressedButton;
        });
        for (EditText e : GoalEdit) {
            e.setEnabled(false);
        }

        return view;

    }
}