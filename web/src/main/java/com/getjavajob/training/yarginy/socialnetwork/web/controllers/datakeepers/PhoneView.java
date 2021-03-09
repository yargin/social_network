package com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers;

public class PhoneView {
    private String value;
    private String error;

    public PhoneView(String value, String error) {
        this.value = value;
        this.error = error;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PhoneView{" +
                ", value='" + value + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
