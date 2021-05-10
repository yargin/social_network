package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.messages.GroupWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class GroupWallMessagesDao implements OneToManyDao<GroupWallMessage> {
    @PersistenceContext
    private transient EntityManager entityManager;
    private final GroupWallMessageDao messageDao;

    public GroupWallMessagesDao(GroupWallMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public Collection<GroupWallMessage> selectMany(long groupId) {
        Group group = new Group();
        group.setId(groupId);
        TypedQuery<GroupWallMessage> selectMany = entityManager.createQuery("select m from GroupWallMessage m " +
                "join fetch m.author where m.receiver = :receiver order by m.date desc", GroupWallMessage.class);
        selectMany.setParameter("receiver", group);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long groupId, long messageId) {
        GroupWallMessage message = messageDao.select(messageId);
        Group  receiver = message.getReceiver();
        return !isNull(receiver) && receiver.getId() == groupId;
    }
}
