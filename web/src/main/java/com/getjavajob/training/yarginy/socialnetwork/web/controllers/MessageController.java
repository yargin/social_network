package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.AccountWallMessageService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final AccountWallMessageService accountWallMessageService;
    private final GroupWallMessageService groupWallMessageService;
    private final DialogMessagesService accountPrivateMessageService;
    private final Redirector redirector;

    public MessageController(AccountWallMessageService accountWallMessageService,
                             GroupWallMessageService groupWallMessageService,
                             DialogMessagesService accountPrivateMessageService,
                             Redirector redirector) {
        this.accountWallMessageService = accountWallMessageService;
        this.groupWallMessageService = groupWallMessageService;
        this.accountPrivateMessageService = accountPrivateMessageService;
        this.redirector = redirector;
    }

    @PostMapping("/add")
    public String addMessage(@RequestParam("type") String type, @ModelAttribute Message message,
                             HttpServletRequest req) {
        if (!message.getText().isEmpty() || !isNull(message.getImage()) && message.getImage().length != 0) {
            if ("accountWall".equals(type)) {
                //todo fix casting
                accountWallMessageService.addMessage((AccountWallMessage) message);
            } else if ("accountPrivate".equals(type)) {
                accountPrivateMessageService.addMessage((DialogMessage) message);
            } else if ("groupWall".equals(type)) {
                groupWallMessageService.addMessage((GroupWallMessage) message);
            }
        }
        return redirector.redirectBackView(req);
    }

    @PostMapping("/delete")
    public String deleteMessage(@RequestParam("type") String type, @ModelAttribute Message message,
                                HttpServletRequest req) {
        if ("accountWall".equals(type)) {
            accountWallMessageService.deleteMessage((AccountWallMessage) message);
        } else if ("accountPrivate".equals(type)) {
            accountPrivateMessageService.deleteMessage((DialogMessage) message);
        } else if ("groupWall".equals(type)) {
            groupWallMessageService.deleteMessage((GroupWallMessage) message);
        }
        return redirector.redirectBackView(req);
    }

    @InitBinder("message")
    public void registerCustomEditors(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
