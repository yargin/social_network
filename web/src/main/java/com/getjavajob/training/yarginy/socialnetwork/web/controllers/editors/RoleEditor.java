package com.getjavajob.training.yarginy.socialnetwork.web.controllers.editors;

import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;

import java.beans.PropertyEditorSupport;

public class RoleEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        Role role = (Role) getValue();
        return role.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Role.valueOf(text));
    }
}
