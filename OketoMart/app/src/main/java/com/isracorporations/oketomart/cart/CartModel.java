package com.isracorporations.oketomart.cart;

public class CartModel {
    private  String name ,price,quantity,totalPrice,pid,weight,multiple,costPrice,image;

    public CartModel() {
    }

    public CartModel(String name, String price ,String quantity,String totalPrice,String pid ,String weight ,String multiple,String costPrice,String image) {
        this.name = name;
        this.price = price;
        this.quantity=quantity;
        this.totalPrice = totalPrice;
        this.pid =pid;
        this.weight=weight;
        this.multiple=multiple;
        this.costPrice=costPrice;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
