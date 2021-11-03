package com.isracorporations.oketobusiness;

public class Datalist {

    String name,des,priceOfProduct,productQuantity,currentDate,currentTime,randomKey,imageUrl;

    public Datalist(String name, String des, String priceOfProduct, String productQuantity, String currentDate, String currentTime, String randomKey, String imageUrl) {
        this.name = name;
        this.des = des;
        this.priceOfProduct = priceOfProduct;
        this.productQuantity = productQuantity;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.randomKey = randomKey;
        this.imageUrl = imageUrl;
    }

    public Datalist() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPriceOfProduct() {
        return priceOfProduct;
    }

    public void setPriceOfProduct(String priceOfProduct) {
        this.priceOfProduct = priceOfProduct;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
