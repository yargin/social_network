package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.GROUP_MEMBERS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.GROUP_VIEW;

@Controller
@RequestMapping("/group/moderators")
public class GroupModeratorsController {
    private final GroupService groupService;
    private final DataHandleHelper dataHandleHelper;

    @Autowired
    public GroupModeratorsController(GroupService groupService, DataHandleHelper dataHandleHelper) {
        this.groupService = groupService;
        this.dataHandleHelper = dataHandleHelper;
    }

    @PostMapping("/add")
    public String addModerator(@RequestAttribute("requesterId") long accountId,
                               @RequestAttribute("receiverId") long groupId) {
        groupService.addModerator(accountId, groupId);
        return "redirect:" + GROUP_MEMBERS;
    }

    @PostMapping("/remove")
    public String removeModerator(@RequestAttribute("requesterId") long accountId,
                                  @RequestAttribute("receiverId") long groupId) {
        groupService.removeModerator(accountId, groupId);
        return "redirect:" + GROUP_MEMBERS;
    }

    @GetMapping("/show")
    public ModelAndView showModerators(@RequestAttribute long id) {
        ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
        Collection<Account> moderators = groupService.getModerators(id);
        modelAndView.addObject("moderators", moderators);
        Group group = groupService.get(id);
        modelAndView.addObject("group", group);
        modelAndView.addObject("photo", dataHandleHelper.getHtmlPhoto(group.getPhoto()));
        modelAndView.addObject("tab", "moderators");
        return modelAndView;
    }
}
