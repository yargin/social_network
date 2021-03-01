package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectBackView;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final MessageService accountWallMessageService;
    private final MessageService groupWallMessageService;
    private final MessageService accountPrivateMessageService;

    @Autowired
    public MessageController(@Qualifier("accountWallMessageService") MessageService accountWallMessageService,
                             @Qualifier("groupWallMessageService") MessageService groupWallMessageService,
                             @Qualifier("dialogMessageService") MessageService accountPrivateMessageService) {
        this.accountWallMessageService = accountWallMessageService;
        this.groupWallMessageService = groupWallMessageService;
        this.accountPrivateMessageService = accountPrivateMessageService;
    }

    @PostMapping("/add")
    public String addMessage(@RequestParam("type") String type, @ModelAttribute Message message,
                             @RequestParam("imageUpload") MultipartFile image, HttpServletRequest req) {
        if (!image.isEmpty()) {
            try {
                message.setImage(image.getBytes());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        if ("accountWall".equals(type)) {
            accountWallMessageService.addMessage(message);
        } else if ("accountPrivate".equals(type)) {
            accountPrivateMessageService.addMessage(message);
        } else if ("groupWall".equals(type)) {
            groupWallMessageService.addMessage(message);
        }
        return redirectBackView(req);
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
        return redirectBackView(req);
    }
}
