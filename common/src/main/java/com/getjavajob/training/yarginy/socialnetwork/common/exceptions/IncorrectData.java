package com.getjavajob.training.yarginy.socialnetwork.common.exceptions;

public enum IncorrectData {
    PASSWORD_TOO_LONG(), PASSWORD_TOO_SHORT(), NOT_A_PASSWORD(), WRONG_PASSWORD(), WRONG_EMAIL(), NOT_AN_EMAIL(),
    NOT_A_PHONE(), WRONG_STRING(), TOO_YOUNG(), TOO_OLD(), SAME_ADDITIONAL_EMAIL(), EMAIL_DUPLICATE(),
    PHONE_DUPLICATE(), FILE_TOO_LARGE(), UPLOADING_ERROR(), GROUP_DUPLICATE(), WRONG_REQUEST();

    IncorrectData() {
    }
}
