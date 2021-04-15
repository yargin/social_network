package com.getjavajob.training.yarginy.socialnetwork.dao.models.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullDialogMessage;
import static java.util.Objects.isNull;

@Repository("jpaDialogMessageDao")
public class DialogMessageDao extends GenericDao<DialogMessage> {
    @Override
    public DialogMessage getNullModel() {
        return getNullDialogMessage();
    }

    @Override
    protected Supplier<TypedQuery<DialogMessage>> getSelectByAltKey(EntityManager entityManager, DialogMessage message) {
        return () -> {
            TypedQuery<DialogMessage> query = entityManager.createQuery("select m from DialogMessage m " +
                            "where m.author = :author and m.receiver = :receiver and m.date = :posted", DialogMessage.class);
            query.setParameter("author", message.getAuthor());
            query.setParameter("receiver", message.getReceiver());
            query.setParameter("posted", message.getDate());
            return query;
        };
    }

    @Override
    protected Supplier<DialogMessage> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(DialogMessage.class, id);
    }

    @Override
    protected Supplier<TypedQuery<DialogMessage>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select m from DialogMessage m", DialogMessage.class);
    }

    @Override
    protected Supplier<DialogMessage> getModelReference(EntityManager entityManager, DialogMessage message) {
        return () -> entityManager.getReference(DialogMessage.class, message.getId());
    }

    @Override
    protected boolean checkEntity(DialogMessage message) {
        return !(isNull(message.getReceiver()) || isNull(message.getAuthor()));
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, DialogMessage message) {
        message.setAuthor(entityManager.getReference(Account.class, message.getAuthor().getId()));
        message.setReceiver(entityManager.getReference(Dialog.class, message.getReceiver().getId()));
    }
}
