package com.vinh.doctor_x.User;

/**
 * Created by Vinh on 07-Mar-18.
 */

public class Patient_class {
    public String name;
    public String email;
    public String phone;
    public String medicalFiles;
    public String status;
    public String address;
    public String birthday;
    public String avatar;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String type;
    public int gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMedicalFiles() {
        return medicalFiles;
    }

    public void setMedicalFiles(String medicalFiles) {
        this.medicalFiles = medicalFiles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Patient_class() {
    }

    public Patient_class(String name, String email, String phone, String medicalFiles, String status, String address, String birthday, String avatar, String type, int gender) {

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.medicalFiles = medicalFiles;
        this.status = status;
        this.address = address;
        this.birthday = birthday;
        this.avatar = avatar;
        this.type = type;
        this.gender = gender;
    }
}
