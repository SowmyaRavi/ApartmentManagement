package com.example.piyushravi.finalapartment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
    String flatNo;
    String message;
    String key;
    String from;

    public Notification(){

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification(String flatNo, String message,String from){
        this.flatNo=flatNo;
        this.from=from;
        this.message=message;

    }



}
