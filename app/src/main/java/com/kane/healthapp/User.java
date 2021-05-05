package com.kane.healthapp;

public class User {
    private String Username;
    private String Password;
    private String PhoneNum;
    private String FullName;


    public User(){}
    public User(String Username, String Password, String PhoneNo, String FullName) {
        this.Username = Username;
        this.Password = Password;
        this.PhoneNum = PhoneNo;
        this.FullName = FullName;
    }

    public String getUsername() {return Username;}
    public String getPassword() {return Password;}
    public String getPhoneNum() {return PhoneNum;}
    public String getFullName() {return FullName;}

    public void setUsername(String Username) {this.Username = Username;}
    public void setPassword(String Password) {this.Password = Password;}
    public void setPhoneNum(String PhoneNum) {this.Username = PhoneNum;}
    public void setFullName(String FullName) {this.Password = FullName;}
}
