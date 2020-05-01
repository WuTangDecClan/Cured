package com.example.cured;

public class MyDiary {
    String diary_title, diary_desc, diary_date, diary_key;

    public MyDiary(){

    }

    public MyDiary(String diary_title, String diary_desc, String diary_date, String diary_key) {
        this.diary_title = diary_title;
        this.diary_desc = diary_desc;
        this.diary_date = diary_date;
        this.diary_key = diary_key;
    }

    public String getDiary_key() {
        return diary_key;
    }

    public void setDiary_key(String medicine_key) {
        this.diary_key = diary_key;
    }

    public String getDiary_title() {
        return diary_title;
    }

    public void setDiary_title(String diary_title) {
        this.diary_title = diary_title;
    }

    public String getDiary_desc() {
        return diary_desc;
    }

    public void setDiary_desc(String diary_desc) {
        this.diary_desc = diary_desc;
    }

    public String getDiary_date() {
        return diary_date;
    }

    public void setDiary_date(String diary_date) {
        this.diary_date = diary_date;
    }
}
