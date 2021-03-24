package com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers;

import java.io.Serializable;

public class PhoneView implements Serializable {
    private String number;
    private String error;

    public PhoneView() {
    }

    public PhoneView(String value, String error) {
        this.number = value;
        this.error = error;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
