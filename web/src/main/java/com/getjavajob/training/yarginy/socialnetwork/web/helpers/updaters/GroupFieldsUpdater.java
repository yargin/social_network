package com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_WALL;
import static java.util.Objects.isNull;

public class GroupFieldsUpdater {
    private final DataHandler dataHandler = new DataHandler();
    private final HttpSession session;
    private final HttpServletRequest req;
    private final String updateFailView;
    private String updateSuccessUrl;

    public GroupFieldsUpdater(HttpServletRequest req, HttpSession session, String updateFailView) {
        this.session = session;
        this.req = req;
        this.updateFailView = updateFailView;
        //if empty - create, if not update
        Object requestedId = req.getAttribute(REQUESTED_ID);
        if (!isNull(requestedId)) {
            setSuccessUrl(GROUP_WALL, REQUESTED_ID, (long) requestedId);
        } else {
            updateSuccessUrl = GROUP_WALL;
        }
    }

    public void setSuccessUrl(String successUrl, String param, long value) {
        updateSuccessUrl = successUrl + '?' + param + '=' + value;
    }

    public String getView(Group group, String view) {
        req.setAttribute("group", group);

        byte[] photoBytes = group.getPhoto();
        if (!isNull(photoBytes)) {
            String photo = dataHandler.getHtmlPhoto(photoBytes);
            req.setAttribute(PHOTO, photo);
        }
        return view;
    }

    public String acceptActionOrRetry(boolean updated, Group group) {
        if (updated) {
            session.removeAttribute(GROUP);
            session.removeAttribute(PHOTO);
            return "redirect:" + updateSuccessUrl;
        }
        return getView(group, updateFailView);
    }

    public String handleInfoExceptions(IncorrectDataException e, Group group) {
        if (e.getType() == IncorrectData.GROUP_DUPLICATE) {
            req.setAttribute(NAME_DUPLICATE, e.getType().getPropertyKey());
        }
        return getView(group, updateFailView);
    }
}
