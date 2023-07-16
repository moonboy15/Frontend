package com.example.signuploginfirebase;

public class UserHelper {

    String userName,userEmail, age, height, weight, sickness;

    public UserHelper() {

    }

    public UserHelper(String userName,String userEmail, String age, String height, String weight, String sickness) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sickness = sickness;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }
}
