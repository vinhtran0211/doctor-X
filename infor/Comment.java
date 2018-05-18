package com.vinh.doctor_x.User;

/**
 * Created by Vinh on 18-May-18.
 */

public class Comment
{
    String ten;
    String text;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    int rating;

    public Comment() {
    }

    public String getTen() {

        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment(String ten, String text, int rating) {
        this.ten = ten;
        this.text = text;
        this.rating = rating;
    }
}
