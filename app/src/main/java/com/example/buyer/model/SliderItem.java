package com.example.buyer.model;

public class SliderItem {

    private String description;
    private String imageUrl,brandNam;

    public SliderItem(String brand,String image,String brandName) {
        description = brand;
        imageUrl=image;
        brandNam=brandName;
    }

    public String getDescription() {
        return description;
    }

    public String getBrandName() {
        return brandNam;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
