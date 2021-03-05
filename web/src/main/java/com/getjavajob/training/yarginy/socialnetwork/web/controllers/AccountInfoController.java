package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllerhelpers.AccountInfoHelper2;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.viewservlets.GroupsListServlet.ALL_GROUPS_LIST;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.GROUPS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.ACCOUNT_WALL_VIEW;

@Controller
@RequestMapping("/account")
public class AccountInfoController {
    private final AccountInfoHelper2 infoHelper;
    private final AccountService accountService;
    private final GroupService groupService;
    private final MessageService messageService;

    @Autowired
    public AccountInfoController(AccountInfoHelper2 infoHelper, AccountService accountService, GroupService groupService,
                                 @Qualifier("accountWallMessageService") MessageService messageService) {
        this.infoHelper = infoHelper;
        this.accountService = accountService;
        this.groupService = groupService;
        this.messageService = messageService;
    }

    @GetMapping("/wall")
    public ModelAndView showWall(@RequestParam(value = "id", required = false) Long requestedId,
                                 @SessionAttribute("userId") long sessionId) {
        long id = requestedId == null ? sessionId : requestedId;
        ModelAndView modelAndView = new ModelAndView(ACCOUNT_WALL_VIEW);
        infoHelper.setAccountInfo(modelAndView, id);
        Collection<Message> messages = messageService.selectMessages(id);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("type", "accountWall");
        modelAndView.addObject("id", id);
        modelAndView.addObject(TAB, "wall");
        return modelAndView;
    }

    @GetMapping("/requests")
    public ModelAndView getFriendshipRequests(@RequestParam(value = "id", required = false) Long requestedId,
                                              @SessionAttribute("userId") long sessionId) {
        long id = requestedId == null ? sessionId : requestedId;
        ModelAndView modelAndView = new ModelAndView("accountpages/friendshipRequestsList");

        Collection<Account> requesters = accountService.getFriendshipRequests(id);
        modelAndView.addObject("requesters", requesters);

        infoHelper.setAccountInfo(modelAndView, id);

        modelAndView.addObject(TAB, "friendshipRequestsList");
        return modelAndView;
    }

    @GetMapping("/friends")
    public ModelAndView getFriends(@RequestParam(value = "id", required = false) Long requestedId,
                                   @SessionAttribute("userId") long sessionId) {
        long id = requestedId == null ? sessionId : requestedId;
        ModelAndView modelAndView = new ModelAndView("accountpages/friendsList");

        modelAndView.addObject("friends", accountService.getFriends(id));

        infoHelper.setAccountInfo(modelAndView, id);
        modelAndView.addObject(TAB, "friendsList");
        return modelAndView;
    }

    @GetMapping("/dialogs")
    public ModelAndView getDialogs(@RequestParam(value = "id", required = false) Long requestedId,
                                   @SessionAttribute("userId") long sessionId) {
        long id = requestedId == null ? sessionId : requestedId;
        ModelAndView modelAndView = new ModelAndView("accountpages/dialogs");
        Collection<Dialog> dialogs = accountService.getDialogs(id);
        modelAndView.addObject(Attributes.DIALOGS, dialogs);
        infoHelper.setAccountInfo(modelAndView, id);
        modelAndView.addObject(TAB, "dialogs");
        return modelAndView;
    }

    @GetMapping("/groups")
    public ModelAndView getGroups(@RequestParam(value = "id", required = false) Long requestedId,
                                  @RequestParam(value = "allgroups", required = false) String listAll,
                                  @SessionAttribute("userId") long sessionId) {
        long id = requestedId == null ? sessionId : requestedId;
        ModelAndView modelAndView = new ModelAndView("accountpages/accountGroups");
        if ("true".equals(listAll)) {
            Map<Group, Boolean> unjoinedGroups = groupService.getAllUnjoinedGroupsAreRequested(id);
            modelAndView.addObject(GROUPS, unjoinedGroups);
            modelAndView.addObject(ALL_GROUPS_LIST, "true");
        } else {
            Collection<Group> joinedGroups = groupService.getAccountGroups(id);
            modelAndView.addObject(GROUPS, joinedGroups);
            modelAndView.addObject(ALL_GROUPS_LIST);
        }
        infoHelper.setAccountInfo(modelAndView, id);
        modelAndView.addObject(TAB, "groups");
        return modelAndView;
    }
}
