package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("dialogMessagesDao")
public class DialogMessagesDao extends GenericOneToManyDao<DialogMessage> {
    private Dao<DialogMessage> messageDao;

    @Autowired
    public void setMessageDao(@Qualifier("dialogMessageDao") Dao<DialogMessage> messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public Collection<DialogMessage> genericSelectMany(EntityManager entityManager, long dialogId) {
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
