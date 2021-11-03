package com.isracorporations.oketomart.ui.home;

public class Model {

    String item,price,image,key,subCategory,name,pin,quantity,weight,costPrice,description;

    public Model(String item, String price ,String image,String key,String subCategory,String name,String pin,String quantity ,String weight ,String costPrice, String description) {
        this.item = item;
        this.price = price;
        this.image = image;
        this.key=key;
        this.subCategory = subCategory;
        this.name= name;
        this.pin = pin;
        this.quantity=quantity;
        this.weight= weight;
        this.costPrice = costPrice;
        this.description= description;


    }

    public Model() {
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;


    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

