package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Repository
public class AdditionalManyToManyDao implements Serializable {
    @PersistenceContext
    private transient EntityManager entityManager;

    public Map<Account, Boolean> getGroupMembersAreModerators(long groupId) {
        Query query = entityManager.createNativeQuery("SELECT a.id id, a.name name, a.surname surname, a.email email, " +
                "(CASE WHEN gmods.account_id <> 0 THEN TRUE ELSE FALSE END) is_moderator " +
                "FROM Groups_members gmems " +
                "JOIN Accounts a ON a.id = gmems.account_id " +
                "LEFT JOIN groups_moderators gmods " +
                "ON gmems.account_id = gmods.account_id AND gmems.group_id = gmods.group_id " +
                "WHERE gmems.group_id = :groupId", "rsMapping.groupMembersAreModerators").setParameter("groupId", groupId);
        List<Object[]> rawMembersAreModerators = query.getResultList();
        Map<Account, Boolean> membersAreModerators = new HashMap<>();
        for (Object[] raw : rawMembersAreModerators) {
            Account member = (Account) raw[1];
            Boolean isModerator = (Boolean) raw[0];
            membersAreModerators.put(member, isModerator);
        }
        return membersAreModerators;
    }

    public Map<Group, Boolean> selectUnJoinedGroupsAreRequested(long accountId) {
        Query query = entityManager.createNativeQuery("SELECT g.id, g.name, gmr.group_id requested" +
                " FROM `Groups` g LEFT JOIN " +
                " (SELECT group_id FROM groups_memberships_requests" +
                " WHERE account_id = ?) gmr " +
                " ON gmr.group_id = g.id " +
                " WHERE g.id NOT IN " +
                " (SELECT group_id FROM groups_members" +
                " WHERE account_id = ?);");
        query.setParameter(1, accountId);
        query.setParameter(2, accountId);
        List<Object[]> result = query.getResultList();
        Map<Group, Boolean> unJoinedGroupAreRequested = new HashMap<>();
        for (Object[] raw : result) {
            Group group = new Group(((BigInteger) raw[0]).longValue());
            group.setName((String) raw[1]);
            unJoinedGroupAreRequested.put(group, !isNull(raw[2]));
        }
        return unJoinedGroupAreRequested;
    }
}
