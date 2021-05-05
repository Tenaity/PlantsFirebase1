package com.midterm.plantsfirebase1.Model;

public class Plant {
    String name,price,image,menuId;

    public Plant() {
    }

    public Plant(String name, String price, String image, String menuId) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
