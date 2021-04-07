package com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.GROUP;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static java.util.Objects.isNull;

public class GroupFieldsUpdater {
    private final HttpSession session;
    private final ModelAndView modelAndView;
    private final String updateFailView;
    private final DataHandler dataHandler = new DataHandler();

    public GroupFieldsUpdater(HttpSession session, String updateFailView) {
        modelAndView = new ModelAndView();
        this.session = session;
        this.updateFailView = updateFailView;
    }

    public ModelAndView getModelAndView(Group group) {
        modelAndView.setViewName(updateFailView);
        modelAndView.addObject("group", group);
        byte[] photoBytes = group.getPhoto();
        if (!isNull(photoBytes)) {
            String photo = dataHandler.getHtmlPhoto(photoBytes);
            modelAndView.addObject(PHOTO, photo);
        }
        return modelAndView;
    }

    public ModelAndView acceptActionOrRetry(boolean updated, Group group) {
        if (updated) {
            session.removeAttribute(GROUP);
            session.removeAttribute(PHOTO);
            if (!isNull(group) && group.getId() > 0) {
                return new ModelAndView(REDIRECT + GROUP_WALL + '?' + REQUESTED_ID + '=' + group.getId());
            } else {
                return new ModelAndView(REDIRECT + Pages.GROUPS);
            }
        }
        return getModelAndView(group);
    }
}
