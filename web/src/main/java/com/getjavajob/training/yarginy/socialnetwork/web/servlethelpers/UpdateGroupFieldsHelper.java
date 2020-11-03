package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

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
import static java.util.Objects.isNull;

public class UpdateGroupFieldsHelper extends UpdateFieldsHelper {
    public Group getOrCreateGroupAttribute(HttpServletRequest req, Supplier<Group> groupCreator) {
        Group group = (Group) req.getAttribute(Attributes.GROUP);
        if (isNull(group)) {
            group = groupCreator.get();
        }
        return group;
    }

    public void getValuesFromParams(HttpServletRequest req, Group group, ThreadLocal<Boolean> paramsAccepted)
            throws IOException, ServletException {
        setStringFromParam(group::setName, "name", req, paramsAccepted);
        setStringFromParam(group::setDescription, "description", req, paramsAccepted);
        setPhotoFromParam(group::setPhoto, "photo", req, paramsAccepted);
        if (isNull(group.getPhoto())) {
            group.setPhoto((byte[]) req.getSession().getAttribute("savedPhoto"));
        }
    }

    public void initGroupAttributes(HttpServletRequest req, Group group) {
        setAttribute(req, "name", group::getName);
        setAttribute(req, "description", group::getDescription);

        byte[] photoBytes = group.getPhoto();
        if (!isNull(photoBytes)) {
            String photo = Base64.getEncoder().encodeToString(photoBytes);
            req.setAttribute("photo", photo);
            req.getSession().setAttribute("savedPhoto", photoBytes);
        }
    }

    public void acceptActionOrRetry(HttpServletRequest req, HttpServletResponse resp, boolean updated,
                                    String successUrl, DoGetWrapper doGet) throws IOException, ServletException {
        if (updated) {
            HttpSession session = req.getSession();
            session.removeAttribute("savedPhoto");
            redirect(req, resp, successUrl);
        } else {
            doGet.accept(req, resp);
        }
    }

    public void acceptActionOrRetry(HttpServletRequest req, HttpServletResponse resp, boolean updated,
                                    String successUrl, DoGetWrapper doGet, String param, String value) throws
            IOException, ServletException {
        String url = successUrl + '?' + param + '=' + value;
        acceptActionOrRetry(req, resp, updated, url, doGet);
    }
}
