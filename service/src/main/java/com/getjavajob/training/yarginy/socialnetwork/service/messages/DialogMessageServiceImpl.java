package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.DialogMessageDaoImpl;

public class DialogMessageServiceImpl extends AbstractMessageService {
    public DialogMessageServiceImpl() {
        super(new DialogMessageDaoImpl());
    }
}
