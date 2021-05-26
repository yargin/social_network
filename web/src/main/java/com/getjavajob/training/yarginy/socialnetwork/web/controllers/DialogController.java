package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.dialog.DialogMessageDto;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.DataConverter;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.SHOW_DIALOG;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.DIALOG_VIEW;

@Controller
@RequestMapping("/dialog")
public class DialogController {
    public static final String DIALOG = "dialog";
    private final ObjectMapper mapper = new ObjectMapper();
    private final DialogService dialogService;
    private final DialogMessagesService messageService;
    private final Redirector redirector;
    private final DataConverter dataConverter;

    public DialogController(DialogService dialogService, Redirector redirector, DialogMessagesService messageService,
                            DataConverter dataConverter) {
        this.dialogService = dialogService;
        this.redirector = redirector;
        this.messageService = messageService;
        this.dataConverter = dataConverter;
    }

    @PostMapping("/create")
    public String createDialog(@RequestParam(required = false) String text, @RequestAttribute long requesterId,
                               @RequestAttribute long receiverId,
                               @RequestParam(name = "imageUpload", required = false) MultipartFile image) {
        Account author = new Account(requesterId);
        Account receiver = new Account(receiverId);
        Dialog dialog = new Dialog(author, receiver);
        DialogMessage message = new DialogMessage();
        message.setText(text);
        message.setAuthor(author);
        message.setReceiver(dialog);
        if (!image.isEmpty()) {
            try {
                message.setImage(image.getBytes());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        dialogService.create(dialog, message);
        long createdId = dialogService.get(dialog).getId();
        return redirector.getMvcPathForRedirect(SHOW_DIALOG, createdId);
    }

    @GetMapping("/show")
    public ModelAndView showDialog(HttpServletRequest req, @RequestAttribute long id) throws JsonProcessingException {
        Dialog dialog = dialogService.getFullInfo(id);
        if (Objects.equals(dialog, dialogService.getNullDialog())) {
            return new ModelAndView(redirector.redirectBackView(req));
        }
        ModelAndView modelAndView = new ModelAndView(DIALOG_VIEW);
        modelAndView.addObject(DIALOG, dialog);
        Collection<DialogMessage> messages = messageService.selectMessages(id);
        String jsonMessages = mapper.writeValueAsString(mapMessagesToDto(messages));
        modelAndView.addObject("messages", jsonMessages);
        modelAndView.addObject("type", DIALOG);
        modelAndView.addObject(TAB, DIALOG);
        return modelAndView;
    }

    private Collection<DialogMessageDto> mapMessagesToDto(Collection<DialogMessage> dialogMessages) {
        return dialogMessages.stream().map(m -> {
            Account author = m.getAuthor();
            return new DialogMessageDto(Long.toString(m.getId()), m.getText(), dataConverter.getHtmlPhoto(m.getImage()),
                    Long.toString(author.getId()), author.getName(), author.getSurname(),
                    dataConverter.getStringDate(m.getDate()));
        }).collect(Collectors.toList());
    }

    @PostMapping("/new")
    public ModelAndView newDialog(@RequestAttribute long receiverId, @RequestAttribute long requesterId) {
        Dialog dialog = dialogService.getByTalkers(receiverId, requesterId);
        if (!Objects.equals(dialog, dialogService.getNullDialog())) {
            return new ModelAndView(REDIRECT + SHOW_DIALOG + "?id=" + dialog.getId());
        } else {
            ModelAndView modelAndView = new ModelAndView("accountpages/newDialog");
            modelAndView.addObject(REQUESTED_ID, receiverId);
            return modelAndView;
        }
    }
}
