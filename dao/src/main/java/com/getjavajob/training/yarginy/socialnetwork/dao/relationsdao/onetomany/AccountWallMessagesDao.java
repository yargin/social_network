package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.messages.AccountWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("jpaAccountWallMessagesDao")
public class AccountWallMessagesDao extends GenericOneToManyDao<AccountWallMessage> {
    private AccountWallMessageDao messageDao;

    @Autowired
    public void setMessageDao(AccountWallMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public Collection<AccountWallMessage> genericSelectMany(EntityManager entityManager, long accountId) {
        Account account = new Account(accountId);
        TypedQuery<AccountWallMessage> selectMany = entityManager.createQuery("select m from AccountWallMessage m " +
                "where m.receiver = :receiver", AccountWallMessage.class);
        selectMany.setParameter("receiver", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long messageId) {
        AccountWallMessage message = messageDao.select(messageId);
        return !isNull(message.getReceiver()) && message.getReceiver().getId() ==  accountId;
    }
}
