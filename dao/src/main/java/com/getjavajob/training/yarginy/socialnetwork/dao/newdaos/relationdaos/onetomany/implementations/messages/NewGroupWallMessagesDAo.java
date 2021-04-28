package com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.messages.NewGroupWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.NewOneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class NewGroupWallMessagesDAo implements NewOneToManyDao<GroupWallMessage> {
    @PersistenceContext
    private EntityManager entityManager;
    private NewGroupWallMessageDao messageDao;

    @Autowired
    public void setMessageDao(NewGroupWallMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public Collection<GroupWallMessage> selectMany(long groupId) {
        Group group = new Group();
        group.setId(groupId);
        TypedQuery<GroupWallMessage> selectMany = entityManager.createQuery("select m from GroupWallMessage m " +
                "where m.receiver = :receiver order by m.date desc", GroupWallMessage.class);
        selectMany.setParameter("receiver", group);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long groupId, long messageId) {
        GroupWallMessage message = messageDao.select(messageId);
        return !isNull(message.getReceiver()) && message.getReceiver().getId() == groupId;
    }
}
