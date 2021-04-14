package com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Component
@Scope("prototype")
@Entity
@Table(name = "groups_moderators")
public class GroupModerator implements JpaManyToMany<Account, Group> {
    @EmbeddedId
    private GroupModeratorKey key = new GroupModeratorKey();
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "c_24"))
    private Account account;
    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "c_25"))
    private Group group;

    public GroupModerator() {
    }

    public GroupModerator(Account account, Group group) {
        this.account = account;
        this.group = group;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static GroupModeratorKey createGroupModeratorKey(long accountId, long groupId) {
        return new GroupModeratorKey(accountId, groupId);
    }

    @Embeddable
    static class GroupModeratorKey implements Serializable {
        private long accountId;
        private long groupId;

        public GroupModeratorKey() {
        }

        public GroupModeratorKey(long accountId, long groupId) {
            this.accountId = accountId;
            this.groupId = groupId;
        }

        public long getAccountId() {
            return accountId;
        }

        public void setAccountId(long accountId) {
            this.accountId = accountId;
        }

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GroupModeratorKey that = (GroupModeratorKey) o;
            return accountId == that.accountId && groupId == that.groupId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountId, groupId);
        }
    }
}
