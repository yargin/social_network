package com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
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
@Table(name = "friendships")
public class Friendship implements JpaSelfManyToMany<Account> {
    @EmbeddedId
    private FriendshipKey friendshipKey = new FriendshipKey();
    @ManyToOne
    @MapsId("firstAccountId")
    @JoinColumn(name = "first_account")
    private Account firstAccount;
    @ManyToOne
    @MapsId("secondAccountId")
    @JoinColumn(name = "second_account")
    private Account secondAccount;

    public Friendship() {
    }

    public Friendship(Account firstAccount, Account secondAccount) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
    }

    public Account getFirstAccount() {
        return firstAccount;
    }

    public void setFirstAccount(Account firstAccount) {
        this.firstAccount = firstAccount;
    }

    public Account getSecondAccount() {
        return secondAccount;
    }

    public void setSecondAccount(Account secondAccount) {
        this.secondAccount = secondAccount;
    }

    public static FriendshipKey createFriendshipKey(long firstId, long secondId) {
        return new FriendshipKey(firstId, secondId);
    }

    @Embeddable
    static class FriendshipKey implements Serializable {
        @Column(name = "first_account")
        private long firstAccountId;
        @Column(name = "second_account")
        private long secondAccountId;

        public FriendshipKey() {
        }

        public FriendshipKey(long firstAccountId, long secondAccountId) {
            this.firstAccountId = firstAccountId;
            this.secondAccountId = secondAccountId;
        }

        public long getFirstAccountId() {
            return firstAccountId;
        }

        public void setFirstAccountId(long firstAccountId) {
            this.firstAccountId = firstAccountId;
        }

        public long getSecondAccountId() {
            return secondAccountId;
        }

        public void setSecondAccountId(long secondAccountId) {
            this.secondAccountId = secondAccountId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FriendshipKey friendshipKey = (FriendshipKey) o;
            return firstAccountId == friendshipKey.firstAccountId && secondAccountId == friendshipKey.secondAccountId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstAccountId, secondAccountId);
        }
    }
}
