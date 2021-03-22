package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final MessageService accountWallMessageService;
    private final MessageService groupWallMessageService;
    private final MessageService accountPrivateMessageService;
    private final Redirector redirector;

    public MessageController(@Qualifier("accountWallMessageService") MessageService accountWallMessageService,
                             @Qualifier("groupWallMessageService") MessageService groupWallMessageService,
                             @Qualifier("dialogMessageService") MessageService accountPrivateMessageService,
                             Redirector redirector) {
        this.accountWallMessageService = accountWallMessageService;
        this.groupWallMessageService = groupWallMessageService;
        this.accountPrivateMessageService = accountPrivateMessageService;
        this.redirector = redirector;
    }

    @PostMapping("/add")
    public String addMessage(@RequestParam("type") String type, @ModelAttribute Message message,
                             HttpServletRequest req) {
        if ("accountWall".equals(type)) {
            accountWallMessageService.addMessage(message);
        } else if ("accountPrivate".equals(type)) {
            accountPrivateMessageService.addMessage(message);
        } else if ("groupWall".equals(type)) {
            groupWallMessageService.addMessage(message);
        }
        return redirector.redirectBackView(req);
    }

    @PostMapping("/delete")
    public String deleteMessage(@RequestParam("type") String type, @ModelAttribute Message message,
                                HttpServletRequest req) {
        if ("accountWall".equals(type)) {
            accountWallMessageService.deleteMessage(message);
        } else if ("accountPrivate".equals(type)) {
            accountPrivateMessageService.deleteMessage(message);
        } else if ("groupWall".equals(type)) {
            groupWallMessageService.deleteMessage(message);
        }
        return redirector.redirectBackView(req);
    }

    @InitBinder("message")
    public void registerCustomEditors(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
