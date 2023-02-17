package com.sergio.greengenie.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergio.greengenie.Bill;
import com.sergio.greengenie.UI.Main.PageViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.sergio.greengenie.R;

import java.util.ArrayList;
import androidx.lifecycle.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page3 extends Fragment {
    public static BarChart graphic;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PageViewModel mViewModel;
    TextView waterdata1,waterdata2,lightdata1,lightdata2,gasdata1,gasdata2,fueldata1,petroldata1;
   // TextView[] edittexts=new TextView[8];
    TextView[] edittexts={   waterdata1, waterdata2,lightdata1,lightdata2,gasdata1,gasdata2,fueldata1,petroldata1};

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
        edittexts[0] = view.findViewById(R.id.water_data1);
        edittexts[1] = view.findViewById(R.id.water_data2);
        edittexts[2] = view.findViewById(R.id.light_data1);
        edittexts[3] = view.findViewById(R.id.light_data2);
        edittexts[4] = view.findViewById(R.id.gas_data1);
        edittexts[5]= view.findViewById(R.id.gas_data2);
        edittexts[6] = view.findViewById(R.id.petrol_data1);
        edittexts[7] = view.findViewById(R.id.petrol_data2);

        graphic = (BarChart) view.findViewById(R.id.graphic);
        //  Graphic.chart(graphic);

        mViewModel.getBills().observe(getViewLifecycleOwner(), new Observer<ArrayList<Bill>>() {
            @Override
            public void onChanged(ArrayList<Bill> bills) {
                // Obtener el último elemento del ArrayList
                try{
                Bill bill = mViewModel.getBills().getValue().get(mViewModel.getBills().getValue().size() - 1);
                edittexts[0].setText(bill.getWater() + "");
                edittexts[1].setText(bill.getLight() + "");
                edittexts[2].setText(bill.getGas() + "");
                edittexts[3].setText(bill.getPetrol() + "");
                edittexts[4].setText(bill.getWater2() + "");
                edittexts[5].setText(bill.getLight2() + "");
                edittexts[6].setText(bill.getGas2() + "");
                edittexts[7].setText(bill.getPetrol2() + "");
            }  catch(java.lang.ArrayIndexOutOfBoundsException e){
                    Log.d("dsds","size:"+(mViewModel.getBills().getValue().size() - 1));}
                }
        });
        return view;

    }


}