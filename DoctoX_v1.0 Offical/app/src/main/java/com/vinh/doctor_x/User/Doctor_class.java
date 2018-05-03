package com.vinh.doctor_x.User;

/**
 * Created by Vinh on 10-Apr-18.
 */

public class Doctor_class {
    String name,avatar,type,locationcurrent;

    public Doctor_class() {
    }

    public String getName() {
        return name;
    }

    public String getLocationcurrent() {
        return locationcurrent;
    }

    public void setLocationcurrent(String locationcurrent) {
        this.locationcurrent = locationcurrent;
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

    public Doctor_class(String name, String avatar, String type) {

        this.name = name;
        this.avatar = avatar;
        this.type = type;
    }
}
