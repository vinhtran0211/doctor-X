package com.vinh.doctor_x.User;

/**
 * Created by nntd290897 on 4/23/18.
 */

public class Location_cr {
    double lat;
    double log;
    String name,mobile,address,key_user;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKey_user() {
        return key_user;
    }

    public void setKey_user(String key_user) {
        this.key_user = key_user;
    }

    public Location_cr(){

    }

    public Location_cr(double lat, double log, String name, String mobile, String address, String key_user) {
        this.lat = lat;
        this.log = log;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.key_user = key_user;
    }
}
