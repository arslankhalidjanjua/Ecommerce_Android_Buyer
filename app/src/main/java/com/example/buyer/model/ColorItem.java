package com.example.buyer.model;


public class ColorItem {

    private String colorName;
    private String imageUrl,brandNam;

    public ColorItem(String color,String image) {
        colorName = color;
        imageUrl=image;
    }

    public String getColorName() {
        return colorName;
    }

    public String getBrandName() {
        return brandNam;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

