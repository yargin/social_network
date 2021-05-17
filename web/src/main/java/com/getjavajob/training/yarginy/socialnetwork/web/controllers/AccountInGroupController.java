package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.RECEIVER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_MEMBERS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_MODERATORS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_REQUESTS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_WALL;

@Controller
@RequestMapping("/group")
public class AccountInGroupController {
    private final GroupService groupService;
    private final Redirector redirector;

    public AccountInGroupController(GroupService groupService, Redirector redirector) {
        this.groupService = groupService;
        this.redirector = redirector;
    }

    @PostMapping("/join")
    public String joinGroup(@RequestAttribute(REQUESTER_ID) long accountId,
                            @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.sendGroupRequest(accountId, groupId);
        return redirector.getMvcPathForRedirect(GROUP_WALL, groupId);
    }

    @PostMapping("/leave")
    public String leaveGroup(@RequestAttribute(REQUESTER_ID) long accountId,
                             @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.leaveGroup(accountId, groupId);
        return redirector.getMvcPathForRedirect(GROUP_WALL, groupId);
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
        return redirector.getMvcPathForRedirect(GROUP_REQUESTS, groupId);
    }

    @PostMapping("/moderators/add")
    public String addModerator(@RequestAttribute(REQUESTER_ID) long accountId,
                               @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.addModerator(accountId, groupId);
        return redirector.getMvcPathForRedirect(GROUP_MEMBERS, groupId);
    }

    @PostMapping("/moderators/remove")
    public String removeModerator(@RequestAttribute(REQUESTER_ID) long accountId,
                                  @RequestAttribute(RECEIVER_ID) long groupId) {
        groupService.removeModerator(accountId, groupId);
        return redirector.getMvcPathForRedirect(GROUP_MODERATORS, groupId);
    }
}
