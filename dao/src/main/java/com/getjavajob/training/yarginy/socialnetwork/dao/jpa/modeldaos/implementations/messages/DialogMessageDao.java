package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullDialogMessage;
import static java.util.Objects.isNull;

@Repository
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
    public DialogMessage selectFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.DialogMessage.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(DialogMessage.class, id, hints);
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
    protected boolean checkEntityFail(DialogMessage message) {
        return isNull(message.getReceiver()) || isNull(message.getAuthor());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, DialogMessage message) {
        message.setReceiver(entityManager.getReference(Dialog.class, message.getReceiver().getId()));
        message.setAuthor(entityManager.getReference(Account.class, message.getAuthor().getId()));
    }
}
