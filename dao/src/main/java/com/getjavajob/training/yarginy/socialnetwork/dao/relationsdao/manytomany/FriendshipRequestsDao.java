package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.FriendshipRequest;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.FriendshipRequest.createFriendshipRequestsKey;

@Repository("friendshipRequestsDao")
public class FriendshipRequestsDao extends GenericManyToManyDao<Account, Account> {
    @Override
    public Collection<Account> genericSelectByFirst(EntityManager entityManager, long requesterId) {
        Account requester = new Account(requesterId);
        TypedQuery<Account> query = entityManager.createQuery("select f.receiver from FriendshipRequest f " +
                "where f.requester = :requester", Account.class);
        query.setParameter("requester", requester);
        return query.getResultList();
    }

    @Override
    public Collection<Account> genericSelectBySecond(EntityManager entityManager, long receiverId) {
        Account receiver = new Account(receiverId);
        TypedQuery<Account> query = entityManager.createQuery("select f.requester from FriendshipRequest f " +
                "where f.receiver = :receiver", Account.class);
        query.setParameter("receiver", receiver);
        return query.getResultList();
    }

    @Override
    protected JpaManyToMany<Account, Account> genericGetReference(EntityManager entityManager, long requesterId,
                                                                  long receiverId) {
        return entityManager.getReference(FriendshipRequest.class, createFriendshipRequestsKey(requesterId, receiverId));
    }

    @Override
    protected JpaManyToMany<Account, Account> genericFind(EntityManager entityManager, long requesterId,
                                                          long receiverId) {
        return entityManager.find(FriendshipRequest.class, createFriendshipRequestsKey(requesterId, receiverId));
    }

    @Override
    protected JpaManyToMany<Account, Account> genericCreateObject(EntityManager entityManager, long requesterId,
                                                                  long receiverId) {
        Account requester = entityManager.getReference(Account.class, requesterId);
        Account receiver = entityManager.getReference(Account.class, receiverId);
        return new FriendshipRequest(requester, receiver);
    }
}
