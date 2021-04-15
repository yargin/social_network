package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.AccountWallMessageService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;

import java.util.function.Predicate;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final AccountWallMessageService accountWallMessageService;
    private final GroupWallMessageService groupWallMessageService;
    private final DialogMessagesService dialogMessagesService;
    private final Redirector redirector;

    public MessageController(AccountWallMessageService accountWallMessageService,
                             GroupWallMessageService groupWallMessageService,
                             DialogMessagesService dialogMessagesService, Redirector redirector) {
        this.accountWallMessageService = accountWallMessageService;
        this.groupWallMessageService = groupWallMessageService;
        this.dialogMessagesService = dialogMessagesService;
        this.redirector = redirector;
    }

    @PostMapping("/account/add")
    public String addAccountWallMessage(@ModelAttribute AccountWallMessage message, HttpServletRequest req) {
        return addMessage(message, req, accountMessage -> accountWallMessageService.addMessage(message));
    }

    @PostMapping("/group/add")
    public String addGroupWallMessage(@ModelAttribute GroupWallMessage message, HttpServletRequest req) {
        return addMessage(message, req, accountMessage -> groupWallMessageService.addMessage(message));
    }

    @PostMapping("/dialog/add")
    public String addDialogMessage(@ModelAttribute DialogMessage message, HttpServletRequest req) {
        return addMessage(message, req, accountMessage -> dialogMessagesService.addMessage(message));
    }

    private <E extends Model> String addMessage(Message<E> message, HttpServletRequest request, Predicate<Message<E>> consumer) {
        if (!message.getText().isEmpty() || !isNull(message.getImage()) && message.getImage().length != 0) {
            consumer.test(message);
        }
        return redirector.redirectBackView(request);
    }

    @PostMapping("/account/delete")
    public String deleteAccountWallMessage(@ModelAttribute AccountWallMessage message, HttpServletRequest req) {
        accountWallMessageService.deleteMessage(message);
        return redirector.redirectBackView(req);
    }

    @PostMapping("/group/delete")
    public String deleteGroupWallMessage(@ModelAttribute GroupWallMessage message, HttpServletRequest req) {
        groupWallMessageService.deleteMessage(message);
        return redirector.redirectBackView(req);
    }

    @PostMapping("/dialog/delete")
    public String deleteDialogMessage(@ModelAttribute DialogMessage message, HttpServletRequest req) {
        dialogMessagesService.deleteMessage(message);
        return redirector.redirectBackView(req);
    }

    @InitBinder
    public void registerCustomEditors(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
