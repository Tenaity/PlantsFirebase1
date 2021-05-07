package com.midterm.plantsfirebase1.Model;

public class User {
    private String name;
    private String Password;
    private String phone;
    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
    public String getPhone(){return phone;}
    public void setPhone(String phone){
        this.phone = phone;
    }
}
