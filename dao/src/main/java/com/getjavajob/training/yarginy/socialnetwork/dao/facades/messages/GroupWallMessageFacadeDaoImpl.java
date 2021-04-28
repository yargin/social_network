package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.messages.NewGroupWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.messages.NewGroupWallMessagesDAo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupWallMessageDaoFacade")
public class GroupWallMessageFacadeDaoImpl implements GroupWallMessageDaoFacade {
    private NewGroupWallMessageDao groupWallMessageDao;
    private NewGroupWallMessagesDAo groupWallMessagesDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setGroupWallMessageDao(NewGroupWallMessageDao groupWallMessageDao) {
        this.groupWallMessageDao = groupWallMessageDao;
    }

    @Autowired
    public void setGroupWallMessagesDao(NewGroupWallMessagesDAo groupWallMessagesDao) {
        this.groupWallMessagesDao = groupWallMessagesDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
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
