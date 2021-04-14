package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.GroupWallMessageFacadeDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Objects.isNull;

@Service
public class GroupWallMessageService implements MessageService<Group, GroupWallMessage> {
    private GroupWallMessageFacadeDaoImpl messageDao;

    @Autowired
    public void setMessageDao(GroupWallMessageFacadeDaoImpl messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public boolean addMessage(GroupWallMessage message) {
        if (isNull(message.getImage()) && isNull(message.getText())) {
            return false;
        }
        message.setPosted(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        return messageDao.create(message);
    }

    @Override
    public boolean deleteMessage(GroupWallMessage message) {
        return messageDao.delete(message);
    }

    @Override
    public Collection<GroupWallMessage> selectMessages(long groupId) {
        return messageDao.getMessages(groupId);
    }
}
