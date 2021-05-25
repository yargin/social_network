package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.dialog.DialogMessageDto;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.DtoMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
public class DialogMessageController {
    private final DialogMessagesService dialogMessagesService;
    private final DtoMapper dtoMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public DialogMessageController(DialogMessagesService dialogMessagesService, DtoMapper dtoMapper,
                                   SimpMessagingTemplate messagingTemplate) {
        this.dialogMessagesService = dialogMessagesService;
        this.dtoMapper = dtoMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message/dialog/add")
    public void addDialogMessage(@RequestBody DialogMessageDto dialogMessageDto) {
        DialogMessage message = dtoMapper.getDialogMessage(dialogMessageDto);
        if ((!dialogMessageDto.getText().isEmpty() || !dialogMessageDto.getImage().isEmpty()) &&
                dialogMessagesService.addMessage(message)) {
            dialogMessageDto.setId("" + message.getId());
            Account author = message.getAuthor();
            dialogMessageDto.setAuthorName(author.getName());
            dialogMessageDto.setAuthorSurname(author.getSurname());
            dialogMessageDto.setStringPosted(dateFormat.format(message.getDate()));
            messagingTemplate.convertAndSend("/dialog/messages/add?id=" + dialogMessageDto.getDialogId(), dialogMessageDto);
        }
    }

    @MessageMapping("/message/dialog/delete")
    public void deleteDialogMessage(@RequestBody String id) {
        DialogMessage message = new DialogMessage();
        message.setId(Long.parseLong(id));
        if (dialogMessagesService.deleteMessage(message)) {
            messagingTemplate.convertAndSend("/dialog/messages/delete?id=" + message.getReceiver().getId(), id);
        }
    }
}