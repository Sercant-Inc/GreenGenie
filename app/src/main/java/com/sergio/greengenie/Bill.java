package com.sergio.greengenie;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Bill {


    private  int index;
    private float water;
    private float light;
    private float gas;
    private float petrol;
    private float water2;
    private float light2;
    private float gas2;
    private float petrol2;
    private int house;
    private float home;
    private String uid;
    @ServerTimestamp
    private Date date;

    public Bill(float water, float light, float gas, float petrol, float water2, float light2, float gas2, float petrol2, int house, float home, String uid,int index) {
        this.water = water;
        this.light = light;
        this.gas = gas;
        this.petrol = petrol;
        this.water2 = water2;
        this.light2 = light2;
        this.gas2 = gas2;
        this.petrol2 = petrol2;
        this.house = house;
        this.home = home;
        this.uid = uid;
        this.index=index;
    }
    public Bill(float water, float light, float gas, float petrol, float water2, float light2, float gas2, float petrol2, String uid,int index) {
        this.water = water;
        this.light = light;
        this.gas = gas;
        this.petrol = petrol;
        this.water2 = water2;
        this.light2 = light2;
        this.gas2 = gas2;
        this.petrol2 = petrol2;
        this.uid = uid;
        this.index=index;
    }
    public Bill() {
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getDate() {
        return date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public float getHome() {
        return home;
    }

    public void setHome(float home) {
        this.home = home;
    }

    public float getGas() {
        return gas;
    }

    public void setGas(float gas) {
        this.gas = gas;
    }

    public float getPetrol() {
        return petrol;
    }

    public void setPetrol(float petrol) {
        this.petrol = petrol;
    }

    public float getWater2() {
        return water2;
    }

    public void setWater2(float water2) {
        this.water2 = water2;
    }

    public float getLight2() {
        return light2;
    }

    public void setLight2(float light2) {
        this.light2 = light2;
    }

    public float getGas2() {
        return gas2;
    }

    public void setGas2(float gas2) {
        this.gas2 = gas2;
    }

    public float getPetrol2() {
        return petrol2;
    }

    public void setPetrol2(float petrol2) {
        this.petrol2 = petrol2;
    }


}
