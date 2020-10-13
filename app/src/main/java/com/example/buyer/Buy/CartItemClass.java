package com.example.buyer.Buy;

class CartItemClass {
    String id;

    String brandname;
    String modelname;
    String color;
    String storage;
    String price;
    String image;
    String is_ptaapproved;
    String seller_id;
    int quantity;

    public CartItemClass(String id, String brandname, String modelname, String color, String storage, String price, String image, String is_ptaapproved, int quantity) {
        this.id = id;
        this.brandname = brandname;
        this.modelname = modelname;
        this.color = color;
        this.storage = storage;
        this.price = price;
        this.image = image;
        this.is_ptaapproved = is_ptaapproved;
        this.quantity = quantity;

    }
    public CartItemClass(String id, String brandname, String modelname, String color, String storage, String price, String image, String is_ptaapproved, int quantity,String seller_id) {
        this.id = id;
        this.brandname = brandname;
        this.modelname = modelname;
        this.color = color;
        this.storage = storage;
        this.price = price;
        this.image = image;
        this.is_ptaapproved = is_ptaapproved;
        this.quantity = quantity;
        this.seller_id=seller_id;

    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getIs_ptaapproved() {
        return is_ptaapproved;
    }

    public void setIs_ptaapproved(String is_ptaapproved) {
        this.is_ptaapproved = is_ptaapproved;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
