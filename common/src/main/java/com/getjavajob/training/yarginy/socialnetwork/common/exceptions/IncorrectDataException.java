package com.getjavajob.training.yarginy.socialnetwork.common.exceptions;

public class IncorrectDataException extends RuntimeException {
    private IncorrectData incorrectData;

    public IncorrectDataException(IncorrectData incorrectData) {
        this.incorrectData = incorrectData;
    }

    public IncorrectDataException(String message) {
        super(message);
    }

    public IncorrectData getType() {
        return incorrectData;
    }
}
