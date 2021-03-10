package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters.GroupFieldsUpdater;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_CREATE_VIEW;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_UPDATE_VIEW;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/group")
public class GroupCrudController {
    private final GroupService groupService;

    @Autowired
    public GroupCrudController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/create")
    public String showCreate(HttpServletRequest req, HttpSession session) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(req, session, GROUP_CREATE_VIEW);
        Group group = new Group();
        return updater.getView(group, GROUP_CREATE_VIEW);
    }

    @PostMapping("/create")
    public String performCreation(HttpServletRequest req, HttpSession session, @ModelAttribute Group group,
                                  @SessionAttribute("user") Account owner) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(req, session, GROUP_CREATE_VIEW);
        if ("cancel".equals(req.getParameter("save"))) {
            return updater.acceptActionOrRetry(true, null);
        }

        if (group.getPhoto().length != 0) {
            session.setAttribute(PHOTO, group.getPhoto());
        } else {
            group.setPhoto((byte[]) session.getAttribute(PHOTO));
        }

        req.setAttribute(GROUP, group);

        group.setOwner(owner);
        return createGroup(updater, group);
    }

    private String createGroup(GroupFieldsUpdater updater, Group group) {
        boolean created;
        try {
            created = groupService.createGroup(group);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, group);
        }
        Group createdGroup = groupService.get(group);
        updater.setSuccessUrl(GROUP_WALL, GROUP_ID, createdGroup.getId());
        return updater.acceptActionOrRetry(created, group);
    }

    @GetMapping("/update")
    public String showUpdate(HttpServletRequest req, HttpSession session) {
        long requestedId = (long) req.getAttribute(REQUESTED_ID);

        GroupFieldsUpdater updater = new GroupFieldsUpdater(req, session, GROUP_UPDATE_VIEW);
        //select at first visit
        Group group = groupService.get(requestedId);

        //save to session if wasn't
        if (isNull(session.getAttribute(GROUP))) {
            session.setAttribute(GROUP, group);
        }
        return updater.getView(group, GROUP_UPDATE_VIEW);
    }

    @PostMapping("/update")
    public String performUpdate(HttpServletRequest req, HttpSession session, @ModelAttribute Group group) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(req, session, GROUP_UPDATE_VIEW);
        if ("cancel".equals(req.getParameter("save"))) {
            return updater.acceptActionOrRetry(true, null);
        }
        long requestedId = (long) req.getAttribute(REQUESTED_ID);
        Group storedGroup = (Group) session.getAttribute(GROUP);

        //for further views
        group.setId(requestedId);
        if (group.getPhoto().length < 1) {
            group.setPhoto(storedGroup.getPhoto());
        } else {
            storedGroup.setPhoto(group.getPhoto());
        }

        //for next view
        return updateGroup(updater, group, storedGroup);
    }

    private String updateGroup(GroupFieldsUpdater updater, Group group, Group storedGroup) {
        boolean updated;
        try {
            updated = groupService.updateGroup(group, storedGroup);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, group);
        }
        updater.setSuccessUrl(GROUP_WALL, GROUP_ID, group.getId());
        return updater.acceptActionOrRetry(updated, group);
    }

    @GetMapping("/delete")
    public String deleteGroup(@RequestAttribute("id") long requestedId) {
        Group group = new Group();
        group.setId(requestedId);
        if (groupService.removeGroup(group)) {
            return "redirect:" + Pages.GROUPS;
        }
        return "error";
    }

    @InitBinder("group")
    public void registerCustomEditors(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
