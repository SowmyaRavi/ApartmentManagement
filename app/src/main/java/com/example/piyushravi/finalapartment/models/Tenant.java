package com.example.piyushravi.finalapartment.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Tenant{

    // Used for a query
    public static final String TENANT_KEY = "flatno";
    @JsonIgnore
    private String key;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String flatno;
    private String rent;

    // Required default constructor for Firebase object mapping
    public Tenant() {}

    // Used when creating from scratch
    public Tenant(String flatno, String firstName, String lastName, String email, String password,String rent) {
        this.flatno = flatno;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.rent=rent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "key='" + key + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rent='" + rent + '\'' +
                '}';
    }
}
