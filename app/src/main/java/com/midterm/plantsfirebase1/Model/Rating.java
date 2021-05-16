package com.midterm.plantsfirebase1.Model;

public class Rating {
    private String phone;
    private String plantId;
    private String rateValue;
    private String comment;

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

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rating() {
    }

    public Rating(String phone, String plantId, String rateValue, String comment) {
        this.phone = phone;
        this.plantId = plantId;
        this.rateValue = rateValue;
        this.comment = comment;
    }
}
