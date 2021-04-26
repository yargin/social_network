package com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.GenericDaoTransactional;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullGroupWallMessage;
import static java.util.Objects.isNull;

@Repository
public class GroupWallMessageDaoTransactional extends GenericDaoTransactional<GroupWallMessage> {
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
    protected boolean checkEntity(GroupWallMessage message) {
        return isNull(message.getReceiver()) || isNull(message.getAuthor());
    }

    @Override
    protected void prepareModelRelations(EntityManager entityManager, GroupWallMessage message) {
        message.setAuthor(entityManager.getReference(Account.class, message.getAuthor().getId()));
        message.setReceiver(entityManager.getReference(Group.class, message.getReceiver().getId()));
    }
}
