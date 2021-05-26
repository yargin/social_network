package com.getjavajob.training.yarginy.socialnetwork.web.helpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.MessageDto;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.dialog.DialogMessageDto;
import org.springframework.stereotype.Component;

import static java.lang.Long.parseLong;

@Component
public class DtoMapper {
    private final DataConverter dataConverter;

    public DtoMapper(DataConverter dataConverter) {
        this.dataConverter = dataConverter;
    }

    private void mapMessageAttributes(Message<? extends Model> message, MessageDto messageDto) {
        message.setId(messageDto.getId());
        message.setText(messageDto.getText());
        message.setImage(messageDto.getImage());
        message.setDate(messageDto.getDate());
    }

    public AccountWallMessage getAccountWallMessage(long authorId, long receiverId, MessageDto messageDto) {
        AccountWallMessage message = new AccountWallMessage();
        mapMessageAttributes(message, messageDto);
        message.setAuthor(new Account(authorId));
        message.setReceiver(new Account(receiverId));
        return message;
    }

    public GroupWallMessage getGroupWallMessage(long authorId, long receiverId, MessageDto messageDto) {
        GroupWallMessage message = new GroupWallMessage();
        mapMessageAttributes(message, messageDto);
        message.setAuthor(new Account(authorId));
        message.setReceiver(new Group(receiverId));
        return message;
    }

    public DialogMessage getDialogMessage(DialogMessageDto messageDto) {
        DialogMessage message = new DialogMessage();
        Account author = new Account(parseLong(messageDto.getAuthorId()));
        message.setAuthor(author);
        Dialog dialog = new Dialog(parseLong(messageDto.getDialogId()));
        message.setReceiver(dialog);
        message.setText(messageDto.getText());
        message.setImage(dataConverter.getBytesDecoded(messageDto.getImage()));
        return message;
    }
}
