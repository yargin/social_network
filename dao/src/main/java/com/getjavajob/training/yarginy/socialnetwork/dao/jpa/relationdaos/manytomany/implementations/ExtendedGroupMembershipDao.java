package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;

public class ExtendedGroupMembershipDao extends GroupMembershipDao {
    @PersistenceContext
    private EntityManager entityManager;

//    public HashMap<Account, Boolean> getMembersModerators(long groupId) {
//        Group group = new Group(groupId);
//        TypedQuery<MemberIsModerator> query = entityManager.createQuery("select g.account, " +
//                "(CASE WHEN (moder.account != null) THEN true ELSE false END) as isModerator " +
//                "GroupMembership g " +
//                "join g.account left join GroupModerator moder on g.account = moder.account where g.group = :group", MemberIsModerator.class);
//        query.setParameter("group", group);
//        return query.getResultList();
//    }

    static class MemberIsModerator {
        private Account account;
        private boolean isModerator;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public boolean isModerator() {
            return isModerator;
        }

        public void setModerator(boolean moderator) {
            isModerator = moderator;
        }
    }
}
