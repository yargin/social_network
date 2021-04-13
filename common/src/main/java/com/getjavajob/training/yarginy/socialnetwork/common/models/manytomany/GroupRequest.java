package com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Component
@Scope("prototype")
@Entity
@Table(name = "groups_memberships_requests")
public class GroupRequest implements JpaManyToMany<Account, Group> {
    @EmbeddedId
    private GroupRequestKey key;
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    public GroupRequest() {
    }

    public GroupRequest(Account account, Group group) {
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
    public static GroupRequestKey createGroupRequestKey(long accountId, long groupId) {
        return new GroupRequestKey(accountId, groupId);
    }

    @Embeddable
    static class GroupRequestKey implements Serializable {
        private long accountId;
        private long groupId;

        public GroupRequestKey() {
        }

        public GroupRequestKey(long accountId, long groupId) {
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
            GroupRequestKey that = (GroupRequestKey) o;
            return accountId == that.accountId && groupId == that.groupId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountId, groupId);
        }
    }
}
