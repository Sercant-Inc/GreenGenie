package com.sergio.greengenie;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class Graphic {

    public static void chart(BarChart graphic)
        {   String[] months = {"Jan","", "Feb","", "Mar", "","Apr", "","May", "","Jun","", "Jul", "","Aug","", "Sep","", "Oct","", "Nov", "","Dec"};
            int[] group1 = {2,4,7};
            int[] group2 = {3,5,8};
            int[] group3 = {6,1,9};
            int[] group4= {1,3,5};
            List<BarEntry> entriesGroup1 = new ArrayList<>();
            List<BarEntry> entriesGroup2 = new ArrayList<>();
            List<BarEntry> entriesGroup3 = new ArrayList<>();
            List<BarEntry> entriesGroup4 = new ArrayList<>();
// fill the lists
            for(int i = 0; i < group1.length; i++) {
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
//            set1.setDrawValues(false);
//            set2.setDrawValues(false);
//            set3.setDrawValues(false);
//            set4.setDrawValues(false);
            float groupSpace = 0.32f;
            float barSpace = 0.02f; // x2 dataset
            float barWidth = 0.40f; // x2 dataset
// (0.02 + 0.4) * 2 + 0.32 = 2.00 -> interval per "group"
            BarData data = new BarData(set1, set2,set3,set4);

            int groupCount = entriesGroup1.size();
            data.setBarWidth(barWidth); // set the width of each bar
            graphic.setData(data);
            graphic.setDrawGridBackground(false);
            graphic.getDescription().setEnabled(false);
            graphic.getAxisLeft().setDrawGridLines(false);
            graphic.getAxisRight().setDrawGridLines(false);
            graphic.getXAxis().setDrawGridLines(false);
            graphic.setDrawBorders(true);
            graphic.getXAxis().setAxisMinimum(0f);
           graphic.getXAxis().setGranularity(2f);
            graphic.getAxisRight().setEnabled(false);
            graphic.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
         graphic.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));

          graphic.getXAxis().setAxisMaximum(0f+ graphic.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
         // graphic.getXAxis().setLabelCount(groupCount+1, true);

            graphic.getXAxis().setCenterAxisLabels(true);
            graphic.groupBars(0.f, groupSpace, barSpace); // perform the "explicit" grouping
             graphic.setFitBars(true); // make the x-axis fit exactly all bars

            graphic.invalidate(); // refresh

        }
}
