package com.example.cured;

public class MyMedicine {
    String medicine_title, medicine_dose, medicine_time, medicine_key;

    public MyMedicine(){

    }

    public MyMedicine(String medicine_title, String medicine_dose, String medicine_time, String medicine_key) {
        this.medicine_title = medicine_title;
        this.medicine_dose = medicine_dose;
        this.medicine_time = medicine_time;
        this.medicine_key = medicine_key;
    }

    public String getMedicine_key() {
        return medicine_key;
    }

    public void setMedicine_key(String medicine_key) {
        this.medicine_key = medicine_key;
    }

    public String getMedicine_title() {
        return medicine_title;
    }

    public void setMedicine_title(String medicine_title) {
        this.medicine_title = medicine_title;
    }

    public String getMedicine_dose() {
        return medicine_dose;
    }

    public void setMedicine_dose(String medicine_dose) {
        this.medicine_dose = medicine_dose;
    }

    public String getMedicine_time() {
        return medicine_time;
    }

    public void setMedicine_time(String medicine_time) {
        this.medicine_time = medicine_time;
    }
}
