package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.RECEIVER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_MEMBERS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_MODERATORS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_REQUESTS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_VIEW;

@Controller
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final DataHandler dataHandler;
    private final GroupWallMessageService messageService;

    public GroupController(GroupService groupService, DataHandler dataHandler,
                           GroupWallMessageService messageService) {
        this.groupService = groupService;
        this.dataHandler = dataHandler;
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
        Group group = groupService.getFullInfo(id);
        addInfoAndPhoto(modelAndView, group);
        modelAndView.addObject(TAB, "moderators");
        return modelAndView;
    }

    private void addInfoAndPhoto(ModelAndView modelAndView, Group group) {
        modelAndView.addObject(PHOTO, dataHandler.getHtmlPhoto(group.getPhoto()));
        modelAndView.addObject("group", group);
    }
}
