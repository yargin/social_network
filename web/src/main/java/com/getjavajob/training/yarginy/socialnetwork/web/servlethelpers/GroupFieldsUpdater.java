package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_WALL;
import static java.util.Objects.isNull;

public class GroupFieldsUpdater extends AbstractFieldsUpdater {
    private final DataHandler dataHandler = new DataHandler();
    private final HttpSession session;

    public GroupFieldsUpdater(HttpServletRequest req, HttpSession session) {
        super(req, GROUP_ID, GROUP_WALL);
        this.session = session;
    }

    public Group getOrCreate(Supplier<Group> groupCreator) {
        Group group = (Group) req.getAttribute(GROUP);
        if (isNull(group)) {
            group = groupCreator.get();
            //set group for view if it wasn't in post
            req.setAttribute(GROUP, group);
        }
        return group;
    }

    public void initAttributes(Group group) {
        setAttribute("name", group::getName);
        setAttribute("description", group::getDescription);

        byte[] photoBytes = group.getPhoto();
        if (!isNull(photoBytes)) {
            String photo = dataHandler.getHtmlPhoto(photoBytes);
            req.setAttribute(PHOTO, photo);
        }
    }

    public String acceptActionOrRetry(boolean updated, GetMethodWrapper doGet) {
        if (updated) {
            session.removeAttribute(GROUP);
            session.removeAttribute(PHOTO);
            return "redirect:" + updateSuccessUrl;
        }
        return doGet.performGet(req, session);
    }

    public String handleInfoExceptions(IncorrectDataException e, GetMethodWrapper doGet) {
        if (e.getType() == IncorrectData.GROUP_DUPLICATE) {
            req.setAttribute(NAME_DUPLICATE, e.getType().getPropertyKey());
        }
        return doGet.performGet(req, session);
    }
}
