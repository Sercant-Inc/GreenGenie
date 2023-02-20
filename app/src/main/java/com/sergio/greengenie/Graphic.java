package com.sergio.greengenie;


import android.util.Log;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class Graphic {
    private static final String TAG = "My App";

    public void chart(BarChart barChart, ArrayList<Bill> bills) {
        Log.d("MyApp", bills.size() + "graphic");
        // Declaración de arrays con los meses y los valores de cada grupo
        //List<String> months = new ArrayList<>(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        ArrayList<Float> water = new ArrayList<>();
        ArrayList<Float> light = new ArrayList<>();
        ArrayList<Float> gas = new ArrayList<>();
        ArrayList<Float> fuel = new ArrayList<>();
        for (Bill f : bills) {
            water.add(f.getWater());
            light.add(f.getLight());
            gas.add(f.getGas());
            fuel.add(f.getPetrol());
        }

        // Creación de las listas de entradas para cada grupo
        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();
        List<BarEntry> entriesGroup3 = new ArrayList<>();
        List<BarEntry> entriesGroup4 = new ArrayList<>();


// Rellenado de las listas con los valores de cada grupo
        for (int i = 0; i < water.size(); i++) {

            entriesGroup1.add(new BarEntry(i, water.get(i)));
            entriesGroup2.add(new BarEntry(i, light.get(i)));
            entriesGroup3.add(new BarEntry(i, gas.get(i)));
            entriesGroup4.add(new BarEntry(i, fuel.get(i)));

        }
        BarDataSet set1 = new BarDataSet(entriesGroup1, "Water");
        BarDataSet set2 = new BarDataSet(entriesGroup2, "Light");
        BarDataSet set3 = new BarDataSet(entriesGroup3, "Gas");
        BarDataSet set4 = new BarDataSet(entriesGroup4, "Fuel");
        set1.setColor(ContextCompat.getColor(barChart.getContext(), R.color.azul_claro));
        set2.setColor(ContextCompat.getColor(barChart.getContext(), R.color.yellow_chart));
        set3.setColor(ContextCompat.getColor(barChart.getContext(), R.color.red_chart));
        set4.setColor(ContextCompat.getColor(barChart.getContext(), R.color.gray_chart));
        set1.setDrawValues(false);//numeros encima de las barras
        set2.setDrawValues(false);
        set3.setDrawValues(false);
        set4.setDrawValues(false);


        float groupSpace = 0.16f;
        float barSpace = 0.01f; // x4 dataset
        float barWidth = 0.20f; // x4 dataset
        int groupCount = entriesGroup1.size();
        int groupsVisible = 4;
        //(barWidth+barSpace)*4+groupSpace=1


        BarData data = new BarData(set1, set2, set3, set4);
        data.setBarWidth(barWidth); // set the width of each bar
        barChart.setData(data);

        barChart.getDescription().setEnabled(false);


        YAxis axisLeft = barChart.getAxisLeft();
        // axisLeft.setDrawGridLines(false);//no horizontal lines
        axisLeft.setAxisMinimum(0f);//starts at 0
        barChart.getAxisRight().setEnabled(false);//no right axis
        barChart.setScaleYEnabled(false);//no vertical zoom


        XAxis xaxis = barChart.getXAxis();
        xaxis.setDrawGridLines(false);//no vertical lines
        xaxis.setValueFormatter(new IndexAxisValueFormatter(months));//x axis labels as months
        xaxis.setCenterAxisLabels(true);//first x label centered in first group
        xaxis.setGranularity(barChart.getBarData().getGroupWidth(groupSpace, barSpace));//x axis labels distance
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.setHighlightPerTapEnabled(false);
        barChart.setHighlightPerDragEnabled(false);
        //graphic.setScaleEnabled(false);//no zoom

        xaxis.setAxisMinimum(0f);////starts at 0
        barChart.groupBars(xaxis.getAxisMinimum(), groupSpace, barSpace); // perform the "explicit" grouping
        xaxis.setAxisMaximum(xaxis.getAxisMinimum() + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);//x axis size
        barChart.setVisibleXRangeMaximum(xaxis.getAxisMaximum() / groupCount * (Math.min(groupCount, groupsVisible)));//
        barChart.setVisibleXRangeMinimum(xaxis.getAxisMaximum() / (Math.min(groupCount, groupsVisible)));
        barChart.moveViewToX(barChart.getXAxis().getAxisMaximum() / groupCount * (groupCount - groupsVisible));

        barChart.invalidate(); // refresh

    }


}