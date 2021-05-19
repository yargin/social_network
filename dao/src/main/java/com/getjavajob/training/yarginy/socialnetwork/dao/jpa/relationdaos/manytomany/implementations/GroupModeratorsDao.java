 package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupModerator;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.ManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.ModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.GenericManyToMany;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupModerator.createGroupModeratorKey;

 @Repository
 public class GroupModeratorsDao extends GenericManyToMany<Account, Group> {
     private final ModelsFactory factory;

     public GroupModeratorsDao(ModelsFactory factory) {
         this.factory = factory;
     }

     @Override
     protected ManyToMany<Account, Group> genericGetReference(EntityManager entityManager, long accountId,
                                                              long groupId) {
         return entityManager.getReference(GroupModerator.class, createGroupModeratorKey(accountId, groupId));
     }

     @Override
     protected ManyToMany<Account, Group> genericFind(EntityManager entityManager, long accountId, long groupId) {
         return entityManager.find(GroupModerator.class, createGroupModeratorKey(accountId, groupId));
    }

    @Override
    protected ManyToMany<Account, Group> genericCreateObject(EntityManager entityManager, long accountId,
                                                             long groupId) {
        Account account = entityManager.getReference(Account.class, accountId);
        Group group = entityManager.getReference(Group.class, groupId);
        return new GroupModerator(account, group);
    }

    @Override
    public Collection<Group> genericSelectByFirst(EntityManager entityManager, long accountId) {
        Account account = factory.getAccount(accountId);
        TypedQuery<Group> query = entityManager.createQuery("select g.group from GroupModerator g join g.group " +
                "where g.account = :account", Group.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Collection<Account> genericSelectBySecond(EntityManager entityManager, long groupId) {
        Group group = factory.getGroup(groupId);
        TypedQuery<Account> query = entityManager.createQuery("select g.account from GroupModerator g " +
                "join g.account where g.group = :group", Account.class);
        query.setParameter("group", group);
        return query.getResultList();
    }

    @Transactional
    public Map<Account, Boolean> getGroupMembersAreModerators(long groupId) {
        Query query = entityManager.createNativeQuery("SELECT a.id id, a.name name, a.surname surname, a.email email, " +
                "(CASE WHEN gmods.account_id <> 0 THEN TRUE ELSE FALSE END) is_moderator " +
                "FROM Groups_members gmems " +
                "JOIN Accounts a ON a.id = gmems.account_id " +
                "LEFT JOIN groups_moderators gmods " +
                "ON gmems.account_id = gmods.account_id AND gmems.group_id = gmods.group_id " +
                "WHERE gmems.group_id = :groupId", "rsMapping.groupMembersAreModerators");
        query.setParameter("groupId", groupId);
        List<Object[]> rawMembersAreModerators = query.getResultList();
        Map<Account, Boolean> membersAreModerators = new HashMap<>();
        for (Object[] raw : rawMembersAreModerators) {
            Account member = (Account) raw[1];
            Boolean isModerator = (Boolean) raw[0];
            membersAreModerators.put(member, isModerator);
        }
        return membersAreModerators;
    }
}
