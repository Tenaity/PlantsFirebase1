package com.midterm.plantsfirebase1.Model;

public class Favorites {
    private String phone;
    private String plantId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public Favorites() {
    }

    public Favorites(String phone, String plantId) {
        this.phone = phone;
        this.plantId = plantId;
    }
}
