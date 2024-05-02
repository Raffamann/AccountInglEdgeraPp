package com.pluralsight;

public class transactions {
    private String date;
    private String description;
    private String vendor;
    private String time;
    private float amount;


    public transactions(String date, String description, String vendor, String time, Float amount) {
        this.date = date;
        this.description = description;
        this.vendor = vendor;
        this.time = time;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "transactions{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                "vendor='" + vendor + '\'' +
                ", time='" + time + '\'' +
                ", amount=" + amount +
                '}';
    }
}
