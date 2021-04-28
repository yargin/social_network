package com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.messages.NewAccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.NewOneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class NewAccountWallMessagesDao implements NewOneToManyDao<AccountWallMessage> {
    @PersistenceContext
    private EntityManager entityManager;
    private NewAccountWallMessageDao messageDao;

    @Autowired
    public void setMessageDao(NewAccountWallMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public Collection<AccountWallMessage> selectMany(long accountId) {
        Account account = new Account(accountId);
        TypedQuery<AccountWallMessage> selectMany = entityManager.createQuery("select m from AccountWallMessage m " +
                "where m.receiver = :receiver order by m.date desc", AccountWallMessage.class);
        selectMany.setParameter("receiver", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long messageId) {
        AccountWallMessage message = messageDao.select(messageId);
        return !isNull(message.getReceiver()) && message.getReceiver().getId() ==  accountId;
    }
}
