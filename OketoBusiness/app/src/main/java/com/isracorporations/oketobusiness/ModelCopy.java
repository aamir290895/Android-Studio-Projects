package com.isracorporations.oketobusiness;

public class ModelCopy {
    String costPrice,crossed,date,description,group,image,item,key,pin,price,quantity,subGroup,time,weight;

    public ModelCopy(String costPrice, String crossed, String date, String description, String group, String image, String item, String key, String pin, String price, String quantity, String subGroup, String time, String weight) {
        this.costPrice = costPrice;
        this.crossed = crossed;
        this.date = date;
        this.description = description;
        this.group = group;
        this.image = image;
        this.item = item;
        this.key = key;
        this.pin = pin;
        this.price = price;
        this.quantity = quantity;
        this.subGroup = subGroup;
        this.time = time;
        this.weight = weight;
    }

    public ModelCopy() {
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getCrossed() {
        return crossed;
    }

    public void setCrossed(String crossed) {
        this.crossed = crossed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
