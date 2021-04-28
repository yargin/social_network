package com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.messages.DialogMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class DialogMessagesDao implements OneToManyDao<DialogMessage> {
    @PersistenceContext
    private transient EntityManager entityManager;
    private final DialogMessageDao messageDao;

    public DialogMessagesDao(DialogMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public Collection<DialogMessage> selectMany(long dialogId) {
        Dialog dialog = new Dialog();
        dialog.setId(dialogId);
        TypedQuery<DialogMessage> selectMany = entityManager.createQuery("select m from DialogMessage m " +
                        "join fetch m.author a join fetch m.receiver r where m.receiver = :receiver order by m.date desc",
                DialogMessage.class);
        selectMany.setParameter("receiver", dialog);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long dialogId, long messageId) {
        DialogMessage message = messageDao.select(messageId);
        return !isNull(message.getReceiver()) && message.getReceiver().getId() == dialogId;
    }
}
