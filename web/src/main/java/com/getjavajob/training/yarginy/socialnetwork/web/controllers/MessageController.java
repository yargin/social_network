package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.AccountWallMessageService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.MessageDto;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.dialog.DialogMessageDto;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.DtoMapper;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final AccountWallMessageService accountWallMessageService;
    private final GroupWallMessageService groupWallMessageService;
    private final DialogMessagesService dialogMessagesService;
    private final Redirector redirector;
    private final DtoMapper dtoMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yy, HH:mm");

    public MessageController(AccountWallMessageService accountWallMessageService,
                             GroupWallMessageService groupWallMessageService, SimpMessagingTemplate messagingTemplate,
                             DialogMessagesService dialogMessagesService, Redirector redirector, DtoMapper dtoMapper) {
        this.accountWallMessageService = accountWallMessageService;
        this.groupWallMessageService = groupWallMessageService;
        this.messagingTemplate = messagingTemplate;
        this.dialogMessagesService = dialogMessagesService;
        this.redirector = redirector;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/account/add")
    public String addAccountWallMessage(@ModelAttribute("message") MessageDto message, @RequestAttribute long requesterId,
                                        @RequestAttribute long receiverId, HttpServletRequest req) {
        return addMessage(message, req, accountMessage -> accountWallMessageService.addMessage(
                dtoMapper.getAccountWallMessage(requesterId, receiverId, message)));
    }

    @PostMapping("/group/add")
    public String addGroupWallMessage(@ModelAttribute("message") MessageDto message, @RequestAttribute long requesterId,
                                      @RequestAttribute long receiverId, HttpServletRequest req) {
        return addMessage(message, req, accountMessage -> groupWallMessageService.addMessage(
                dtoMapper.getGroupWallMessage(requesterId, receiverId, message)));
    }

//    @MessageMapping("/dialog/add")
//    @ResponseBody
//    public void addDialogMessage(@RequestBody DialogMessageDto dialogMessageDto) {
//        DialogMessage message = dtoMapper.getDialogMessage(dialogMessageDto);
//        if (dialogMessagesService.addMessage(message)) {
//            dialogMessageDto.setStringPosted(dateFormat.format(message.getDate()));
//            messagingTemplate.convertAndSend("dialog/messages?id='" + dialogMessageDto.getDialogId());
//        }
//    }

    private String addMessage(MessageDto message, HttpServletRequest request, Predicate<MessageDto> consumer) {
        if (!message.getText().isEmpty() || !isNull(message.getImage()) && message.getImage().length != 0) {
            consumer.test(message);
        }
        return redirector.redirectBackView(request);
    }

    @PostMapping("/account/delete")
    public String deleteAccountWallMessage(@RequestAttribute long id, HttpServletRequest req) {
        AccountWallMessage message = new AccountWallMessage();
        message.setId(id);
        accountWallMessageService.deleteMessage(message);
        return redirector.redirectBackView(req);
    }

    @PostMapping("/group/delete")
    public String deleteGroupWallMessage(@RequestAttribute long id, HttpServletRequest req) {
        GroupWallMessage message = new GroupWallMessage();
        message.setId(id);
        groupWallMessageService.deleteMessage(message);
        return redirector.redirectBackView(req);
    }

    @PostMapping("/dialog/delete")
    public String deleteDialogMessage(@RequestAttribute long id, HttpServletRequest req) {
        DialogMessage message = new DialogMessage();
        message.setId(id);
        dialogMessagesService.deleteMessage(message);
        return redirector.redirectBackView(req);
    }

    @InitBinder
    public void registerCustomEditors(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
