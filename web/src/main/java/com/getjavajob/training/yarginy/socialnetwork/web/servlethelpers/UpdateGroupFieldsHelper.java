package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.NAME_DUPLICATE;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.SAVED_PHOTO;
import static java.util.Objects.isNull;

public class UpdateGroupFieldsHelper extends UpdateFieldsHelper {
    public UpdateGroupFieldsHelper(HttpServletRequest req, HttpServletResponse resp, String idParam, String successUrl) {
        super(req, resp, idParam, successUrl);
    }

    public Group getOrCreateGroupAttribute(Supplier<Group> groupCreator) {
        Group group = (Group) req.getAttribute(Attributes.GROUP);
        if (isNull(group)) {
            group = groupCreator.get();
        }
        return group;
    }

    public void getValuesFromParams(Group group) throws IOException, ServletException {
        setStringFromParam(group::setName, "name");
        setStringFromParam(group::setDescription, "description");
        setPhotoFromParam(group::setPhoto, "photo");
        if (group.getPhoto() != null) {
            req.getSession().setAttribute(SAVED_PHOTO, group.getPhoto());
        } else {
            group.setPhoto((byte[]) req.getSession().getAttribute(SAVED_PHOTO));
        }
    }

    public void initGroupAttributes(Group group) {
        setAttribute("name", group::getName);
        setAttribute("description", group::getDescription);

        byte[] photoBytes = group.getPhoto();
        if (!isNull(photoBytes)) {
            String photo = Base64.getEncoder().encodeToString(photoBytes);
            req.setAttribute("photo", photo);
        }
    }

    public void acceptActionOrRetry(boolean updated, DoGetWrapper doGet) throws IOException, ServletException {
        if (updated) {
            HttpSession session = req.getSession();
            session.removeAttribute(SAVED_PHOTO);
            redirect(req, resp, updateSuccessUrl);
        } else {
            doGet.accept(req, resp);
        }
    }

    public void handleInfoExceptions(IncorrectDataException e, DoGetWrapper doGet) throws ServletException, IOException {
        if (e.getType() == IncorrectData.GROUP_DUPLICATE) {
            req.setAttribute(NAME_DUPLICATE, e.getType().getPropertyKey());
        }
        doGet.accept(req, resp);
    }
}
