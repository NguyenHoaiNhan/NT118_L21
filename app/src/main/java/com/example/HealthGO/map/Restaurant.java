package com.example.HealthGO.map;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public String name;
    public String phone;
    public String servetime;
    public double lat;
    public double longi;
    List<String> lsDish;
    List<Integer> lsCost;

    public Restaurant(String name, String phone, String servetime, double lat, double longi, List<String> lsDish, List<Integer> lsCost) {
        this.name = name;
        this.phone = phone;
        this.servetime = servetime;
        this.lat = lat;
        this.longi = longi;
        this.lsDish = lsDish;
        this.lsCost = lsCost;
    }

    public Restaurant(String name, double lat, double longi) {
        this.name = name;
        this.lat = lat;
        this.longi = longi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServetime() {
        return servetime;
    }

    public void setServetime(String servetime) {
        this.servetime = servetime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public List<String> getLsDish() {
        return lsDish;
    }

    public void setLsDish(List<String> lsDish) {
        this.lsDish = lsDish;
    }

    public List<Integer> getLsCost() {
        return lsCost;
    }

    public void setLsCost(List<Integer> lsCost) {
        this.lsCost = lsCost;
    }

}
