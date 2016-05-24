package com.example.piyushravi.finalapartment.models;

/**
 * Created by Piyush Ravi on 5/12/2016.
 */
public class Event {
    String date;
    String message;
    public static final String COURSE_KEY = "date";
    public Event(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Event(String date, String message){
        this.date=date;
        this.message=message;
    }
}
