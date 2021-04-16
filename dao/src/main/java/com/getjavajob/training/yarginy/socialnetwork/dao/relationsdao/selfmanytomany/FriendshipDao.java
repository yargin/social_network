package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.Friendship;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaSelfManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.Friendship.createFriendshipKey;
import static java.lang.Math.max;
import static java.lang.Math.min;

@Repository("friendshipDao")
public class FriendshipDao extends GenericSelfManyToManyDao<Account> {
    @Override
    public Collection<Account> genericSelect(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Account friend = new Account(id);
        TypedQuery<Account> firstQuery = entityManager.createQuery("select f.firstAccount from Friendship f " +
                "where f.secondAccount = :friend", Account.class);
        firstQuery.setParameter("friend", friend);
        TypedQuery<Account> secondQuery = entityManager.createQuery("select f.secondAccount from Friendship f " +
                "where f.firstAccount = :friend", Account.class);
        secondQuery.setParameter("friend", friend);
        Collection<Account> friends = firstQuery.getResultList();
        friends.addAll(secondQuery.getResultList());
        return friends;
    }

    @Override
    protected JpaSelfManyToMany<Account> genericGetReference(EntityManager entityManager, long firstId, long secondId) {
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        return entityManager.getReference(Friendship.class, createFriendshipKey(greaterId, lowerId));
    }

    @Override
    protected JpaSelfManyToMany<Account> genericFind(EntityManager entityManager, long firstId, long secondId) {
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        return entityManager.find(Friendship.class, createFriendshipKey(greaterId, lowerId));
    }

    @Override
    protected JpaSelfManyToMany<Account> genericCreateObject(EntityManager entityManager, long firstId, long secondId) {
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        Account firstAccount = entityManager.getReference(Account.class, greaterId);
        Account secondAccount = entityManager.getReference(Account.class, lowerId);
        return new Friendship(firstAccount, secondAccount);
    }
}
