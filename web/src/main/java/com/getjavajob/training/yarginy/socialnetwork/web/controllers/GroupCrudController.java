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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.GROUP;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
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
    public ModelAndView showCreate(HttpSession session) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_CREATE_VIEW);
        return updater.getModelAndView(new Group(), GROUP_CREATE_VIEW);
    }

    @PostMapping("/create")
    public ModelAndView performCreation(HttpServletRequest req, HttpSession session, @ModelAttribute Group group,
                                        @SessionAttribute("user") Account owner) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_CREATE_VIEW);
        if ("cancel".equals(req.getParameter("save"))) {
            return updater.acceptActionOrRetry(true, null);
        }

        if (group.getPhoto().length != 0) {
            session.setAttribute(PHOTO, group.getPhoto());
        } else {
            group.setPhoto((byte[]) session.getAttribute(PHOTO));
        }

        updater.addAttribute(GROUP, group);

        group.setOwner(owner);
        return createGroup(updater, group);
    }

    private ModelAndView createGroup(GroupFieldsUpdater updater, Group group) {
        boolean created;
        try {
            created = groupService.createGroup(group);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, group);
        }
        return updater.acceptActionOrRetry(created, groupService.get(group));
    }

    @GetMapping("/update")
    public ModelAndView showUpdate(@RequestAttribute("id") long requestedId, HttpSession session) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_UPDATE_VIEW);
        //select at first visit
        Group group = groupService.get(requestedId);

        //save to session if wasn't
        if (isNull(session.getAttribute(GROUP))) {
            session.setAttribute(GROUP, group);
        }
        return updater.getModelAndView(group, GROUP_UPDATE_VIEW);
    }

    @PostMapping("/update")
    public ModelAndView performUpdate(HttpSession session, @ModelAttribute Group group,
                                      @RequestParam(value = "save", required = false) String save,
                                      @RequestAttribute("id") long requestedId) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_UPDATE_VIEW);
        if ("cancel".equals(save)) {
            return updater.acceptActionOrRetry(true, null);
        }
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

    private ModelAndView updateGroup(GroupFieldsUpdater updater, Group group, Group storedGroup) {
        boolean updated;
        try {
            updated = groupService.updateGroup(group, storedGroup);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, group);
        }
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
