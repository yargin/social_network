package com.getjavajob.training.yarginy.socialnetwork.common.exceptions;

public enum DataFlowViolation {
    EMAIL_DUPLICATE(""), PHONE_DUPLICATE("");

    private final String propertyKey;

    DataFlowViolation(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }
}
