package com.example.buyer.Dashboard;

public class MobileClass {

    String menuid;
    String menuname;
    String menuprice;
    String menudescription;
    String menucategory;
    String menuimage;

    public String getMenucategory() {
        return menucategory;
    }

    public void setMenucategory(String menucategory) {
        this.menucategory = menucategory;
    }



    public String getMenuimage() {
        return menuimage;
    }

    public void setMenuimage(String menuimage) {
        this.menuimage = menuimage;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenuprice() {
        return menuprice;
    }

    public void setMenuprice(String menuprice) {
        this.menuprice = menuprice;
    }

    public String getMenudescription() {
        return menudescription;
    }

    public void setMenudescription(String menudescription) {
        this.menudescription = menudescription;
    }

    public MobileClass(String menuid, String menuname, String menuprice, String menucategory, String menudescription, String menuimage) {
        this.menuid = menuid;
        this.menuname = menuname;
        this.menuprice = menuprice;
        this.menudescription = menudescription;
        this.menuimage=menuimage;
        this.menucategory=menucategory;
    }

    public MobileClass(){}





}
