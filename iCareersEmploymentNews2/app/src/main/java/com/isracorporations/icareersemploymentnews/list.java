package com.isracorporations.icareersemploymentnews;

public class list {
    public  String pre,mains,image,set,link;

    public list() {
    }

    public list(String pre, String mains, String image, String set,String link) {
        this.pre = pre;
        this.mains = mains;
        this.image = image;
        this.set = set;
        this.link = link;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getMains() {
        return mains;
    }

    public void setMains(String mains) {
        this.mains = mains;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
