package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaGenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullAccountWallMessage;

@Repository("jpaAccountWallMessageDao")
public class JpaAccountWallMessageDao extends JpaGenericDao<AccountWallMessage> {
    @Override
    public AccountWallMessage getNullModel() {
        return getNullAccountWallMessage();
    }

    @Override
    protected Supplier<TypedQuery<AccountWallMessage>> getSelectByAltKey(EntityManager entityManager,
                                                                         AccountWallMessage message) {
        return () -> {
            TypedQuery<AccountWallMessage> query = entityManager.createQuery("select m from AccountWallMessage m " +
                    "where m.author = :author and m.receiver = :receiver and m.posted = :posted", AccountWallMessage.class);
            query.setParameter("author", message.getAuthor());
            query.setParameter("receiver", message.getReceiver());
            query.setParameter("posted", message.getPosted());
            return query;
        };
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
}
