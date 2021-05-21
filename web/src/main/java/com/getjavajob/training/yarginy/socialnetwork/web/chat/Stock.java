package com.getjavajob.training.yarginy.socialnetwork.web.chat;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stock implements Serializable {
    public static final String DATE_FORMAT = "dd MMM yyyy HH:mm:ss";
    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private String code;
    private double price;
    private Date date = new Date();

    public Stock() {
    }

    public Stock(String code, double price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
