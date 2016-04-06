package com.example.android.idontknow;

import java.io.Serializable;

/**
 * Created by aswin on 4/4/16.
 */
public class Transactions extends Item implements Serializable{

    private String name;
    private String pincode;
    private String address;
    private String landmark;
    private String City;
    private String phone;
    private String amount;
    private int nostars;
    private String transId;
    private String dateoftrans;

    public String getDateoftrans() {
        return dateoftrans;
    }

    public void setDateoftrans(String dateoftrans) {
        this.dateoftrans = dateoftrans;
    }

    public int getNostars() {
        return nostars;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransId() {
        return transId;
    }

    public void setNostars(int nostars) {
        this.nostars = nostars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getPincode() {
        return pincode;
    }

    public String getAddress() {
        return address;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getCity() {
        return City;
    }

    public String getPhone() {
        return phone;
    }

    public String getAmount() {
        return amount;
    }
}
