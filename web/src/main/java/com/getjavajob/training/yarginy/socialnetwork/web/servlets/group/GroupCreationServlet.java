package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetPostServlet;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupCreationServlet extends AbstractGetPostServlet {
    @Autowired
    private GroupService groupService;

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        GroupFieldsUpdater updater = new GroupFieldsUpdater(req, resp);
//        Group group = updater.getOrCreateGroup(Group::new);
//        updater.initGroupAttributes(group);
//        req.getRequestDispatcher(Jsps.GROUP_CREATION).forward(req, resp);
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        GroupFieldsUpdater updater = new GroupFieldsUpdater(req, resp);
//        Group group = new Group();
//        updater.getValuesFromParams(group);
//        if (!isNull(group.getPhoto())) {
//            req.getSession().setAttribute(PHOTO, group.getPhoto());
//        } else if (!isNull(req.getSession().getAttribute(PHOTO))) {
//            group.setPhoto((byte[]) req.getSession().getAttribute(PHOTO));
//        }
//
//        boolean accepted = updater.isParamsAccepted();
//        req.setAttribute(GROUP, group);
//        if (accepted) {
//            group.setOwner((Account) req.getSession().getAttribute(USER));
//            createGroup(updater, group);
//        } else {
//            req.setAttribute(GROUP, group);
//            safeDoGet(req, resp);
//        }
    }

//    private void createGroup(GroupFieldsUpdater updater, Group group) throws IOException, ServletException {
//        boolean created;
//        try {
//            created = groupService.createGroup(group);
//        } catch (IncorrectDataException e) {
//            updater.handleInfoExceptions(e, this::safeDoGet);
//            return;
//        }
//        Group createdGroup = groupService.get(group);
//        updater.setSuccessUrl(Pages.GROUP_WALL, GROUP_ID, "" + createdGroup.getId());
//        updater.acceptActionOrRetry(created, this::safeDoGet);
//    }
}
