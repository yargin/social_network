package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.messages.AccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class AccountWallMessagesDao implements OneToManyDao<AccountWallMessage> {
    @PersistenceContext
    private transient EntityManager entityManager;
    private final AccountWallMessageDao messageDao;

    public AccountWallMessagesDao(AccountWallMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public Collection<AccountWallMessage> selectMany(long accountId) {
        Account account = new Account(accountId);
        TypedQuery<AccountWallMessage> selectMany = entityManager.createQuery("select m from AccountWallMessage m " +
                "join fetch m.author where m.receiver = :receiver order by m.date desc", AccountWallMessage.class);
        selectMany.setParameter("receiver", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long messageId) {
        AccountWallMessage message = messageDao.select(messageId);
        Account receiver = message.getReceiver();
        return !isNull(receiver) && receiver.getId() == accountId;
    }
}
