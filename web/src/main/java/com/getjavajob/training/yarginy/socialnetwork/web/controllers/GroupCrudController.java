package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.GroupDto;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters.GroupFieldsUpdater;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import com.getjavajob.training.yarginy.socialnetwork.web.validators.GroupValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Date;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.GROUP;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_CREATE_VIEW;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_UPDATE_VIEW;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/group")
public class GroupCrudController {
    private final GroupService groupService;
    private final GroupValidator groupValidator;

    public GroupCrudController(GroupService groupService, GroupValidator groupValidator) {
        this.groupService = groupService;
        this.groupValidator = groupValidator;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(HttpSession session) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_CREATE_VIEW);
        return updater.getModelAndView(new Group());
    }

    @PostMapping("/create")
    public ModelAndView performCreation(HttpSession session, @ModelAttribute("group") GroupDto groupDto,
                                        @RequestParam(value = "save", required = false) String save,
                                        BindingResult result) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_CREATE_VIEW);
        Group group = groupDto.getGroup();
        if ("cancel".equals(save)) {
            return updater.acceptActionOrRetry(true, null);
        }

        if (group.getPhoto().length != 0) {
            session.setAttribute(PHOTO, group.getPhoto());
        } else {
            group.setPhoto((byte[]) session.getAttribute(PHOTO));
        }

        groupValidator.validate(group, result);
        if (result.hasErrors()) {
            return updater.getModelAndView(group);
        }

        group.setOwner((Account) session.getAttribute("user"));
        return createGroup(updater, group, result);
    }

    private ModelAndView createGroup(GroupFieldsUpdater updater, Group group, BindingResult result) {
        boolean created;
        try {
            created = groupService.createGroup(group);
        } catch (IncorrectDataException e) {
            result.rejectValue("name", "error.duplicate");
            return updater.getModelAndView(group);
        }
        return updater.acceptActionOrRetry(created, groupService.get(group));
    }

    @GetMapping("/update")
    public ModelAndView showUpdate(@RequestAttribute("id") long requestedId, HttpSession session,
                                   HttpServletRequest request) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_UPDATE_VIEW);
        //select at first visit
        Group group = groupService.getFullInfo(requestedId);

        //save to session if wasn't
        if (isNull(session.getAttribute(GROUP))) {
            session.setAttribute(GROUP, group);
        }
        return updater.getModelAndView(group);
    }

    @PostMapping("/update")
    public ModelAndView performUpdate(HttpSession session, @ModelAttribute("group") GroupDto groupDto,
                                      @RequestParam(value = "save", required = false) String save,
                                      @RequestAttribute("id") long requestedId, HttpServletRequest request,
                                      BindingResult result) {
        GroupFieldsUpdater updater = new GroupFieldsUpdater(session, GROUP_UPDATE_VIEW);
        Group group = groupDto.getGroup();
        if ("cancel".equals(save)) {
            return updater.acceptActionOrRetry(true, group);
        }
        Group storedGroup = (Group) session.getAttribute(GROUP);

        //for further views
        group.setId(requestedId);
        if (group.getPhoto().length < 1) {
            group.setPhoto(storedGroup.getPhoto());
        } else {
            storedGroup.setPhoto(group.getPhoto());
        }
        group.setVersion(storedGroup.getVersion());

        groupValidator.validate(group, result);
        if (result.hasErrors()) {
            return updater.getModelAndView(group);
        }

        try {
            return updateGroup(updater, group, result);
        } catch (IllegalStateException e) {
            request.setAttribute("concurrentError", "error.concurrentError");
            return showUpdate(storedGroup.getId(), session, request);
        }
    }

    private ModelAndView updateGroup(GroupFieldsUpdater updater, Group group, BindingResult result) {
        boolean updated;
        try {
            updated = groupService.updateGroup(group);
        } catch (IncorrectDataException e) {
            result.rejectValue("name", "error.duplicate");
            return updater.getModelAndView(group);
        }
        return updater.acceptActionOrRetry(updated, group);
    }

    @GetMapping("/delete")
    public String deleteGroup(@RequestAttribute("id") long requestedId) {
        Group group = new Group();
        group.setId(requestedId);
        if (groupService.removeGroup(group)) {
            return REDIRECT + Pages.GROUPS;
        }
        return "error";
    }

    @InitBinder("group")
    public void registerCustomEditors(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
