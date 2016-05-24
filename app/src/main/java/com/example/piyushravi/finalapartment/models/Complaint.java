package com.example.piyushravi.finalapartment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Complaint {

    String number;
    String message;
    String key;
    String reportType;

    public Complaint(){

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setNumber(String number) {
        this.number = number;

    }

    public Complaint(String number, String message,String reportType) {
        this.number = number;
        this.message = message;
        this.reportType=reportType;

    }

}
