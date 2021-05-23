package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.dialog.DialogMessageDto;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.DtoMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

import static java.lang.Long.*;

@RestController
public class WebSocketController {
    private final DialogMessagesService dialogMessagesService;
    private final DtoMapper dtoMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yy, HH:mm");

    public WebSocketController(DialogMessagesService dialogMessagesService, DtoMapper dtoMapper,
                               SimpMessagingTemplate messagingTemplate) {
        this.dialogMessagesService = dialogMessagesService;
        this.dtoMapper = dtoMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message/dialog/add")
    public void addDialogMessage(@RequestBody DialogMessageDto dialogMessageDto) {
        DialogMessage message = dtoMapper.getDialogMessage(dialogMessageDto);
        if (dialogMessagesService.addMessage(message)) {
            Account author = message.getAuthor();
            dialogMessageDto.setAuthorName(author.getName());
            dialogMessageDto.setAuthorSurname(author.getSurname());
            dialogMessageDto.setStringPosted(dateFormat.format(message.getDate()));
            //todo subscribe address
//            System.out.println("trying to send to /sn/dialog/messages?id=" + dialogMessageDto.getDialogId());
//            messagingTemplate.convertAndSend("/dialog/messages?id=" + dialogMessageDto.getDialogId(), dialogMessageDto);
            System.out.println("trying to send to /dialog/messages");
            messagingTemplate.convertAndSend("/dialog/messages", dialogMessageDto);
        }
    }
}
