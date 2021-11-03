package com.isracorporations.oketomart;

public class DeliveryModel {
    private String address, name, phone,pinCode ,city,pid;

    public DeliveryModel(String address, String name, String phone ,String pinCode,String city,String pid) {
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.pinCode=pinCode;
        this.city =city;
        this.pid =pid;
    }

    public DeliveryModel() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}