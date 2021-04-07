package com.getjavajob.training.yarginy.socialnetwork.common.models.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Component
@Scope("prototype")
@Entity
@Table(name = "dialogs_messages")
public class DialogMessage extends Message {
}
