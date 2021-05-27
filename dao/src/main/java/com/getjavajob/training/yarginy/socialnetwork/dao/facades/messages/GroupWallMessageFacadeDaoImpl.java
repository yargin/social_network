package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.messages.GroupWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations.messages.GroupWallMessagesDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupWallMessageDaoFacade")
public class GroupWallMessageFacadeDaoImpl implements GroupWallMessageDaoFacade {
    private final GroupWallMessageDao groupWallMessageDao;
    private final GroupWallMessagesDao groupWallMessagesDao;
    private final TransactionPerformer transactionPerformer;

    public GroupWallMessageFacadeDaoImpl(GroupWallMessageDao groupWallMessageDao,
                                         GroupWallMessagesDao groupWallMessagesDao,
                                         TransactionPerformer transactionPerformer) {
        this.groupWallMessageDao = groupWallMessageDao;
        this.groupWallMessagesDao = groupWallMessagesDao;
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public GroupWallMessage select(long messageId) {
        return groupWallMessageDao.select(messageId);
    }

    @Override
    public GroupWallMessage select(GroupWallMessage message) {
        return groupWallMessageDao.select(message);
    }

    @Override
    public boolean create(GroupWallMessage message) {
        return transactionPerformer.transactionPerformed(groupWallMessageDao::create, message);
    }

    @Override
    public boolean update(GroupWallMessage message) {
        return transactionPerformer.transactionPerformed(groupWallMessageDao::update, message);
    }

    @Override
    public boolean delete(GroupWallMessage message) {
        return transactionPerformer.transactionPerformed(groupWallMessageDao::delete, message);
    }

    @Override
    public Collection<GroupWallMessage> selectAll() {
        return groupWallMessageDao.selectAll();
    }

    @Override
    public GroupWallMessage getNullMessage() {
        return groupWallMessageDao.getNullModel();
    }

    @Override
    public Collection<GroupWallMessage> getMessages(long groupId) {
        return groupWallMessagesDao.selectMany(groupId);
    }
}
