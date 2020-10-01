package com.getjavajob.training.yarginy.socialnetwork.common.exceptions;

public enum IncorrectData {
    PASSWORD_TOO_LONG("error.passwordTooLong"), PASSWORD_TOO_SHORT("error.passwordTooShort"),
    NOT_A_PASSWORD("error.notPassword"), WRONG_PASSWORD("error.wrongPassword"), WRONG_EMAIL("error.wrongEmail"),
    NOT_AN_EMAIL("error.notEmail"), NOT_A_PHONE("error.notPhone"), WRONG_STRING("error.wrongString"),
    TOO_YOUNG("error.tooYoung"), TOO_OLD("error.tooOld"), SAME_ADDITIONAL_EMAIL("error.sameAdditionalEmail");

    private final String propertyKey;

    IncorrectData(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }
}
