package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.messages.JpaGroupWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("jpaGroupWallMessagesDao")
public class JpaGroupWallMessagesDao implements JpaOneToManyDao<GroupWallMessage> {
    private transient EntityManagerFactory entityManagerFactory;
    private JpaGroupWallMessageDao messageDao;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Autowired
    public void setMessageDao(JpaGroupWallMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public Collection<GroupWallMessage> selectMany(long groupId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Group group = new Group();
        group.setId(groupId);
        TypedQuery<GroupWallMessage> selectMany = entityManager.createQuery("select m from GroupWallMessage m " +
                "where m.receiver = :receiver", GroupWallMessage.class);
        selectMany.setParameter("receiver", group);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long groupId, long messageId) {
        GroupWallMessage message = messageDao.select(messageId);
        return !isNull(message.getReceiver()) && message.getReceiver().getId() == groupId;
    }
}