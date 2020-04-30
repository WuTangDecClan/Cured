package com.example.cured;

public class MyDiary {

    String titlediary, datediary, descdiary;

    public MyDiary() {
    }

    public MyDiary(String titlediary, String datediary, String descdiary) {
        this.titlediary = titlediary;
        this.datediary = datediary;
        this.descdiary = descdiary;
    }

    public String getTitlediary() {
        return titlediary;
    }

    public void setTitlediary(String titlediary) {
        this.titlediary = titlediary;
    }

    public String getDatediary() {
        return datediary;
    }

    public void setDatediary(String datediary) {
        this.datediary = datediary;
    }

    public String getDescdiary() {
        return descdiary;
    }

    public void setDescdiary(String descdiary) {
        this.descdiary = descdiary;
    }
}
