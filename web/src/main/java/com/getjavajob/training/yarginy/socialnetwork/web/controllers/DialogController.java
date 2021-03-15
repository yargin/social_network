package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.DIALOG;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.DIALOG_VIEW;

@Controller
@RequestMapping("/dialog")
public class DialogController {
    private final DialogService dialogService;
    private final MessageService messageService;
    private final Redirector redirector;

    @Autowired
    public DialogController(DialogService dialogService, Redirector redirector,
                            @Qualifier("dialogMessageService") MessageService messageService) {
        this.dialogService = dialogService;
        this.redirector = redirector;
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public String createDialog(@ModelAttribute Dialog dialog, @ModelAttribute Message message,
                               @RequestParam("imageUpload") MultipartFile image) {
        Account author = message.getAuthor();
        dialog.setFirstAccount(author);
        Account receiver = new Account();
        receiver.setId(message.getReceiverId());
        dialog.setSecondAccount(receiver);
        if (!image.isEmpty()) {
            try {
                message.setImage(image.getBytes());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        dialogService.create(dialog, message);
        long createdId = dialogService.getByTalkers(author.getId(), receiver.getId()).getId();
        return REDIRECT + "/dialog/show?" + REQUESTED_ID + '=' + createdId;
    }

    @GetMapping("/show")
    public ModelAndView showDialog(HttpServletRequest req, @RequestParam long id) {
        Dialog dialog = dialogService.get(id);
        if (Objects.equals(dialog, dialogService.getNullDialog())) {
            return new ModelAndView(redirector.redirectBackView(req));
        }
        ModelAndView modelAndView = new ModelAndView(DIALOG_VIEW);
        modelAndView.addObject(dialog);
        Collection<Message> messages = messageService.selectMessages(dialog.getId());
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("type", "accountPrivate");
        modelAndView.addObject(TAB, "dialog");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView newDialog(@RequestAttribute long receiverId, @RequestAttribute long requesterId) {
        Dialog dialog = dialogService.getByTalkers(receiverId, requesterId);
        if (!Objects.equals(dialog, dialogService.getNullDialog())) {
            return new ModelAndView(REDIRECT + DIALOG + "?id=" + dialog.getId());
        } else {
            ModelAndView modelAndView = new ModelAndView("accountpages/newDialog");
            modelAndView.addObject(REQUESTED_ID, receiverId);
            return modelAndView;
        }
    }
}
