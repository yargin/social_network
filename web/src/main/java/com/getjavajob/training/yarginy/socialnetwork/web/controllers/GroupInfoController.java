package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.DataConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_VIEW;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/group")
public class GroupInfoController {
    private final GroupService groupService;
    private final DataConverter dataConverter;
    private final GroupWallMessageService messageService;

    public GroupInfoController(GroupService groupService, DataConverter dataConverter,
                               GroupWallMessageService messageService) {
        this.groupService = groupService;
        this.dataConverter = dataConverter;
        this.messageService = messageService;
    }

    @GetMapping("/wall")
    public ModelAndView getGroupWall(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Group group = groupService.getFullInfo(id);
        Collection<GroupWallMessage> messages = messageService.selectMessages(id);
        modelAndView.addObject("messages", messages);
        addInfoAndPhoto(modelAndView, group);
        modelAndView.addObject("type", "group");
        modelAndView.addObject("id", id);
        modelAndView.addObject(TAB, "wall");
        return modelAndView;
    }

    @GetMapping("/requests")
    public ModelAndView getGroupRequests(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Collection<Account> requesters = groupService.getGroupRequests(id);
        modelAndView.addObject("requesters", requesters);
        Group group = groupService.getFullInfo(id);
        addInfoAndPhoto(modelAndView, group);
        modelAndView.addObject(TAB, "requests");
        return modelAndView;
    }

    @GetMapping("/members")
    public ModelAndView getGroupMembers(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Map<Account, Boolean> members = groupService.getGroupMembersModerators(id);
        modelAndView.addObject("members", members);
        Group group = groupService.getFullInfo(id);
        addInfoAndPhoto(modelAndView, group);
        modelAndView.addObject(TAB, "members");
        return modelAndView;
    }

    @GetMapping("/moderators")
    public ModelAndView showModerators(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Collection<Account> moderators = groupService.getModerators(id);
        modelAndView.addObject("moderators", moderators);
        Group group = groupService.getFullInfo(id);
        addInfoAndPhoto(modelAndView, group);
        modelAndView.addObject(TAB, "moderators");
        return modelAndView;
    }

    private void addInfoAndPhoto(ModelAndView modelAndView, Group group) {
        byte[] photo = group.getPhoto();
        if (!isNull(photo)) {
            modelAndView.addObject(PHOTO, dataConverter.getHtmlPhoto(photo));
        }
        modelAndView.addObject("group", group);
    }
}
