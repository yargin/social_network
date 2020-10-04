package com.getjavajob.training.yarginy.socialnetwork.web.servlets.additionaldata;

public class PhoneExchanger {
    private String name;
    private String value;
    private String error;

    public PhoneExchanger(String name, String value, String error) {
        this.name = name;
        this.value = value;
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "PhoneExchanger{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
