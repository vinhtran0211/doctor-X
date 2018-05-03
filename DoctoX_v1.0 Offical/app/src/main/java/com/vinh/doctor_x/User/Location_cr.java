package com.vinh.doctor_x.User;

/**
 * Created by nntd290897 on 4/23/18.
 */

public class Location_cr {
    double lat;
    double log;
    String title,city;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Location_cr(double lat, double log, String title, String city) {
        this.lat = lat;
        this.log = log;
        this.title = title;
        this.city = city;
    }

    public Location_cr() {

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

}
