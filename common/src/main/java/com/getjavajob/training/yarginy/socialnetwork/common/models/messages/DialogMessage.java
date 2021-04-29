package com.getjavajob.training.yarginy.socialnetwork.common.models.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
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
@Table(name = "dialogs_messages")
@NamedEntityGraph(name = "graph.DialogMessage.allProperties", includeAllAttributes = true)
public class DialogMessage extends Message<Dialog> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "receiver_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "c_34"))
    private Dialog receiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "author", referencedColumnName = "id", foreignKey = @ForeignKey(name = "c_33"))
    private Account author;

    @Override
    public Dialog getReceiver() {
        return receiver;
    }

    public void setReceiver(Dialog receiver) {
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
        DialogMessage message = (DialogMessage) o;
        return implementedEquals(message);
    }
}
