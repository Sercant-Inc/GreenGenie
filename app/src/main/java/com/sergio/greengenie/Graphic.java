package com.sergio.greengenie;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graphic {

    public static void chart(BarChart graphic) {
        // Declaración de arrays con los meses y los valores de cada grupo
        List<String> months = new ArrayList<>(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
        //String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        int[] group1 = {2, 6, 8, 6, 4, 5, 7, 6, 5};
        int[] group2 = {3, 5, 2, 4, 5, 5, 4, 5, 8};
        int[] group3 = {6, 1, 4, 4, 4, 3, 3, 6, 9};
        int[] group4 = {1, 3, 5, 6, 2, 4, 5, 1, 8, 5};
        // Creación de las listas de entradas para cada grupo
        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();
        List<BarEntry> entriesGroup3 = new ArrayList<>();
        List<BarEntry> entriesGroup4 = new ArrayList<>();


// Rellenado de las listas con los valores de cada grupo
        for (int i = 0; i < group1.length; i++) {

            entriesGroup1.add(new BarEntry(i, group1[i]));
            entriesGroup2.add(new BarEntry(i, group2[i]));
            entriesGroup3.add(new BarEntry(i, group3[i]));
            entriesGroup4.add(new BarEntry(i, group4[i]));

        }
        BarDataSet set1 = new BarDataSet(entriesGroup1, "Light");
        BarDataSet set2 = new BarDataSet(entriesGroup2, "Water");
        BarDataSet set3 = new BarDataSet(entriesGroup3, "Gas");
        BarDataSet set4 = new BarDataSet(entriesGroup4, "Fuel");

        set1.setColor(Color.YELLOW);
        set2.setColor(Color.BLUE);
        set3.setColor(Color.RED);
        set4.setColor(Color.GRAY);
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
        graphic.setData(data);

        graphic.getDescription().setEnabled(false);


        YAxis axisLeft = graphic.getAxisLeft();
        // axisLeft.setDrawGridLines(false);//no horizontal lines
        axisLeft.setAxisMinimum(0f);//starts at 0
        graphic.getAxisRight().setEnabled(false);//no right axis
        graphic.setScaleYEnabled(false);//no vertical zoom


        XAxis xaxis = graphic.getXAxis();
        xaxis.setDrawGridLines(false);//no vertical lines
        xaxis.setValueFormatter(new IndexAxisValueFormatter(months));//x axis labels as months
        xaxis.setCenterAxisLabels(true);//first x label centered in first group
        xaxis.setGranularity(graphic.getBarData().getGroupWidth(groupSpace, barSpace));//x axis labels distance
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        graphic.setHighlightPerTapEnabled(false);
        graphic.setHighlightPerDragEnabled(false);
        //graphic.setScaleEnabled(false);//no zoom

        xaxis.setAxisMinimum(0f);////starts at 0
        graphic.groupBars(xaxis.getAxisMinimum(), groupSpace, barSpace); // perform the "explicit" grouping
        xaxis.setAxisMaximum(xaxis.getAxisMinimum() + graphic.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);//x axis size
        graphic.setVisibleXRangeMaximum(xaxis.getAxisMaximum() / groupCount * (Math.min(groupCount, groupsVisible)));//
        graphic.moveViewToX(graphic.getXAxis().getAxisMaximum() /groupCount* (groupCount - groupsVisible));

        graphic.invalidate(); // refresh

    }
}