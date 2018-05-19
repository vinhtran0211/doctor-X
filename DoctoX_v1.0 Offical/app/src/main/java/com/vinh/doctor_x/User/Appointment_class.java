package com.vinh.doctor_x.User;

/**
 * Created by nntd290897 on 5/18/18.
 */

public class Appointment_class {
    private String name_patient,phone_patient,time,address,type,avatar;
    private Double book_lat,book_log;



    private Double rating;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Appointment_class() {
    }

    public Appointment_class(String name_patient, String phone_patient, String time, String address, String type, String avatar, Double book_lat, Double book_log, Double rating) {
        this.name_patient = name_patient;
        this.phone_patient = phone_patient;
        this.time = time;
        this.address = address;
        this.type = type;
        this.avatar = avatar;
        this.book_lat = book_lat;
        this.book_log = book_log;
        this.rating = rating;
    }

    public String getName_patient() {
        return name_patient;
    }

    public void setName_patient(String name_patient) {
        this.name_patient = name_patient;
    }

    public String getPhone_patient() {
        return phone_patient;
    }

    public void setPhone_patient(String phone_patient) {
        this.phone_patient = phone_patient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Double getBook_lat() {
        return book_lat;
    }

    public void setBook_lat(Double book_lat) {
        this.book_lat = book_lat;
    }

    public Double getBook_log() {
        return book_log;
    }

    public void setBook_log(Double book_log) {
        this.book_log = book_log;
    }
}
