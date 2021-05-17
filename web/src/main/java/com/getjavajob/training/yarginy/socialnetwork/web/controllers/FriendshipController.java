package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ERROR;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.FRIENDS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.FRIENDSHIP_REQUESTS;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.FRIENDSHIP_REQUEST_VIEW;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/friendship")
public class FriendshipController {
    private final AccountService accountService;
    private final AccountInfoHelper infoHelper;
    private final Redirector redirector;

    public FriendshipController(AccountService accountService, AccountInfoHelper infoHelper, Redirector redirector) {
        this.accountService = accountService;
        this.infoHelper = infoHelper;
        this.redirector = redirector;
    }

    @PostMapping("/accept")
    public String acceptFriendship(@RequestParam String accept, @RequestAttribute Long requesterId,
                                   @RequestAttribute long receiverId) {
        if ("true".equals(accept)) {
            accountService.addFriend(requesterId, receiverId);
        } else {
            accountService.deleteFriendshipRequest(requesterId, receiverId);
        }
        return redirector.getMvcPathForRedirect(FRIENDSHIP_REQUESTS, receiverId);
    }

    @GetMapping("/add")
    public ModelAndView addFriend(@RequestAttribute long requesterId, @RequestAttribute(required = false) String friend,
                                  @SessionAttribute long userId, @RequestAttribute long receiverId,
                                  HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView(FRIENDSHIP_REQUEST_VIEW);

        if (!isNull(friend) || userId != requesterId) {
            return new ModelAndView(redirector.redirectBackView(req));
        }

        try {
            modelAndView.addObject("created", accountService.createFriendshipRequest(requesterId, receiverId));
        } catch (IncorrectDataException e) {
            modelAndView.addObject(ERROR, "error.wrongRequest");
        }

        Account account = accountService.getFullInfo(receiverId);
        infoHelper.setAccountInfo(modelAndView, account);
        modelAndView.addObject(TAB, "addFriend");
        modelAndView.addObject(REQUESTED_ID, receiverId);
        return modelAndView;
    }

    @PostMapping("/remove")
    public String removeFromFriends(@RequestAttribute long requesterId, @RequestAttribute long receiverId) {
        accountService.removeFriend(requesterId, receiverId);
        return redirector.getMvcPathForRedirect(FRIENDS, receiverId);
    }
}
