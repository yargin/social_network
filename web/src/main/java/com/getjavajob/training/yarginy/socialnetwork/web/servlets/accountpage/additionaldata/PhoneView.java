package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.additionaldata;

import java.io.Serializable;

/**
 * used for exchanging phones between servlets & jsps
 * contains param-name, phone & error
 */
public class PhoneView implements Serializable {
    private String paramName;
    private String value;
    private String error;

    public PhoneView(String paramName, String value, String error) {
        this.paramName = paramName;
        this.value = value;
        this.error = error;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String name) {
        this.paramName = name;
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
                "name='" + paramName + '\'' +
                ", value='" + value + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
