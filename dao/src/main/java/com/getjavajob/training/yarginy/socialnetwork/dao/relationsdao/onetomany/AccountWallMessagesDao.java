package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.messages.AccountWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("accountWallMessagesDao")
public class AccountWallMessagesDao extends GenericOneToManyDao<AccountWallMessage> {
    private Dao<AccountWallMessage> messageDao;

    @Autowired
    public void setMessageDao(@Qualifier("accountWallMessageDao") Dao<AccountWallMessage> messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public Collection<AccountWallMessage> genericSelectMany(EntityManager entityManager, long accountId) {
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
