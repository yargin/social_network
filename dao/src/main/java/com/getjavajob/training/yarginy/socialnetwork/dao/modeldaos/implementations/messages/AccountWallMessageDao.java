package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory.getNullAccountWallMessage;
import static java.util.Objects.isNull;

@Repository
public class AccountWallMessageDao extends GenericDao<AccountWallMessage> {
    @Override
    public AccountWallMessage getNullModel() {
        return getNullAccountWallMessage();
    }

    @Override
    protected Supplier<TypedQuery<AccountWallMessage>> getSelectByAltKey(EntityManager entityManager,
                                                                         AccountWallMessage message) {
        return () -> {
            TypedQuery<AccountWallMessage> query = entityManager.createQuery("select m from AccountWallMessage m " +
                    "where m.author = :author and m.receiver = :receiver and m.date = :posted", AccountWallMessage.class);
            query.setParameter("author", message.getAuthor());
            query.setParameter("receiver", message.getReceiver());
            query.setParameter("posted", message.getDate());
            return query;
        };
    }

    @Override
    public AccountWallMessage selectFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.AccountWallMessage.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(AccountWallMessage.class, id, hints);
    }

    @Override
    protected Supplier<AccountWallMessage> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(AccountWallMessage.class, id);
    }

    @Override
    protected Supplier<TypedQuery<AccountWallMessage>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select m from AccountWallMessage m", AccountWallMessage.class);
    }

    @Override
    protected Supplier<AccountWallMessage> getModelReference(EntityManager entityManager, AccountWallMessage message) {
        return () -> entityManager.getReference(AccountWallMessage.class, message.getId());
    }

    @Override
    protected boolean checkEntityFail(AccountWallMessage message) {
        return isNull(message.getReceiver()) || isNull(message.getAuthor());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, AccountWallMessage message) {
        message.setAuthor(entityManager.getReference(Account.class, message.getAuthor().getId()));
        message.setReceiver(entityManager.getReference(Account.class, message.getReceiver().getId()));
    }
}
