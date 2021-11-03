package com.isracorporations.ihubemploymentnews;

import android.widget.ImageView;

public class list {

   public  String header,link,image;

    public list() {
    }

    public list(String header, String link, String image) {
        this.header = header;
        this.link = link;
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
