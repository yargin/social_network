package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullGroupWallMessage;
import static java.util.Objects.isNull;

@Repository
public class GroupWallMessageDao extends GenericDao<GroupWallMessage> {
    @Override
    public GroupWallMessage getNullModel() {
        return getNullGroupWallMessage();
    }

    @Override
    protected Supplier<TypedQuery<GroupWallMessage>> getSelectByAltKey(EntityManager entityManager, GroupWallMessage message) {
        return () -> {
            TypedQuery<GroupWallMessage> query = entityManager.createQuery("select m from GroupWallMessage m " +
                    "where m.author = :author and m.receiver = :receiver and m.date = :posted", GroupWallMessage.class);
            query.setParameter("author", message.getAuthor());
            query.setParameter("receiver", message.getReceiver());
            query.setParameter("posted", message.getDate());
            return query;
        };
    }

    @Override
    public GroupWallMessage selectFullInfo(long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("graph.GroupWallMessage.allProperties");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(GroupWallMessage.class, id, hints);
    }

    @Override
    protected Supplier<GroupWallMessage> getSelectByPk(EntityManager entityManager, long id) {
        return () -> entityManager.find(GroupWallMessage.class, id);
    }

    @Override
    protected Supplier<TypedQuery<GroupWallMessage>> getSelectAll(EntityManager entityManager) {
        return () -> entityManager.createQuery("select m from GroupWallMessage m", GroupWallMessage.class);
    }

    @Override
    protected Supplier<GroupWallMessage> getModelReference(EntityManager entityManager, GroupWallMessage message) {
        return () -> entityManager.getReference(GroupWallMessage.class, message.getId());
    }

    @Override
    protected boolean checkEntityFail(GroupWallMessage message) {
        return isNull(message.getReceiver()) || isNull(message.getAuthor());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, GroupWallMessage message) {
        message.setAuthor(entityManager.getReference(Account.class, message.getAuthor().getId()));
        message.setReceiver(entityManager.getReference(Group.class, message.getReceiver().getId()));
    }
}
