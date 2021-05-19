package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.ModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.messages.DialogMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class DialogMessagesDao implements OneToManyDao<DialogMessage> {
    private final DialogMessageDao messageDao;
    private final ModelsFactory factory;
    @PersistenceContext
    private transient EntityManager entityManager;

    public DialogMessagesDao(DialogMessageDao messageDao, ModelsFactory modelsFactory) {
        this.messageDao = messageDao;
        this.factory = modelsFactory;
    }

    @Override
    @Transactional
    public Collection<DialogMessage> selectMany(long dialogId) {
        Dialog dialog = factory.getDialog(dialogId);
        TypedQuery<DialogMessage> selectMany = entityManager.createQuery("select m from DialogMessage m " +
                        "join fetch m.author where m.receiver = :receiver order by m.date desc",
                DialogMessage.class);
        selectMany.setParameter("receiver", dialog);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long dialogId, long messageId) {
        DialogMessage message = messageDao.select(messageId);
        Dialog receiver = message.getReceiver();
        return !isNull(receiver) && receiver.getId() == dialogId;
    }
}
