package com.getjavajob.training.yarginy.socialnetwork.common.models.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@Component
@Scope("prototype")
@Entity
@Table(name = "account_wall_messages")
@NamedEntityGraph(name = "graph.AccountWallMessage.allProperties", includeAllAttributes = true)
public class AccountWallMessage extends Message<Account> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "receiver_id", foreignKey = @ForeignKey(name = "c_32"))
    private Account receiver;
    @ManyToOne
    @JoinColumn(nullable = false, name = "author", foreignKey = @ForeignKey(name = "c_31"))
    private Account author;

    @Override
    public Account getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    @Override
    public Account getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(Account author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountWallMessage message = (AccountWallMessage) o;
        return implementedEquals(message);
    }
}
