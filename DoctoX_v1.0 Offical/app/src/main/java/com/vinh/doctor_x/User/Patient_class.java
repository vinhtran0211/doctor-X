package com.vinh.doctor_x.User;

/**
 * Created by Vinh on 07-Mar-18.
 */

public class Patient_class {
    public String name,email,phone,medicalFiles,status,address,birthday;
    public int gender,avatar;

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

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public Patient_class(String name, String email, String phone, String medicalFiles, String status, String address, String birthday, int gender, int avatar) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.medicalFiles = medicalFiles;
        this.status = status;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
        this.avatar = avatar;

    }

    public Patient_class() {
    }

}
