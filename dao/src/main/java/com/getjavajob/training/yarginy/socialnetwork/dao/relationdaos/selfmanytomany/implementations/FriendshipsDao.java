package com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.selfmanytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.Friendship;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.SelfManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.selfmanytomany.GenericSelfManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.Friendship.createFriendshipKey;

@Repository
public class FriendshipsDao extends GenericSelfManyToMany<Account> {
    @Override
    public Collection<Account> genericSelect(EntityManager entityManager, long id) {
        Account friend = new Account(id);
        TypedQuery<Account> firstQuery = entityManager.createQuery("select f.firstAccount from Friendship f " +
                "join f.firstAccount where f.secondAccount = :friend", Account.class);
        firstQuery.setParameter("friend", friend);
        TypedQuery<Account> secondQuery = entityManager.createQuery("select f.secondAccount from Friendship f " +
                "join f.secondAccount where f.firstAccount = :friend", Account.class);
        secondQuery.setParameter("friend", friend);
        Collection<Account> friends = firstQuery.getResultList();
        friends.addAll(secondQuery.getResultList());
        return friends;
    }

    @Override
    protected SelfManyToMany<Account> genericGetReference(EntityManager entityManager, long firstId, long secondId) {
        return entityManager.getReference(Friendship.class, createFriendshipKey(firstId, secondId));
    }

    @Override
    protected SelfManyToMany<Account> genericFind(EntityManager entityManager, long firstId, long secondId) {
        return entityManager.find(Friendship.class, createFriendshipKey(firstId, secondId));
    }

    @Override
    protected SelfManyToMany<Account> genericCreateObject(EntityManager entityManager, long firstId, long secondId) {
        Account firstAccount = entityManager.getReference(Account.class, firstId);
        Account secondAccount = entityManager.getReference(Account.class, secondId);
        return new Friendship(firstAccount, secondAccount);
    }
}
