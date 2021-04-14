package com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
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
@Table(name = "friendships_requests")
public class FriendshipRequest implements JpaManyToMany<Account, Account> {
    @EmbeddedId
    private FriendshipRequestsKey friendshipRequestsKey = new FriendshipRequestsKey();
    @ManyToOne
    @MapsId("requester")
    @JoinColumn(name = "requester", foreignKey = @ForeignKey(name = "c_28"))
    private Account requester;
    @ManyToOne
    @MapsId("receiver")
    @JoinColumn(name = "receiver", foreignKey = @ForeignKey(name = "c_29"))
    private Account receiver;

    public FriendshipRequest() {
    }

    public FriendshipRequest(Account requester, Account receiver) {
        this.requester = requester;
        this.receiver = receiver;
    }

    public Account getRequester() {
        return requester;
    }

    public void setRequester(Account requester) {
        this.requester = requester;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public static FriendshipRequestsKey createFriendshipRequestsKey(long requesterId, long receiverId) {
        return new FriendshipRequestsKey(requesterId, receiverId);
    }

    @Embeddable
    static class FriendshipRequestsKey implements Serializable {
        private long requester;
        private long receiver;

        public FriendshipRequestsKey() {
        }

        public FriendshipRequestsKey(long requester, long receiver) {
            this.requester = requester;
            this.receiver = receiver;
        }

        public long getRequester() {
            return requester;
        }

        public void setRequester(long requester) {
            this.requester = requester;
        }

        public long getReceiver() {
            return receiver;
        }

        public void setReceiver(long receiver) {
            this.receiver = receiver;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FriendshipRequestsKey that = (FriendshipRequestsKey) o;
            return requester == that.requester && receiver == that.receiver;
        }

        @Override
        public int hashCode() {
            return Objects.hash(requester, receiver);
        }
    }
}
