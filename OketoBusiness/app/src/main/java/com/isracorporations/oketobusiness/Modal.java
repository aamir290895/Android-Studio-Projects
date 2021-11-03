package com.isracorporations.oketobusiness;

public class Modal {
    private String pin, key, group, subGroup,item,price,image,crossed,quantity,costPrice;

    public Modal(String pin, String key, String group, String subGroup, String item, String price, String image,String crossed ,String quantity,String costPrice) {
        this.pin = pin;
        this.key = key;
        this.group = group;
        this.subGroup = subGroup;
        this.item = item;
        this.price = price;
        this.image = image;
        this.crossed=crossed;
        this.quantity=quantity;
        this.costPrice=costPrice;
    }

    public Modal() {
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
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

    public String getCrossed() {
        return crossed;
    }

    public void setCrossed(String crossed) {
        this.crossed = crossed;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
