package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("groupWallMessagesDao")
public class GroupWallMessagesDao extends GenericOneToManyDao<GroupWallMessage> {
    private Dao<GroupWallMessage> messageDao;

    @Autowired
    public void setMessageDao(@Qualifier("groupWallMessageDao") Dao<GroupWallMessage> messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public Collection<GroupWallMessage> genericSelectMany(EntityManager entityManager, long groupId) {
        Group group = new Group();
        group.setId(groupId);
        TypedQuery<GroupWallMessage> selectMany = entityManager.createQuery("select m from GroupWallMessage m " +
                "where m.receiver = :receiver order by m.date desc", GroupWallMessage.class);
        selectMany.setParameter("receiver", group);
        return selectMany.getResultList();
    }

    @Override
    @Transactional
    public boolean relationExists(long groupId, long messageId) {
        GroupWallMessage message = messageDao.select(messageId);
        return !isNull(message.getReceiver()) && message.getReceiver().getId() == groupId;
    }
}