package com.sergio.greengenie;

import static com.sergio.greengenie.Fragments.Page3.graphic;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sergio.greengenie.Fragments.Page4;
import com.sergio.greengenie.Fragments.Page3;

import java.util.ArrayList;
import java.util.List;

public class Graphic {
    private static final String TAG = "My App";
    ArrayList<Bill> bills = new ArrayList<>();
    // static BarChart graphic = Page3.graphic;

    public void firebase(FirebaseFirestore db) {
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
                                    chart();
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });
    }

    public void chart() {
        Log.d("Firestore", bills.size() + "");
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
        set1.setColor(ContextCompat.getColor(graphic.getContext(), R.color.azul_claro));
        set2.setColor(ContextCompat.getColor(graphic.getContext(), R.color.yellow_chart));
        set3.setColor(ContextCompat.getColor(graphic.getContext(), R.color.red_chart));
        set4.setColor(ContextCompat.getColor(graphic.getContext(), R.color.gray_chart));
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
        graphic.moveViewToX(graphic.getXAxis().getAxisMaximum() / groupCount * (groupCount - groupsVisible));

        graphic.invalidate(); // refresh

    }

    public void addBill(Bill bill) {
        bills.add(bill);
        chart();
    }
}