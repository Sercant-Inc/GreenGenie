package com.sergio.greengenie.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sergio.greengenie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_page3, container, false);
        BarChart graphic = (BarChart) view.findViewById(R.id.graphic);
        int[] group1 = {2,4,7};
        int[] group2 = {3,5,8};
        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();
// fill the lists
        for(int i = 0; i < group1.length; i++) {
            entriesGroup1.add(new BarEntry(i, group1[i]));
            entriesGroup2.add(new BarEntry(i, group2[i]));
        }
        BarDataSet set1 = new BarDataSet(entriesGroup1, "Light");
        BarDataSet set2 = new BarDataSet(entriesGroup2, "Water");
        set1.setColor(Color.YELLOW);
        set2.setColor(Color.BLUE);
        set1.setDrawValues(false);
        set2.setDrawValues(false);
        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
// (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"
        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth); // set the width of each bar
        graphic.setData(data);
        graphic.setDrawGridBackground(false);
       graphic.getDescription().setEnabled(false);
        graphic.getAxisLeft().setDrawGridLines(false);
        graphic.getAxisRight().setDrawGridLines(false);
        graphic.getXAxis().setDrawGridLines(false);
        graphic.setDrawBorders(true);
       graphic.getXAxis().setAxisMaximum(3.1f);
       graphic.getXAxis().setAxisMinimum(0f);
        graphic.getXAxis().setCenterAxisLabels(true);
     //   graphic.getXAxis().setGranularity(1f);
       graphic.groupBars(0.f, groupSpace, barSpace); // perform the "explicit" grouping
             //   graphic.setFitBars(true); // make the x-axis fit exactly all bars
        graphic.invalidate(); // refresh


        return view;

    }
}