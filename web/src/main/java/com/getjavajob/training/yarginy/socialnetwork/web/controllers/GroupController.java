package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_VIEW;

@Controller
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final DataHandler dataHandler;
    private final MessageService messageService;

    @Autowired
    public GroupController(GroupService groupService, DataHandler dataHandler,
                           @Qualifier("groupWallMessageService") MessageService messageService) {
        this.groupService = groupService;
        this.dataHandler = dataHandler;
        this.messageService = messageService;
    }

    @GetMapping("/wall")
    public ModelAndView getGroupWall(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Collection<Message> messages = messageService.selectMessages(id);
        modelAndView.addObject("messages", messages);
        addInfoAndPhoto(modelAndView, id);
        modelAndView.addObject("type", "groupWall");
        modelAndView.addObject("id", id);
        modelAndView.addObject(TAB, "wall");
        return modelAndView;
    }

    @GetMapping("/requests")
    public ModelAndView getGroupRequests(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Collection<Account> requesters = groupService.getGroupRequests(id);
        modelAndView.addObject("requesters", requesters);
        addInfoAndPhoto(modelAndView, id);
        modelAndView.addObject(TAB, "requests");
        return modelAndView;
    }

    @GetMapping("/members")
    public ModelAndView getGroupMembers(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Map<Account, Boolean> members = groupService.getGroupMembersModerators(id);
        modelAndView.addObject("members", members);
        addInfoAndPhoto(modelAndView, id);
        modelAndView.addObject(TAB, "members");
        return modelAndView;
    }

    @PostMapping("/join")
    public String joinGroup(@RequestAttribute(REQUESTER_ID) long accountId,
                            @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.sendGroupRequest(accountId, groupId);
        return REDIRECT + GROUP_WALL;
    }

    @PostMapping("/leave")
    public String leaveGroup(@RequestAttribute(REQUESTER_ID) long accountId,
                             @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.leaveGroup(accountId, groupId);
        return REDIRECT + GROUP_WALL;
    }

    @PostMapping("/accept")
    public String acceptInGroup(@RequestAttribute(REQUESTER_ID) long accountId,
                                @RequestAttribute(RECEIVER_ID) long groupId,
                                @RequestParam(required = false) String accept) {
        if ("true".equals(accept)) {
            groupService.acceptRequest(accountId, groupId);
        } else {
            groupService.declineRequest(accountId, groupId);
        }
        return REDIRECT + GROUP_REQUESTS;
    }

    @PostMapping("/moderators/add")
    public String addModerator(@RequestAttribute(REQUESTER_ID) long accountId,
                               @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.addModerator(accountId, groupId);
        return REDIRECT + GROUP_MEMBERS;
    }

    @PostMapping("/moderators/remove")
    public String removeModerator(@RequestAttribute(REQUESTER_ID) long accountId,
                                  @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.removeModerator(accountId, groupId);
        return REDIRECT + GROUP_MODERATORS;
    }

    @GetMapping("/moderators")
    public ModelAndView showModerators(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Collection<Account> moderators = groupService.getModerators(id);
        modelAndView.addObject("moderators", moderators);
        addInfoAndPhoto(modelAndView, id);
        modelAndView.addObject(TAB, "moderators");
        return modelAndView;
    }

    private void addInfoAndPhoto(ModelAndView modelAndView, long id) {
        Group group = groupService.get(id);
        modelAndView.addObject(PHOTO, dataHandler.getHtmlPhoto(group.getPhoto()));
        modelAndView.addObject("group", group);
    }
}
