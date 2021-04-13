package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaGenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullGroupWallMessage;

@Repository("jpaGroupWallMessageDao")
public class JpaGroupWallMessageDao extends JpaGenericDao<GroupWallMessage> {
    @Override
    public GroupWallMessage getNullModel() {
        return getNullGroupWallMessage();
    }

    @Override
    protected Supplier<TypedQuery<GroupWallMessage>> getSelectByAltKey(EntityManager entityManager, GroupWallMessage message) {
        return () -> {
            TypedQuery<GroupWallMessage> query = entityManager.createQuery("select m from GroupWallMessage m " +
                    "where m.author = :author and m.receiver = :receiver and m.posted = :posted", GroupWallMessage.class);
            query.setParameter("author", message.getAuthor());
            query.setParameter("receiver", message.getReceiver());
            query.setParameter("posted", message.getPosted());
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
}
