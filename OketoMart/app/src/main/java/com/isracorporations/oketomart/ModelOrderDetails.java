package com.isracorporations.oketomart;

public class ModelOrderDetails {
    String date ,status,payment,upi,payee,orderId,pin,link,contact;

    public ModelOrderDetails(String date, String status, String payment,String upi ,String payee,String orderId,String pin,String link,String contact) {
        this.date = date;
        this.status = status;
        this.payment = payment;
        this.upi = upi;
        this.payee=payee;
        this.orderId=orderId;
        this.pin = pin;
        this.link = link;
        this.contact= contact;

    }

    public ModelOrderDetails() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
