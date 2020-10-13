package com.example.buyer.Dashboard;

public class Productseller_Class {

    String id;
   public String used_status;
    String brandname;
    String modelname;
    String storage;
    String color;
    String price;
    String image;
    String is_ptaapproved;
    String brand_id;
    String discount;
    String warranty_type;
    String accidental_warranty;
    String sim;
    String used_mobile_salesman;
    String shop_name;
    String open_status,followers,rating,is_access;

    public Productseller_Class(){}
    public Productseller_Class(String id, String brandname, String modelname, String storage, String color, String price, String used_status, String is_pta_approved, String image,String seller_id,String seller_name,String brand_id,String model_id, String discount,String warranty_type,String accidental_warranty,String sim, String used_mobile_salesman, String shop_name, String open_status, String followers, String rating, String is_access) {
        this.rating=rating;
        this.is_access=is_access;
        this.followers=followers;
        this.brandname = brandname;
        this.modelname = modelname;
        this.price = price;
        this.storage=storage;
        this.color=color;
        this.id= id;
        this.used_status= used_status;
        this.is_ptaapproved=is_pta_approved;
        this.image=image;
        this.seller_id=seller_id;
        this.seller_name=seller_name;
        this.brand_id=brand_id;
        this.model_id=model_id;
        this.discount=discount;
        this.warranty_type=warranty_type;
        this.accidental_warranty=accidental_warranty;
        this.sim=sim;
        this.used_mobile_salesman=used_mobile_salesman;
        this.shop_name=shop_name;
        this.open_status=open_status;

    }

    public String getWarranty_type() {
        return warranty_type;
    }

    public void setWarranty_type(String warranty_type) {
        this.warranty_type = warranty_type;
    }

    public String getAccidental_warranty() {
        return accidental_warranty;
    }

    public void setAccidental_warranty(String accidental_warranty) {
        this.accidental_warranty = accidental_warranty;
    }

    public String getSim() {
        return sim;
    }

    public String getIs_access() {
        return is_access;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getUsed_mobile_salesman() {
        return used_mobile_salesman;
    }

    public void setUsed_mobile_salesman(String used_mobile_salesman) {
        this.used_mobile_salesman = used_mobile_salesman;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getFollowers() {
        return followers;
    }

    public String getRating() {
        return rating;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getOpen_status() {
        return open_status;
    }

    public void setOpen_status(String open_status) {
        this.open_status = open_status;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    String model_id;
    public void setImage(String image) {
        this.image = image;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    String seller_id;
    String seller_name;

    public String getIs_ptaapproved() {
        return is_ptaapproved;
    }

    public void setIs_ptaapproved(String is_ptaapproved) {
        this.is_ptaapproved = is_ptaapproved;
    }

    public String getImage() {
        return image;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsed_status() {
        return used_status;
    }

    public void setUsed_status(String used_status) {
        this.used_status = used_status;
    }




    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
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

    public String getPrice() {
        return price;
    }

//    public String getAcc_name() {
//        return acc_name;
//    }

    public void setPrice(String price) {
        this.price = price;
    }
}
