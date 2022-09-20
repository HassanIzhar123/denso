package com.tech.denso.Helper;

public class Const {

    public String getBaseUrl() {
        return BaseUrl;
    }

//    String BaseUrl = "http://45.153.48.165:8085";
    String BaseUrl = "https://djautoac.com";

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    static String Email ;
    static String password ;

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        Const.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        Const.lastname = lastname;
    }

    static String firstname;
    static String lastname;
}
