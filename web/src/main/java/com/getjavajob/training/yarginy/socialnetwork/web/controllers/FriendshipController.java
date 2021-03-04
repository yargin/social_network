package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectBackView;
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

    @Autowired
    public FriendshipController(AccountService accountService, AccountInfoHelper infoHelper) {
        this.accountService = accountService;
        this.infoHelper = infoHelper;
    }

    @PostMapping("/accept")
    public String acceptFriendship(@RequestParam String accept, @RequestParam Long requesterId,
                                   @RequestParam Long receiverId) {
        if ("true".equals(accept)) {
            accountService.addFriend(requesterId, receiverId);
        } else {
            accountService.deleteFriendshipRequest(requesterId, receiverId);
        }
        return "redirect:" + FRIENDSHIP_REQUESTS + "?id=" + receiverId;
    }

    @GetMapping("/add")
    public ModelAndView addFriend(@RequestAttribute Long requesterId, @RequestAttribute(required = false) String friend,
                                  @SessionAttribute Long userId, @RequestAttribute Long receiverId,
                                  HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView(FRIENDSHIP_REQUEST_VIEW);

        if (isNull(requesterId) || !isNull(friend) || !Objects.equals(userId, requesterId)) {
            return new ModelAndView(redirectBackView(req));
        }

        try {
            modelAndView.addObject("created", accountService.createFriendshipRequest(requesterId, receiverId));
        } catch (IncorrectDataException e) {
            modelAndView.addObject(Attributes.ERROR, e.getType().getPropertyKey());
        }

        infoHelper.setAccountInfo(modelAndView, receiverId);
        modelAndView.addObject(TAB, "addFriend");
        modelAndView.addObject(REQUESTED_ID, receiverId);
        return modelAndView;
    }

    @PostMapping("/remove")
    public String removeFromFriends(@RequestAttribute Long requesterId, @RequestAttribute Long receiverId) {
        accountService.removeFriend(requesterId, receiverId);
        return "redirect:" + FRIENDS + "?id=" + receiverId;
    }
}
