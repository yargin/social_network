package com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_WALL;
import static java.util.Objects.isNull;

public class GroupFieldsUpdater {
    private final HttpSession session;
    private final ModelAndView modelAndView;
    private final String updateFailView;
    private DataHandler dataHandler = new DataHandler();

    public GroupFieldsUpdater(HttpSession session, String updateFailView) {
        modelAndView = new ModelAndView();
        this.session = session;
        this.updateFailView = updateFailView;
    }

    @Autowired
    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public ModelAndView getModelAndView(Group group, String successView) {
        modelAndView.setViewName(successView);
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
            return new ModelAndView("redirect:" + GROUP_WALL + '?' + REQUESTED_ID + '=' + group.getId());
        }
        return getModelAndView(group, updateFailView);
    }

    public ModelAndView handleInfoExceptions(IncorrectDataException e, Group group) {
        if (e.getType() == IncorrectData.GROUP_DUPLICATE) {
            modelAndView.addObject(NAME_DUPLICATE, e.getType().getPropertyKey());
        }
        return getModelAndView(group, updateFailView);
    }

    public void addAttribute(String name, Object value) {
        modelAndView.addObject(name, value);
    }
}
