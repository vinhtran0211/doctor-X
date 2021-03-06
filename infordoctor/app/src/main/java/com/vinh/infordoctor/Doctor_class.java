package com.vinh.infordoctor;

/**
 * Created by Vinh on 10-Apr-18.
 */

public class Doctor_class {
    String name;
    String avatar;
    String type;
    String address;
    String email;
    String phone;
    String DOB;
    String workat;
    String workinghour;
    String code;
    String specialist;
    int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    String gender;

    public Doctor_class() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getWorkat() {
        return workat;
    }

    public void setWorkat(String workat) {
        this.workat = workat;
    }

    public String getWorkinghour() {
        return workinghour;
    }

    public void setWorkinghour(String workinghour) {
        this.workinghour = workinghour;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Doctor_class(String name, String avatar, String type, String address, String email, String phone, String DOB, String workat, String workinghour, String code, String specialist) {
        this.name = name;
        this.avatar = avatar;
        this.type = type;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.DOB = DOB;
        this.workat = workat;
        this.workinghour = workinghour;
        this.code = code;
        this.specialist = specialist;
    }
}
