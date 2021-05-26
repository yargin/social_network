package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.dialog.DialogMessageDto;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.DataConverter;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.DtoMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DialogMessageController {
    private final DialogMessagesService messagesService;
    private final DtoMapper dtoMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final DataConverter dataConverter;

    public DialogMessageController(DialogMessagesService messagesService, DtoMapper dtoMapper,
                                   SimpMessagingTemplate messagingTemplate, DataConverter dataConverter) {
        this.messagesService = messagesService;
        this.dtoMapper = dtoMapper;
        this.messagingTemplate = messagingTemplate;
        this.dataConverter = dataConverter;
    }

    @MessageMapping("/message/dialog/add")
    public void addDialogMessage(@RequestBody DialogMessageDto messageDto) {
        DialogMessage message = dtoMapper.getDialogMessage(messageDto);
        if ((!messageDto.getText().isEmpty() || !messageDto.getImage().isEmpty()) && messagesService.addMessage(message)) {
            messageDto.setId(Long.toString(+message.getId()));
            Account author = message.getAuthor();
            messageDto.setAuthorName(author.getName());
            messageDto.setAuthorSurname(author.getSurname());
            messageDto.setStringPosted(dataConverter.getStringDate(message.getDate()));
            messagingTemplate.convertAndSend("/dialog/" + messageDto.getDialogId() + "/messages/add", messageDto);
        }
    }

    @MessageMapping("/message/dialog/delete")
    public void deleteDialogMessage(@RequestBody String id) {
        DialogMessage message = new DialogMessage();
        message.setId(Long.parseLong(id));
        if (messagesService.deleteMessage(message)) {
            messagingTemplate.convertAndSend("/dialog/" + message.getReceiver().getId() + "/messages/delete", id);
        }
    }
}