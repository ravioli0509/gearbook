package com.example.gearbook_nawalage;

import java.util.Date;

public class Gear {
    // Main Gear Class
    long id;
    private String maker;
    private String description;
    private float price;
    private String comment;
    private Date date;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker){
        this.maker = maker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }
}
