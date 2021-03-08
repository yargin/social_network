package com.getjavajob.training.yarginy.socialnetwork.web.controllers.editors;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;

import java.beans.PropertyEditorSupport;

public class SexEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        Sex sex = (Sex) getValue();
        return sex.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Sex.valueOf(text));
    }
}
