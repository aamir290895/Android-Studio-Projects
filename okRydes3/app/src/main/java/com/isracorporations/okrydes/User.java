package com.isracorporations.okrydes;

public class User {
    private  String name,email,city,address,pin,phone,uid;


    public User(String name, String email, String city, String address, String pin,String phone ) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.address = address;
        this.pin = pin;
        this.phone=phone;

    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
