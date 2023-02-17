package com.sergio.greengenie.UI.Main;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.sergio.greengenie.Bill;

import java.util.ArrayList;


public class PageViewModel extends ViewModel {
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    //  private ArrayList<Bill> bills = new ArrayList<>();
    private MutableLiveData<ArrayList<Bill>> bills = new MutableLiveData<>();

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setBills(ArrayList<Bill> bill) {
        if (bill!=null){
        bills.setValue(bill);}
    }
    public void addBill(Bill bill) {

        bills.getValue().add(bill);
        bills.postValue( bills.getValue());
    }
    public void setBill(int i,Bill bill) {

        bills.getValue().set(i,bill);
        bills.postValue( bills.getValue());
    }
    public LiveData<ArrayList<Bill>> getBills() {

        return bills;
    }
}