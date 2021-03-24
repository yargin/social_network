package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.DialogMessageDaoFacade;
import org.springframework.stereotype.Service;

@Service("dialogMessageService")
public class DialogMessageServiceImpl extends AbstractMessageService {
    public DialogMessageServiceImpl(DialogMessageDaoFacade dialogMessageDao) {
        super(dialogMessageDao);
    }
}
