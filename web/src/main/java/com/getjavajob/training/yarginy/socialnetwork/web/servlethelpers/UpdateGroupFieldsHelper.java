package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class UpdateGroupFieldsHelper extends UpdateFieldsHelper {
    public Group getOrCreateGroupAttribute(HttpServletRequest req, Supplier<Group> groupCreator) {
        Group group = (Group) req.getAttribute(Attributes.GROUP);
        if (isNull(group)) {
            group = groupCreator.get();
//            req.setAttribute(Attributes.GROUP, group);
        }
        return group;
    }

    public void getValuesFromParams(HttpServletRequest req, Group group, ThreadLocal<Boolean> paramsAccepted)
            throws IOException, ServletException {
        setStringFromParam(group::setName, "name", req, paramsAccepted);
        setStringFromParam(group::setDescription, "description", req, paramsAccepted);
        setPhotoFromParam(group::setPhoto, "photo", req, paramsAccepted);
    }

    public void initGroupAttributes(HttpServletRequest req, Group group) {
        setAttribute(req, "name", group::getName);
        setAttribute(req, "surname", group::getDescription);

        if (!isNull(group.getPhoto())) {
            String photo = Base64.getEncoder().encodeToString(group.getPhoto());
            req.setAttribute("photo", photo);
        }
    }
}
