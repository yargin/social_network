package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.DialogMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.DialogMessageDaoImpl;
import org.springframework.stereotype.Service;

@Service("dialogMessageService")
public class DialogMessageServiceImpl extends AbstractMessageService {
    //todo
    public DialogMessageServiceImpl() {
        super(new DialogMessageDaoImpl());
    }
}
