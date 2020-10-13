package com.example.buyer.Orders;

public class OrderClass {

    public  OrderClass(){

    }

    String orderid;
    String Totalamount;
    String deliverycharges;

    String price;
    String brandname;
    String modelname;
    String storage;
    String color;
    String ptaapproved;
    String order_status;

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTotalamount() {
        return Totalamount;
    }

    public void setTotalamount(String totalamount) {
        Totalamount = totalamount;
    }

    public String getDeliverycharges() {
        return deliverycharges;
    }

    public void setDeliverycharges(String deliverycharges) {
        this.deliverycharges = deliverycharges;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getPtaapproved() {
        return ptaapproved;
    }

    public void setPtaapproved(String ptaapproved) {
        this.ptaapproved = ptaapproved;
    }

    public OrderClass(String orderid, String totalamount, String deliverycharges, String price, String brandname, String modelname, String storage, String color, String ptaapproved,String order_status) {
        this.orderid = orderid;
        Totalamount = totalamount;
        this.deliverycharges = deliverycharges;
        this.price = price;
        this.brandname = brandname;
        this.modelname = modelname;
        this.storage = storage;
        this.color = color;
        this.ptaapproved = ptaapproved;
        this.order_status=order_status;
    }

}
