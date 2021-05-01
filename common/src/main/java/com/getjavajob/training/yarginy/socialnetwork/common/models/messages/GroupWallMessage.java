package com.getjavajob.training.yarginy.socialnetwork.common.models.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
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
@Table(name = "group_wall_messages")
@NamedEntityGraph(name = "graph.GroupWallMessage.allProperties", includeAllAttributes = true)
public class GroupWallMessage extends Message<Group> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "receiver_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "c_36"))
    private Group receiver;
    @ManyToOne
    @JoinColumn(nullable = false, name = "author", referencedColumnName = "id", foreignKey = @ForeignKey(name = "c_35"))
    private Account author;

    @Override
    public Group getReceiver() {
        return receiver;
    }

    public void setReceiver(Group receiver) {
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
        GroupWallMessage message = (GroupWallMessage) o;
        return implementedEquals(message);
    }
}
